package com.example.adminnetflix.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.RefreshTokenResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstScreenActivity extends AppCompatActivity {

    protected boolean _active = true;
    protected int _splashTime = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                SharedPreferences sharedPreferences = FirstScreenActivity.this.getSharedPreferences("AdminSharedPref", Context.MODE_PRIVATE);
                if (sharedPreferences.getString(Contants.accessToken, "").isEmpty()){
                    Intent i3 = new Intent(FirstScreenActivity.this, SliderActivity.class);
                    startActivity(i3);
                }else{
                    getReFreshToken();
                    Intent intentslide = new Intent(FirstScreenActivity.this, MainActivity.class);
                    startActivity(intentslide);
                    finish();
                }
            }
        }, _splashTime);
    }

    public void getReFreshToken(){
        Call<RefreshTokenResponse> refreshTokenResponeCall;
        refreshTokenResponeCall = ApiClient.getUserService().refreshToken(
                StoreUtil.get(FirstScreenActivity.this, Contants.refreshToken));
        refreshTokenResponeCall.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                StoreUtil.save(FirstScreenActivity.this, Contants.accessToken, response.body().getAccessToken());
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {

            }
        });
    }


}