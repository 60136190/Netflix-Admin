package com.example.adminnetflix.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.CreateFilmActivity;
import com.example.adminnetflix.activities.DetailVideoActivity;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.SeriesFilm;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.DeleteImage;
import com.example.adminnetflix.utils.DeleteVideo;
import com.example.adminnetflix.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<SeriesFilm> mseriesFilms;

    public SeriesFilmAdapter(Context mContext, List<SeriesFilm> mseriesFilms) {
        this.mContext = mContext;
        this.mseriesFilms = mseriesFilms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_series_film, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SeriesFilm seriesFilm = mseriesFilms.get(position);
        String imgFilm = seriesFilm.getUrlImage();
        String urlVideo = seriesFilm.getUrlVideo();
        String idFilm = StoreUtil.get(mContext,Contants.idFilm);
        int episode = seriesFilm.getEpisode();
        ((ItemViewHolder) holder).tvSeries.setText(String.valueOf("Episode "+seriesFilm.getEpisode()));
        Picasso.with(mContext)
                .load(imgFilm).fit().centerInside().into(((ItemViewHolder) holder).imgFilm);

        ((ItemViewHolder) holder).imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailVideoActivity.class);
                i.putExtra("video", urlVideo);
                i.putExtra("episode", episode);
                mContext.startActivity(i);
            }
        });
        ((ItemViewHolder) holder).imgDeleteSeriesFilm.setOnClickListener(new View.OnClickListener() {
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
                Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                Button btnDeleteSeries = dialog.findViewById(R.id.btn_confirm_delete);
                ProgressBar progressBar =dialog.findViewById(R.id.spin_kit);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // show dialog
                dialog.show();

                btnDeleteSeries.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ResponseDTO> responseFilm = ApiClient.getFilmService().deleteSeriesFilm(
                                StoreUtil.get(mContext, Contants.accessToken),idFilm,seriesFilm.getId());
                        responseFilm.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                if (response.isSuccessful()){
                                    // delete image series film
                                    DeleteImage.deleteImageFilm(seriesFilm.getPublicIdImage(),mContext);
                                    DeleteVideo.deleteVideoFilm(seriesFilm.getPublicIdVideo(),mContext);
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
                                            mseriesFilms.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                    };
                                    countDownTimer.start();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(mContext, "Maybe is wrong create film", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        });
    }


    @Override
    public int getItemCount() {
        if (mseriesFilms != null) {
            return mseriesFilms.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFilm, imgPlay, imgDeleteSeriesFilm;
        TextView tvSeries;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgFilm = itemView.findViewById(R.id.img_film);
            tvSeries = itemView.findViewById(R.id.tv_series_film);
            imgPlay = itemView.findViewById(R.id.img_play);
            imgDeleteSeriesFilm = itemView.findViewById(R.id.img_delete_series);
        }
    }

}
