package com.example.adminnetflix.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.utils.DeleteImage;
import com.example.adminnetflix.activities.UpdateActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.TestModel;
import com.example.adminnetflix.models.response.Director;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDirectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext1;
    List<Director> mDirectorList;
    private List<TestModel> taskList;
    ArrayList<String> listId = new ArrayList<>();

    public ListDirectorAdapter(Context mContext, List<Director> mDirectorList) {
        this.mContext1 = mContext;
        this.mDirectorList = mDirectorList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_director,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Director director = mDirectorList.get(position);
        String title = director.getName();
        String imgDirector = director.getImage().getUrl();

        ((ItemViewHolder) holder).itemNameOfDirector.setText(title);
        Picasso.with(mContext1)
                .load(imgDirector).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrl);

        ((ItemViewHolder) holder).lnDeleteDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
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

                Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                Button btnDelete = dialog.findViewById(R.id.btn_confirm_delete);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // show dialog
                dialog.show();
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ResponseDTO> listFavoriteFilmResponseCall = ApiClient.getFilmService().deleteDirector(
                                StoreUtil.get(view.getContext(), Contants.accessToken), director.getId());
                        listFavoriteFilmResponseCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                mDirectorList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                dialog.dismiss();
                                DeleteImage.deleteImageDirector(director.getImage().getPublicId(), mContext1);
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(view.getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
            }
        });

        ((ItemViewHolder) holder).ctListDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext1, UpdateActivity.class);
                intent.putExtra("update","Update Director");
                intent.putExtra("id_director",director.getId());
                intent.putExtra("name_director",director.getName());
                intent.putExtra("public_Id_director",director.getImage().getPublicId());
                intent.putExtra("url_director",director.getImage().getUrl());
                mContext1.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDirectorList != null){
            return mDirectorList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrl;
        private TextView itemNameOfDirector;
        private LinearLayout lnDeleteDirector;
        private ConstraintLayout ctListDirector;


        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_director);
            itemNameOfDirector = itemView.findViewById(R.id.tv_name_of_director);
            lnDeleteDirector = itemView.findViewById(R.id.ln_delete);
            ctListDirector = itemView.findViewById(R.id.ct_list_director);
        }
    }

}
