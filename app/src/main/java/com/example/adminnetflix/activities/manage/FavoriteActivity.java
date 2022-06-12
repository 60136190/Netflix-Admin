package com.example.adminnetflix.activities.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.ListDetailActivity;
import com.example.adminnetflix.adapters.ListFavoritelFilmAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.favourite.FavouriteResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView rcvFavorite;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListFavoritelFilmAdapter listFavoritelFilmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initUi();
        getListFavouriteFilm();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoriteActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvFavorite.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvFavorite.setLayoutManager(linearLayoutManager);
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvFavorite = findViewById(R.id.rcv_list_favorite);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListFavouriteFilm() {
        Call<FavouriteResponse> commentDeletedResponseCall = ApiClient.getFilmService().getListFavouriteFilm(
                StoreUtil.get(FavoriteActivity.this, Contants.accessToken));
        commentDeletedResponseCall.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                listFavoritelFilmAdapter = new ListFavoritelFilmAdapter(FavoriteActivity.this,response.body().getData());
                rcvFavorite.setAdapter(listFavoritelFilmAdapter);
            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}