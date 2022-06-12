package com.example.adminnetflix.activities.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.ListDetailActivity;
import com.example.adminnetflix.adapters.ListFeedbackAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.FeedbackResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    RecyclerView rcvFeedback;
    ImageView imgBack;
    TextView tvTitle;
    private ListFeedbackAdapter listFeedbackAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initUi();
        getListFeedback();
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(FeedbackActivity.this);
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rcvFeedback.setLayoutManager(linearLayoutManagera);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvFeedback = findViewById(R.id.rcv_list_feedback);
    }

    private void getListFeedback() {
        Call<FeedbackResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllFeedback(
                StoreUtil.get(FeedbackActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                listFeedbackAdapter = new ListFeedbackAdapter(FeedbackActivity.this,response.body().getData());
                rcvFeedback.setAdapter(listFeedbackAdapter);
                listFeedbackAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFeedback();
    }


}