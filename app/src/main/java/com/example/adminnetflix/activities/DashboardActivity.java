package com.example.adminnetflix.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.FilmRequest;
import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.SeriesFilm;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.models.response.UploadVideoResponse;
import com.example.adminnetflix.realpath.RealPathUtil;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    private Uri mUri;
    private Uri mUriVideo;
    RequestBody requestBody;
    RequestBody requestBodyVideo;

    private TextView tvPublic_video;
    private TextView tvUrl_video;
    private ImageView imgBack;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtYearProduction;
    private EditText edtCountryProduction;
    private EditText edtEpisode;
    private EditText edtAgeLimit;
    private EditText edtLenghtFilm;
    private EditText edtPrice;
    private ImageView imgFilm;
    private ImageView imgVideo;
    private ImageView imgEpisode;
    private Button btnCreate;
    private Spinner spinnerDirector;
    private Spinner spinnerCategory;


    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
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
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgFilm.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private ActivityResultLauncher<Intent> mActivityResultLauncherVideo = registerForActivityResult(
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
                        mUriVideo = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgVideo.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initUi();
        Spinner();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermissionImage();
            }
        });

        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermissionVideo();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFilm();
//                getVideo();
            }
        });


    }

    private void openImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    private void openVideoGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncherVideo.launch(Intent.createChooser(intent, "Select video"));
    }


    private void onClickRequestPermissionImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openImageGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openImageGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void onClickRequestPermissionVideo() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openVideoGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openVideoGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }


    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        edtTitle = findViewById(R.id.edt_title_film);
        edtDescription = findViewById(R.id.edt_description);
        edtYearProduction = findViewById(R.id.edt_year_production);
        edtCountryProduction = findViewById(R.id.edt_country_production);
        edtEpisode = findViewById(R.id.edt_episode);
        edtAgeLimit = findViewById(R.id.edt_age_limit);
        edtLenghtFilm = findViewById(R.id.edt_lenght_film);
        edtPrice = findViewById(R.id.edt_price);
        imgFilm = findViewById(R.id.img_film);
        imgVideo = findViewById(R.id.img_video);
        imgEpisode = findViewById(R.id.img_episode);
        btnCreate = findViewById(R.id.btn_create);
        tvPublic_video = findViewById(R.id.tv_public_video);
        tvUrl_video = findViewById(R.id.url_video);
        spinnerCategory = findViewById(R.id.spn_sort_category);
        spinnerDirector = findViewById(R.id.spn_sort_director);
    }

    private void createFilm() {
        getVideo();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {


                        // upload new image
                        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                        File fileImage = new File(strRealPath);
                        requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
                        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

                        Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageFilm(
                                StoreUtil.get(DashboardActivity.this, Contants.accessToken),
                                multipartBody);
                        responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                            @Override
                            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                                if (response.isSuccessful()) {
                                    String url = response.body().getUrl();
                                    String public_id = response.body().getPublic_id();
                                    Image image = new Image(public_id, url);

                                    String title = edtTitle.getText().toString();
                                    String description = edtDescription.getText().toString();
                                    String yearProduct = edtYearProduction.getText().toString();
                                    String countryProduction = edtCountryProduction.getText().toString();
                                    int ageLimit = Integer.parseInt(edtAgeLimit.getText().toString());
                                    int price = Integer.parseInt(edtPrice.getText().toString());
                                    int episode = Integer.parseInt(edtEpisode.getText().toString());
                                    String lengtFilm = edtLenghtFilm.getText().toString();

                                    SeriesFilm seriesFilm = new SeriesFilm(episode,
                                            tvPublic_video.getText().toString(),
                                            tvUrl_video.getText().toString(), public_id, url);

                                    List<String> list = new ArrayList<>();
                                    list.add("62049b07933677fbeffdc430");

                                    List<String> listCategory = new ArrayList<>();
                                    list.add("62049dda656d8c7511aaab77");

                                    List<SeriesFilm> seriesFilms = new ArrayList<>();
                                    seriesFilms.add(seriesFilm);

                                    FilmRequest filmRequest = new FilmRequest(title, description, yearProduct,
                                            countryProduction, image, list, listCategory,
                                            seriesFilms, ageLimit, lengtFilm, price);
                                    Call<ResponseDTO> responseFilm = ApiClient.getFilmService().createFilm(
                                            StoreUtil.get(DashboardActivity.this, Contants.accessToken), filmRequest);
                                    responseFilm.enqueue(new Callback<ResponseDTO>() {
                                        @Override
                                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                            Toast.makeText(DashboardActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                                Toast.makeText(DashboardActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },
                3000);
    }

    public void getVideo() {
        // upload new image
        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUriVideo);
        File fileImage = new File(strRealPath);
        requestBodyVideo = RequestBody.create(MediaType.parse(getContentResolver().getType(mUriVideo)), fileImage);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBodyVideo);
        Call<UploadVideoResponse> responseDTOCall = ApiClient.getFilmService().uploadVideoFilm(
                StoreUtil.get(DashboardActivity.this, Contants.accessToken),
                multipartBody);
        responseDTOCall.enqueue(new Callback<UploadVideoResponse>() {
            @Override
            public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {

                tvPublic_video.setText(response.body().getPublic_id());
                tvUrl_video.setText(response.body().getUrl());
            }

            @Override
            public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Spinner(){
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(DashboardActivity.this, Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                for(int i = 0; i< response.body().getData().size(); i++){
                    List<String> director = Arrays.asList(String.valueOf(response.body().getData().get(i).getName()));
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,director);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDirector.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}