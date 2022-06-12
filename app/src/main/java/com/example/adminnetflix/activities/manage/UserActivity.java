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
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    RecyclerView rcvUser;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListUserAdapter listUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initUi();
        getListUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvUser.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvUser.setLayoutManager(linearLayoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, CreateActivity.class);
                intent.putExtra("btn","user");
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvUser = findViewById(R.id.rcv_list_detail);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListUser() {
        Call<ListUserResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListUser(
                StoreUtil.get(UserActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                listUserAdapter = new ListUserAdapter(UserActivity.this,response.body().getData());
                rcvUser.setAdapter(listUserAdapter);
                listUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Toast.makeText(UserActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListUser();
    }
}