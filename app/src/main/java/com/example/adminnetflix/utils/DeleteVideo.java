package com.example.adminnetflix.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.DeleteImageRequest;
import com.example.adminnetflix.models.request.DeleteVideoRequest;
import com.example.adminnetflix.models.response.ResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteVideo {

    public static void deleteVideoFilm(String public_id, Context context) {
        DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest(public_id);

        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteVideoFilm(
                StoreUtil.get(context, Contants.accessToken), deleteVideoRequest);
        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(context, "Delete video is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
