package com.example.adminnetflix.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.utils.DeleteImage;
import com.example.adminnetflix.activities.DetailFilmActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.DataAllFilm;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.DeleteVideo;
import com.example.adminnetflix.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<DataAllFilm> testList;

    public DetailFilmAdapter(Context mContext, List<DataAllFilm> testList) {
        this.mContext = mContext;
        this.testList = testList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vertical_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataAllFilm dataAllFilm = testList.get(position);
        String idFilm = dataAllFilm.getId();
        String imageUrl = dataAllFilm.getImageFilm().getUrl();
        ((ItemViewHolder) holder).itemTitle.setText(dataAllFilm.getTitle());
        ((ItemViewHolder) holder).itemDes.setText(dataAllFilm.getDescription());
        ((ItemViewHolder) holder).itemLimitAge.setText(dataAllFilm.getAgeLimit().toString());
        Picasso.with(mContext)
                .load(imageUrl).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImage);

        ((ItemViewHolder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailFilmActivity.class);
                StoreUtil.save(mContext,Contants.idFilm,dataAllFilm.getId());
                mContext.startActivity(i);

            }
        });
        ((ItemViewHolder) holder).imgDeleteFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirm);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtribute = window.getAttributes();
                window.setAttributes(windowAtribute);

                ProgressBar progressBar =dialog.findViewById(R.id.spin_kit);
                Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                Button btnLogout = dialog.findViewById(R.id.btn_confirm_delete);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // show dialog
                dialog.show();
                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ResponseDTO> loginResponeCall = ApiClient.getFilmService().deleteFilm(
                                StoreUtil.get(mContext, Contants.accessToken),idFilm);
                        loginResponeCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, retrofit2.Response<ResponseDTO> response) {
                                if (response.isSuccessful()) {
                                    DeleteImage.deleteImageFilm(dataAllFilm.getImageFilm().getPublicId(),mContext);
                                    DeleteVideo.deleteVideoFilm(dataAllFilm.getVideoFilm().getPublicId(),mContext);
                                    Circle foldingCube = new Circle();
                                    progressBar.setIndeterminateDrawable(foldingCube);
                                    progressBar.setVisibility(View.VISIBLE);
                                    CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            int current = progressBar.getProgress();
                                            if (current >= progressBar.getMax()) {
                                                current = 0;
                                            }
                                            progressBar.setProgress(current + 10);
                                        }

                                        @Override
                                        public void onFinish() {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            dialog.dismiss();

                                            testList.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                    };
                                    countDownTimer.start();
                                }
                            }
                            //
                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        if (testList != null){
            return testList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage, imgDeleteFilm;
        public TextView itemDes;
        public TextView itemTitle;
        public TextView itemLimitAge;
        public ConstraintLayout constraintLayout;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.hot_image);
            imgDeleteFilm = itemView.findViewById(R.id.img_delete_film);
            itemTitle = itemView.findViewById(R.id.tv_title_film);
            itemDes = itemView.findViewById(R.id.tv_description);
            itemLimitAge = itemView.findViewById(R.id.tv_limit_age);
            constraintLayout = itemView.findViewById(R.id.layout_thethao);
        }
    }

}
