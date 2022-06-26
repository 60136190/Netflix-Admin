package com.example.adminnetflix.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.DeleteImageRequest;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteImage {
    public static void deleteImageModeofPayment(String public_id, Context context) {
        DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);

        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteImageModeOfPayment(
                StoreUtil.get(context, Contants.accessToken), deleteImageRequest);
        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(context, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void deleteImageDirector(String public_id, Context context) {
        DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);

        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteImageDirector(
                StoreUtil.get(context, Contants.accessToken), deleteImageRequest);
        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(context, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void deleteImageFilm(String public_id, Context context) {
        DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);

        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteImageFilm(
                StoreUtil.get(context, Contants.accessToken), deleteImageRequest);
        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(context, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
