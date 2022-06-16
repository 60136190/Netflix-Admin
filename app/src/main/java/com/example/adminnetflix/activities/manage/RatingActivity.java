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
import com.example.adminnetflix.adapters.ListRatingAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.RatingResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {

    RecyclerView rcvRating;
    ImageView imgBack;
    TextView tvTitle;
    private ListRatingAdapter ListRatingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating2);
        initUi();
        getListRating();
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(RatingActivity.this);
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rcvRating.setLayoutManager(linearLayoutManagera);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getListRating() {
        Call<RatingResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllRating(
                StoreUtil.get(RatingActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                ListRatingAdapter = new ListRatingAdapter(RatingActivity.this,response.body().getData());
                rcvRating.setAdapter(ListRatingAdapter);
                ListRatingAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                Toast.makeText(RatingActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_name_list);
        rcvRating = findViewById(R.id.rcv_list_rating);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListRating();
    }
}