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
import com.example.adminnetflix.adapters.ListModeOfPaymentAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ModeOfPaymentResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeOfPaymentActivity extends AppCompatActivity {

    RecyclerView rcvModeOfPayment;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListModeOfPaymentAdapter listModeOfPaymentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_of_payment);
        initUi();
        getListModeOfPayment();
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(ModeOfPaymentActivity.this);
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rcvModeOfPayment.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvModeOfPayment.setLayoutManager(linearLayoutManagera);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeOfPaymentActivity.this, CreateActivity.class);
                intent.putExtra("btn","mode");
                startActivity(intent);
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvModeOfPayment = findViewById(R.id.rcv_list_mode);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListModeOfPayment() {
        Call<ModeOfPaymentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getModeOfPayment(
                StoreUtil.get(ModeOfPaymentActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ModeOfPaymentResponse>() {
            @Override
            public void onResponse(Call<ModeOfPaymentResponse> call, Response<ModeOfPaymentResponse> response) {
                listModeOfPaymentAdapter = new ListModeOfPaymentAdapter(ModeOfPaymentActivity.this,response.body().getData());
                rcvModeOfPayment.setAdapter(listModeOfPaymentAdapter);

            }

            @Override
            public void onFailure(Call<ModeOfPaymentResponse> call, Throwable t) {
                Toast.makeText(ModeOfPaymentActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListModeOfPayment();
    }
}