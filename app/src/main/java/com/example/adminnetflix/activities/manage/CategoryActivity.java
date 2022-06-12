package com.example.adminnetflix.activities.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.CreateActivity;
import com.example.adminnetflix.activities.ListDetailActivity;
import com.example.adminnetflix.adapters.ListCategoriesFilmAdapter;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rcvCategory;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListCategoriesFilmAdapter listCategoriesFilmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initUi();
        getListCategory();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CategoryActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCategory.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvCategory.setLayoutManager(linearLayoutManager);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CreateActivity.class);
                intent.putExtra("btn","category");
                startActivity(intent);
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvCategory = findViewById(R.id.rcv_category);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListCategory() {
        Call<ListCategories> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListCategoriesFilm(
                StoreUtil.get(CategoryActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                listCategoriesFilmAdapter = new ListCategoriesFilmAdapter(CategoryActivity.this,response.body().getData());
                rcvCategory.setAdapter(listCategoriesFilmAdapter);
                listCategoriesFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListCategory();
    }
}