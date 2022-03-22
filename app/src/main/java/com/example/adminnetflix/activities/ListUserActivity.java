package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUserActivity extends AppCompatActivity {

    private RecyclerView rcvListUser;
    private ListUserAdapter listUserAdapter;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        initUi();
        getListUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListUserActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListUser.setLayoutManager(linearLayoutManager);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initUi() {
        rcvListUser = findViewById(R.id.rcv_list_user);
        imgBack = findViewById(R.id.img_back);
    }
    private void getListUser() {
        Call<ListUserResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListUser(
                StoreUtil.get(ListUserActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                listUserAdapter = new ListUserAdapter(ListUserActivity.this,response.body().getData());
                rcvListUser.setAdapter(listUserAdapter);
                listUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Toast.makeText(ListUserActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}