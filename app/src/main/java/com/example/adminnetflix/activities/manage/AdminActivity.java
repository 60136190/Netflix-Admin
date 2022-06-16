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
import com.example.adminnetflix.adapters.ListAdminAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListAdminResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    RecyclerView rcvAdmin;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListAdminAdapter listAdminAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initUi();
        getListAdmin();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdminActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvAdmin.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvAdmin.setLayoutManager(linearLayoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreateActivity.class);
                intent.putExtra("btn","admin");
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

    private void getListAdmin() {
        Call<ListAdminResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListAdmin(
                StoreUtil.get(AdminActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListAdminResponse>() {
            @Override
            public void onResponse(Call<ListAdminResponse> call, Response<ListAdminResponse> response) {
                listAdminAdapter = new ListAdminAdapter(AdminActivity.this,response.body().getData());
                rcvAdmin.setAdapter(listAdminAdapter);
                listAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListAdminResponse> call, Throwable t) {
                Toast.makeText(AdminActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvAdmin = findViewById(R.id.rcv_list_admin);
        btnAdd = findViewById(R.id.btn_add);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getListAdmin();
    }

}