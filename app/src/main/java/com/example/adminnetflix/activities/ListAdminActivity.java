package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListAdminAdapter;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListAdminResponse;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdminActivity extends AppCompatActivity {

    private RecyclerView rcvListAdmin;
    private ListAdminAdapter listAdminAdapter;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);
        initUi();
        getListAdmin();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAdminActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListAdmin.setLayoutManager(linearLayoutManager);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initUi() {
        rcvListAdmin = findViewById(R.id.rcv_list_admin);
        imgBack = findViewById(R.id.img_back);
    }

    private void getListAdmin() {
        Call<ListAdminResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListAdmin(
                StoreUtil.get(ListAdminActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListAdminResponse>() {
            @Override
            public void onResponse(Call<ListAdminResponse> call, Response<ListAdminResponse> response) {
                listAdminAdapter = new ListAdminAdapter(ListAdminActivity.this,response.body().getData());
                rcvListAdmin.setAdapter(listAdminAdapter);
                listAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListAdminResponse> call, Throwable t) {
                Toast.makeText(ListAdminActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}