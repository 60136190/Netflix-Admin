package com.example.adminnetflix.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.FilmRequest;
import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.SeriesFilm;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.models.response.UploadVideoResponse;
import com.example.adminnetflix.models.response.VideoFilm;
import com.example.adminnetflix.realpath.RealPathUtil;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSeriesFilmActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    RequestBody requestBodyImageSeries;
    RequestBody requestBodyVideoSeriesFilm;
    public static String  public_idVideo_Series_Film;
    public static String  url_Video_Series_Film;
    public static String url_ImageSeriesFilm, public_Id_ImageSeries;
    EditText edtEpisode;
    ProgressBar progressBar;
    ImageView imgImageSeries, imgVideoSeriesFilm, imgBack;
    Button btnCreateSeriesFilm;
    Uri mUriImageSeries;
    Uri mUriVideoSeriesFilm;

    private ActivityResultLauncher<Intent> mActivityResultLauncherImageSeries = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUriImageSeries = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgImageSeries.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    // get video up series film
    private ActivityResultLauncher<Intent> mActivityResultLauncherVideoSeriesFilm = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUriVideoSeriesFilm = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgVideoSeriesFilm.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series_film);
        intiUi();
        String idFilm = StoreUtil.get(AddSeriesFilmActivity.this,Contants.idFilm);
        imgVideoSeriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermissionVideoSeriesFilm();
            }
        });

        imgImageSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermissionImageSeries();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCreateSeriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEpisode.getText()==null || mUriVideoSeriesFilm==null || mUriImageSeries ==null){
                    Toast.makeText(AddSeriesFilmActivity.this, "Is empty", Toast.LENGTH_SHORT).show();
                }else{
                    createSeriesFilm(idFilm);

                }
            }
        });
    }
    // choose vide series film
    private void openVideoSeriesGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncherVideoSeriesFilm.launch(Intent.createChooser(intent, "Select video series film"));
    }

    // choose image series
    private void openImageSeriesGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncherImageSeries.launch(Intent.createChooser(intent, "Select picture"));
    }

    // permission choose series film
    private void onClickRequestPermissionVideoSeriesFilm() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openVideoSeriesGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openVideoSeriesGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    // permission choose image
    private void onClickRequestPermissionImageSeries() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openImageSeriesGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openImageSeriesGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }


    private void intiUi() {
        edtEpisode = findViewById(R.id.edt_episode);
        imgImageSeries = findViewById(R.id.img_series);
        imgVideoSeriesFilm = findViewById(R.id.img_video_series);
        btnCreateSeriesFilm = findViewById(R.id.btn_create);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        imgBack = findViewById(R.id.img_back);
    }



    public void createSeriesFilm(String idFilm) {
        btnCreateSeriesFilm.setVisibility(View.INVISIBLE);
        Sprite cubeGrid = new Circle();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.VISIBLE);
        // upload new image
        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUriImageSeries);
        File fileImage = new File(strRealPath);
        requestBodyImageSeries = RequestBody.create(MediaType.parse(getContentResolver().getType(mUriImageSeries)), fileImage);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBodyImageSeries);
        Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageFilm(
                StoreUtil.get(AddSeriesFilmActivity.this, Contants.accessToken),
                multipartBody);
        responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                public_Id_ImageSeries = response.body().getPublic_id();
                url_ImageSeriesFilm = response.body().getUrl();
                // upload video
                String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUriVideoSeriesFilm);
                File fileVideo = new File(strRealPath);
                requestBodyVideoSeriesFilm = RequestBody.create(MediaType.parse(getContentResolver().getType(mUriVideoSeriesFilm)), fileVideo);
                MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileVideo.getName(), requestBodyVideoSeriesFilm);
                Call<UploadVideoResponse> responseDTOCall = ApiClient.getFilmService().uploadVideoFilm(
                        StoreUtil.get(AddSeriesFilmActivity.this, Contants.accessToken),
                        multipartBody);
                responseDTOCall.enqueue(new Callback<UploadVideoResponse>() {
                    @Override
                    public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {
                        public_idVideo_Series_Film = response.body().getPublic_id();
                        url_Video_Series_Film = response.body().getUrl();

                        // upload film
                        int episode = Integer.parseInt(edtEpisode.getText().toString());
                        SeriesFilm seriesFilm = new SeriesFilm(episode,
                                public_idVideo_Series_Film
                                ,url_Video_Series_Film,
                                public_Id_ImageSeries, url_ImageSeriesFilm);
                        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().createSeriesFilm(
                                StoreUtil.get(AddSeriesFilmActivity.this, Contants.accessToken),idFilm,seriesFilm);
                        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                if (response.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(AddSeriesFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                        Toast.makeText(AddSeriesFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Toast.makeText(AddSeriesFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}