package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListCategoriesFilmAdapter;
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private ImageView imgBack;
    private RecyclerView rcvListCategories;
    private ListCategoriesFilmAdapter listCategoriesFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initUi();
        getListCategoriesFilm();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListCategories.setLayoutManager(linearLayoutManager);



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        rcvListCategories = findViewById(R.id.rcv_list_categories);
    }

    private void getListCategoriesFilm() {
        Call<ListCategories> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListCategoriesFilm(
                StoreUtil.get(DashboardActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                listCategoriesFilmAdapter = new ListCategoriesFilmAdapter(DashboardActivity.this,response.body().getData());
                rcvListCategories.setAdapter(listCategoriesFilmAdapter);
                listCategoriesFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


}