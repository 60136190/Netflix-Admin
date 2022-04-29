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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.DetailFilmResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilmActivity extends AppCompatActivity {

    private ImageView imgFilm;
    private TextView tvDirector;
    private TextView tvTitleFilm;
    private TextView tvStoryLine;
    private TextView tvDate;
    private TextView tvCategory;
    private TextView tvTimeFilm;
    private TextView tvCountry;
//    private RatingBar ratingBar;
    private RecyclerView recyclerViewComment;
    private RecyclerView rcvSeriesFilm;
//    private ListCommentFilmAdapter listCommentFilmAdapter;
//    private SeriesFilmAdapter seriesFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        initUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            String idFilm = (String) b.get("Id_film");
            Call<DetailFilmResponse> detailFilmResponseCall = ApiClient.getFilmService().detailFilm(
                    StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
            detailFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
                    if (response.isSuccessful()) {
                        tvTitleFilm.setText(response.body().getData().get(0).getTitle());
                        tvStoryLine.setText(response.body().getData().get(0).getDescription());
                        String urlVideoFilm = response.body().getData().get(0).getSeriesFilm().get(0).getUrlVideo();
                        for (int i = 0; i < response.body().getData().get(0).getDirector().size(); i++) {
                            tvDirector.setText(response.body().getData().get(0).getDirector().get(i).getName());
                        }

                        String strImgFilm = response.body().getData().get(0).getImageFilm().getUrl();
                        Picasso.with(DetailFilmActivity.this)
                                .load(strImgFilm).error(R.drawable.backgroundslider).fit().centerInside().into(imgFilm);
                        String string = response.body().getData().get(0).getYearProduction();
                        String[] parts = string.split("T");
                        String part1 = parts[0]; // 004

                        tvDate.setText(part1);
                        tvCategory.setText(response.body().getData().get(0).getCategory().get(0).getName());
                        tvTimeFilm.setText(response.body().getData().get(0).getFilmLength());
                        tvCountry.setText(response.body().getData().get(0).getCountryProduction());

                    }
                }

                @Override
                public void onFailure(Call<DetailFilmResponse> call, Throwable t) {

                }
            });


//            // get series film
//            Call<DetailFilmResponse> serieslFilmResponseCall = ApiClient.getFilmService().getSeries(
//                    StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
//            serieslFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
//                @Override
//                public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
//                    seriesFilmAdapter = new SeriesFilmAdapter(DetailFilmActivity.this, response.body().getData().get(0).getSeriesFilm());
//                    rcvSeriesFilm.setAdapter(seriesFilmAdapter);
//                    seriesFilmAdapter.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onFailure(Call<DetailFilmResponse> call, Throwable t) {
//
//                }
//            });
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailFilmActivity.this, 2);
//            rcvSeriesFilm.setLayoutManager(gridLayoutManager);


        }
    }
    private void initUi() {
        tvDirector = findViewById(R.id.tv_director);
        tvTitleFilm = findViewById(R.id.tv_title_film);
        tvStoryLine = findViewById(R.id.tv_storyline);
        tvDate = findViewById(R.id.tv_date_film);
        tvCategory = findViewById(R.id.tv_category);
        tvCountry = findViewById(R.id.tv_country);
        imgFilm = findViewById(R.id.img_film);
        tvTimeFilm = findViewById(R.id.tv_time_film);
        recyclerViewComment = findViewById(R.id.rcv_comment);
        rcvSeriesFilm = findViewById(R.id.rcv_series_film);
    }
}