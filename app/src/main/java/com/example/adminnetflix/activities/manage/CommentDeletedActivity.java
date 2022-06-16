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
import com.example.adminnetflix.adapters.ListCommentDeletedAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.comment.CommentDeletedResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDeletedActivity extends AppCompatActivity {

    RecyclerView rcvComment;
    ImageView imgBack;
    TextView tvTitle;
    Button btnAdd;
    private ListCommentDeletedAdapter listCommentDeletedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initUi();
        getListComment();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommentDeletedActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvComment.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
        rcvComment.setLayoutManager(linearLayoutManager);

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
        rcvComment = findViewById(R.id.rcv_list_comment);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListComment() {
        Call<CommentDeletedResponse> commentDeletedResponseCall = ApiClient.getFilmService().getAllCommentDeleted(
                StoreUtil.get(CommentDeletedActivity.this, Contants.accessToken));
        commentDeletedResponseCall.enqueue(new Callback<CommentDeletedResponse>() {
            @Override
            public void onResponse(Call<CommentDeletedResponse> call, Response<CommentDeletedResponse> response) {
                listCommentDeletedAdapter = new ListCommentDeletedAdapter(CommentDeletedActivity.this,response.body().getTrash());
                rcvComment.setAdapter(listCommentDeletedAdapter);
            }

            @Override
            public void onFailure(Call<CommentDeletedResponse> call, Throwable t) {
                Toast.makeText(CommentDeletedActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListComment();
    }
}