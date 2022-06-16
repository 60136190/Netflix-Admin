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
import com.example.adminnetflix.adapters.ListCustomerUncheckAdapter;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.CustomerUncheckResponse;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerUncheckActivity extends AppCompatActivity {

    RecyclerView rcvCustomerUncheck;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListCustomerUncheckAdapter listCustomerUncheckAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_uncheck);
        initUi();
        getListCustomerUncheck();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CustomerUncheckActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCustomerUncheck.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvCustomerUncheck.setLayoutManager(linearLayoutManager);

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
        rcvCustomerUncheck = findViewById(R.id.rcv_list_customer_uncheck);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListCustomerUncheck() {
        Call<CustomerUncheckResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListCustomerUncheck();
        listFavoriteFilmResponseCall.enqueue(new Callback<CustomerUncheckResponse>() {
            @Override
            public void onResponse(Call<CustomerUncheckResponse> call, Response<CustomerUncheckResponse> response) {
                listCustomerUncheckAdapter = new ListCustomerUncheckAdapter(CustomerUncheckActivity.this,response.body().getData());
                rcvCustomerUncheck.setAdapter(listCustomerUncheckAdapter);
            }

            @Override
            public void onFailure(Call<CustomerUncheckResponse> call, Throwable t) {
                Toast.makeText(CustomerUncheckActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListCustomerUncheck();
    }
}