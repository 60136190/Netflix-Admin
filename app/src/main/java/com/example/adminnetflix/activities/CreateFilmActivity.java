package com.example.adminnetflix.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.SeriesFilm;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.models.response.UploadVideoResponse;
import com.example.adminnetflix.models.response.VideoFilm;
import com.example.adminnetflix.realpath.RealPathUtil;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;

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

public class CreateFilmActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    private Uri mUri;
    private Uri mUriVideo;
    RequestBody requestBody;
    RequestBody requestBodyVideo;
    private ListDirectorAdapter listDirectorAdapter;
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
    private RecyclerView rcv_choose_director;
    public static String public_idVideo;
    public static String url_Video;
    ArrayList<String> categoryList = new ArrayList<>();

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
        setContentView(R.layout.activity_create_film);
        initUi();
        Spinner();
        getListDirector();
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(CreateFilmActivity.this);
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_choose_director.setLayoutManager(linearLayoutManagera);

//        SpinnerListCategory();
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
//                Toast.makeText(CreateFilmActivity.this,sharedPreferences.getString(Contants.idDirector, ""),Toast.LENGTH_SHORT).show();
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
//        spinnerDirector = findViewById(R.id.spn_sort_director);
        rcv_choose_director = findViewById(R.id.rcv_choose_director);
    }

    private void createFilm() {
        getVideo();
        Toast.makeText(CreateFilmActivity.this,public_idVideo,Toast.LENGTH_SHORT).show();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {

                        // upload new image
                        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                        File fileImage = new File(strRealPath);
                        requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
                        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

                        Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageFilm(
                                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken),
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
                                    String lengtFilm = edtLenghtFilm.getText().toString() + " minutes";

                                    SeriesFilm seriesFilm = new SeriesFilm(episode,
                                            public_idVideo,url_Video,
                                            public_id, url);

                                    VideoFilm videoFilm = new VideoFilm(public_idVideo,url_Video);

                                    List<String> list = new ArrayList<>();
                                    list.add("62049b51933677fbeffdc436");
                                    list.add("620a1317b67785835eade8e6");

                                    List<String> listCategory = new ArrayList<>();
                                    listCategory.add("62049dda656d8c7511aaab77");

                                    List<SeriesFilm> seriesFilms = new ArrayList<>();
                                    seriesFilms.add(seriesFilm);


                                    FilmRequest filmRequest = new FilmRequest(title, description, yearProduct,
                                            countryProduction, image,image,videoFilm,list, listCategory,
                                            seriesFilms, ageLimit, lengtFilm, price);
                                    Call<ResponseDTO> responseFilm = ApiClient.getFilmService().createFilm(
                                            StoreUtil.get(CreateFilmActivity.this, Contants.accessToken), filmRequest);
                                    responseFilm.enqueue(new Callback<ResponseDTO>() {
                                        @Override
                                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                            Toast.makeText(CreateFilmActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                                Toast.makeText(CreateFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
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
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken),
                multipartBody);
        responseDTOCall.enqueue(new Callback<UploadVideoResponse>() {
            @Override
            public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {
                public_idVideo = response.body().getPublic_id();
                url_Video = response.body().getUrl();
//                tvPublic_video.setText(response.body().getPublic_id());
//                tvUrl_video.setText(response.body().getUrl());
            }

            @Override
            public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Spinner(){
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                    for (int i = 0; i < response.body().getData().toArray().length; i++) {
                        String data = response.body().getData().get(i).getId();
                        categoryList.add(data);
                        ArrayAdapter adapter = new ArrayAdapter(CreateFilmActivity.this, android.R.layout.simple_spinner_item, categoryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategory.setAdapter(adapter);
                    }
            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListDirector() {
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                listDirectorAdapter = new ListDirectorAdapter(CreateFilmActivity.this, response.body().getData());
                rcv_choose_director.setAdapter(listDirectorAdapter);
                listDirectorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}