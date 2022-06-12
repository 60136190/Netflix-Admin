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
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectorActivity extends AppCompatActivity {

    RecyclerView rcvDirector;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListDirectorAdapter listDirectorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director);
        initUi();
        getListDirector();
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(DirectorActivity.this);
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDirector.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvDirector.setLayoutManager(linearLayoutManagera);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DirectorActivity.this, CreateActivity.class);
                intent.putExtra("btn","director");
                startActivity(intent);
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvDirector = findViewById(R.id.rcv_list_director);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListDirector() {
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(DirectorActivity.this, Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                listDirectorAdapter = new ListDirectorAdapter(DirectorActivity.this, response.body().getData());
                rcvDirector.setAdapter(listDirectorAdapter);
                listDirectorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(DirectorActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListDirector();
    }
}