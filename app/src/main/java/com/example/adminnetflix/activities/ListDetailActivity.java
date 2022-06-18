package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListAdminAdapter;
import com.example.adminnetflix.adapters.ListCategoriesFilmAdapter;
import com.example.adminnetflix.adapters.ListCommentDeletedAdapter;
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.adapters.ListFavoritelFilmAdapter;
import com.example.adminnetflix.adapters.ListFeedbackAdapter;
import com.example.adminnetflix.adapters.ListModeOfPaymentAdapter;
import com.example.adminnetflix.adapters.ListRatingAdapter;
import com.example.adminnetflix.adapters.ListUserAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.FeedbackResponse;
import com.example.adminnetflix.models.response.ListAdminResponse;
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.models.response.ModeOfPaymentResponse;
import com.example.adminnetflix.models.response.RatingResponse;
import com.example.adminnetflix.models.response.comment.CommentDeletedResponse;
import com.example.adminnetflix.models.response.favourite.FavouriteResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDetailActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvTitle;
    private Button btnAdd;
    private RecyclerView rcvListDetail;
    private ListDirectorAdapter listDirectorAdapter;
    private ListModeOfPaymentAdapter listModeOfPaymentAdapter;
    private ListFeedbackAdapter listFeedbackAdapter;
    private ListRatingAdapter ListRatingAdapter;
    private ListAdminAdapter listAdminAdapter;
    private ListUserAdapter listUserAdapter;
    private ListCategoriesFilmAdapter listCategoriesFilmAdapter;
    private ListCommentDeletedAdapter listCommentDeletedAdapter;
    private ListFavoritelFilmAdapter listFavoritelFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        initUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if(b.get("manager").equals("0")){
            tvTitle.setText("List User");
            getListUser();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManager);
        }

        if(b.get("manager").equals("1")){
            tvTitle.setText("List Admin");
            getListAdmin();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManager);
        }

        if(b.get("manager").equals("2")){
            tvTitle.setText("List Director");
            getListDirector();
            LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManagera);
        }

        if (b.get("manager").equals("3")){
            tvTitle.setText("List Rating");
            getListRating();
            LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManagera);
        }

        if(b.get("manager").equals("4")){
            tvTitle.setText("List Feedback");
            btnAdd.setVisibility(View.INVISIBLE);
            getListFeedback();
            LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setLayoutManager(linearLayoutManagera);
        }

        if(b.get("manager").equals("5")){
            tvTitle.setText("List Mode of Payment");
            getListModeOfPaymet();
            LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManagera);
        }

        if(b.get("manager").equals("6")){
            tvTitle.setText("List Category");
            btnAdd.setVisibility(View.VISIBLE);
            getListCategoriesFilm();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManager);
        }

        if (b.get("manager").equals("7")){

            tvTitle.setText("List comment deleted");

            btnAdd.setVisibility(View.VISIBLE);
            getListCommnetDeleted();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManager);
        }

        if (b.get("manager").equals("list_favourite")){
            tvTitle.setText("List favourite film");
            btnAdd.setVisibility(View.VISIBLE);
            getListFavouriteFilm();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListDetailActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvListDetail.setOnTouchListener(new TranslateAnimationUtil(this,btnAdd));
            rcvListDetail.setLayoutManager(linearLayoutManager);
        }

        if (b.get("manager").equals("history bill")){
            tvTitle.setText("List history bill");

        }



        String value = b.get("manager").toString();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDetailActivity.this, CreateActivity.class);
                intent.putExtra("btn",value);
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
        rcvListDetail = findViewById(R.id.rcv_list_detail);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void getListDirector() {
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                listDirectorAdapter = new ListDirectorAdapter(ListDetailActivity.this, response.body().getData());
                rcvListDetail.setAdapter(listDirectorAdapter);
                listDirectorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListModeOfPaymet() {
        Call<ModeOfPaymentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getModeOfPayment(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ModeOfPaymentResponse>() {
            @Override
            public void onResponse(Call<ModeOfPaymentResponse> call, Response<ModeOfPaymentResponse> response) {
                listModeOfPaymentAdapter = new ListModeOfPaymentAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(listModeOfPaymentAdapter);

            }

            @Override
            public void onFailure(Call<ModeOfPaymentResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListFeedback() {
        Call<FeedbackResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllFeedback(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                listFeedbackAdapter = new ListFeedbackAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(listFeedbackAdapter);
                listFeedbackAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListRating() {
        Call<RatingResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllRating(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                ListRatingAdapter = new ListRatingAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(ListRatingAdapter);
                ListRatingAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListAdmin() {
        Call<ListAdminResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListAdmin(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListAdminResponse>() {
            @Override
            public void onResponse(Call<ListAdminResponse> call, Response<ListAdminResponse> response) {
                listAdminAdapter = new ListAdminAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(listAdminAdapter);
                listAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListAdminResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListUser() {
        Call<ListUserResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListUser(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                listUserAdapter = new ListUserAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(listUserAdapter);
                listUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListCategoriesFilm() {
        Call<ListCategories> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListCategoriesFilm(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                listCategoriesFilmAdapter = new ListCategoriesFilmAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(listCategoriesFilmAdapter);
                listCategoriesFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListCommnetDeleted(){
        Call<CommentDeletedResponse> commentDeletedResponseCall = ApiClient.getFilmService().getAllCommentDeleted(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        commentDeletedResponseCall.enqueue(new Callback<CommentDeletedResponse>() {
            @Override
            public void onResponse(Call<CommentDeletedResponse> call, Response<CommentDeletedResponse> response) {
                listCommentDeletedAdapter = new ListCommentDeletedAdapter(ListDetailActivity.this,response.body().getTrash());
                rcvListDetail.setAdapter(listCommentDeletedAdapter);
            }

            @Override
            public void onFailure(Call<CommentDeletedResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListFavouriteFilm() {
        Call<FavouriteResponse> commentDeletedResponseCall = ApiClient.getFilmService().getListFavouriteFilm(
                StoreUtil.get(ListDetailActivity.this, Contants.accessToken));
        commentDeletedResponseCall.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                listFavoritelFilmAdapter = new ListFavoritelFilmAdapter(ListDetailActivity.this,response.body().getData());
                rcvListDetail.setAdapter(listFavoritelFilmAdapter);
            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                Toast.makeText(ListDetailActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}