package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListCommentFilmAdapter;
import com.example.adminnetflix.adapters.SeriesFilmAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.CheckFilmCanWatch;
import com.example.adminnetflix.models.response.CommentResponse;
import com.example.adminnetflix.models.response.DetailFilmResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilmActivity extends AppCompatActivity {

    private TextView tvDirector, tvStatus, tvTitleFilm,
            tvStoryLine, tvDate, tvCategory, tvTimeFilm, tvCountry;
    VideoView vdFilm;
    private RecyclerView rcvComment;
    private RecyclerView rcvSeriesFilm;
    ListCommentFilmAdapter listCommentFilmAdapter;
    SeriesFilmAdapter seriesFilmAdapter;
    Button btnCreateSeriesFilm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        initUi();
        String idFilm = StoreUtil.get(DetailFilmActivity.this,Contants.idFilm);
            getDetailFilm(idFilm);
            getListComment(idFilm);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailFilmActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvComment.setLayoutManager(linearLayoutManager);
            // get series film
            getSeriesFilm(idFilm);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailFilmActivity.this, 2);
            rcvSeriesFilm.setLayoutManager(gridLayoutManager);

            btnCreateSeriesFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailFilmActivity.this,AddSeriesFilmActivity.class);
                    startActivity(intent);
                }
            });

    }



    private void initUi() {
        tvDirector = findViewById(R.id.tv_director);
        tvTitleFilm = findViewById(R.id.tv_title_film);
        tvStoryLine = findViewById(R.id.tv_storyline);
        tvDate = findViewById(R.id.tv_date_film);
        tvCategory = findViewById(R.id.tv_category);
        tvCountry = findViewById(R.id.tv_country);
        tvTimeFilm = findViewById(R.id.tv_time_film);
        tvStatus = findViewById(R.id.tv_status);
        rcvComment = findViewById(R.id.rcv_comment);
        rcvSeriesFilm = findViewById(R.id.rcv_series_film);
        vdFilm = findViewById(R.id.video_film);
        btnCreateSeriesFilm = findViewById(R.id.btn_add_series_film);
    }

    private void getSeriesFilm(String idFilm) {
        Call<DetailFilmResponse> serieslFilmResponseCall = ApiClient.getFilmService().getSeries(
                StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
        serieslFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
            @Override
            public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
                if (response.body().getData()!= null){
                    seriesFilmAdapter = new SeriesFilmAdapter(DetailFilmActivity.this, response.body().getData().get(0).getSeriesFilm());
                    rcvSeriesFilm.setAdapter(seriesFilmAdapter);
                }else{
                    Toast.makeText(DetailFilmActivity.this, "Series film is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailFilmResponse> call, Throwable t) {

            }
        });
    }

    private void getListComment(String idFilm) {
        Call<CommentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCommentFollowFilm(
                StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
        listFavoriteFilmResponseCall.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                listCommentFilmAdapter = new ListCommentFilmAdapter(DetailFilmActivity.this, response.body().getData());
                rcvComment.setAdapter(listCommentFilmAdapter);
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
            }
        });
    }

    private void getDetailFilm(String idFilm){
        Call<DetailFilmResponse> detailFilmResponseCall = ApiClient.getFilmService().detailFilm(
                StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
        detailFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
            @Override
            public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
                if (response.isSuccessful()) {
                    tvTitleFilm.setText(response.body().getData().get(0).getTitle());
                    tvStoryLine.setText(response.body().getData().get(0).getDescription());

                    if (response.body().getData().get(0).getDirector().isEmpty()) {
                        tvDirector.setText("");
                    } else {
                        String delim = " •";
                        int i = 0;
                        StringBuilder str = new StringBuilder();
                        while (i < response.body().getData().get(0).getDirector().size()-1) {
                            str.append(response.body().getData().get(0).getDirector().get(i).getName());
                            str.append(delim);
                            i++;
                        }
                        str.append(response.body().getData().get(0).getDirector().get(i).getName());
                        String directors = str.toString();
                        tvDirector.setText(directors);
                    }

                    if (response.body().getData().get(0).getCategory().isEmpty()) {
                        tvCategory.setText("");
                    } else {
                        String delim = " •";

                        int i = 0;
                        StringBuilder str = new StringBuilder();
                        while (i < response.body().getData().get(0).getCategory().size()-1) {
                            str.append(response.body().getData().get(0).getCategory().get(i).getName());
                            str.append(delim);
                            i++;
                        }
                        str.append(response.body().getData().get(0).getCategory().get(i).getName());
                        String categorys = str.toString();
                        tvCategory.setText(categorys);

                    }

                    String path = response.body().getData().get(0).getVideoFilm().getUrl();
                    int cost = response.body().getData().get(0).getPrice();
                    if (cost== 0){
                        tvStatus.setText("Free");
                    }else{
                        tvStatus.setText("Cost");
                    }
                    vdFilm.setVideoPath(path);
                    vdFilm.start();
                    String string = response.body().getData().get(0).getYearProduction();
                    String[] parts = string.split("T");
                    String part1 = parts[0]; // 004

                    tvDate.setText(part1);
                    tvTimeFilm.setText(response.body().getData().get(0).getFilmLength());
                    tvCountry.setText(response.body().getData().get(0).getCountryProduction());

                }
            }

            @Override
            public void onFailure(Call<DetailFilmResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String idFilm = StoreUtil.get(DetailFilmActivity.this,Contants.idFilm);
        getDetailFilm(idFilm);
        getSeriesFilm(idFilm);
    }
}