package com.example.adminnetflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationAdminActivity extends AppCompatActivity {

    private Button btnOpenUpdateUser;
    private ImageView imgUser;
    private TextView tvFullName;
    private TextView tvPhoneNumber;
    private TextView tvSex;
    private TextView tvDateOfBirth;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_admin);
        getProfile();
        initUi();
        btnOpenUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationAdminActivity.this,UpdateInformationAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUi() {
        btnOpenUpdateUser = findViewById(R.id.btn_open_update);
        imgUser = findViewById(R.id.img_user);
        tvFullName = findViewById(R.id.tv_full_name_user);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvSex = findViewById(R.id.tv_sex);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        tvEmail = findViewById(R.id.tv_email);
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(InformationAdminActivity.this, Contants.accessToken));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                String fullName = response.body().getUser().getFullname();
                String email = response.body().getUser().getEmail();
                String im = response.body().getUser().getImage().getUrl();
                String date_of_birth = response.body().getUser().getDateOfBirth();
                String sdt = response.body().getUser().getPhoneNumber();

                tvFullName.setText(fullName);
                tvEmail.setText(email);
                tvDateOfBirth.setText(date_of_birth);
                tvPhoneNumber.setText(sdt);

                if (im.isEmpty()) {
                    imgUser.setImageResource(R.drawable.backgroundslider);
                }else{
                    Glide.with(getApplicationContext())
                            .load(im)
                            .into(imgUser);
                }
                if (response.body().getUser().getSex() == 1) {
                    tvSex.setText("Male");
                } else {
                    tvSex.setText("Female");

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }
}