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
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.manage.CategoryActivity;
import com.example.adminnetflix.adapters.ListCategoriesCreateFilmAdapter;
import com.example.adminnetflix.adapters.ListCategoriesFilmAdapter;
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.adapters.ListDirectorCreateFilmAdapter;
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
import com.example.adminnetflix.utils.HideKeyBoard;
import com.example.adminnetflix.utils.StoreUtil;
import com.example.adminnetflix.utils.TranslateAnimationUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFilmActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    Uri mUri;
    Uri mUriImageSeries;
    Uri mUriVideo;
    Uri mUriVideoSeriesFilm;
    RequestBody requestBody;
    RequestBody requestBodyImageSeries;
    RequestBody requestBodyVideo;
    RequestBody requestBodyVideoSeriesFilm;
    private ListDirectorCreateFilmAdapter listDirectorAdapter;
    private ListCategoriesCreateFilmAdapter listCategoriesFilmAdapter;
    ImageView imgBack;
    EditText edtTitle,edtDescription, edtYearProduction, edtCountryProduction, edtEpisode
            ,edtAgeLimit, edtLenghtFilm, edtPrice;
    ProgressBar progressBar;
    ImageView imgFilm, imgSeriesFilm;
    ImageView imgVideo,imgVideoSeriesFilm;
    Button btnCreate;
    RecyclerView rcv_choose_director, rcv_choose_category;
    public static String public_idVideo, public_idVideo_Series_Film;
    public static String url_Video, url_Video_Series_Film;
    public static String url_ImageSeriesFilm, public_Id_ImageSeries;
    ArrayList<String> categoryList = new ArrayList<>();
    List<String> listDirector;
    List<String> listCategory;
    SharedPreferences sharedPreferences;

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
                            imgSeriesFilm.setImageBitmap(bitmap);
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

    boolean barIsShowing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_film);
        HideKeyBoard.hide(CreateFilmActivity.this);
        listDirector = new ArrayList<>();
        listCategory = new ArrayList<>();
        initUi();
        getListDirector();
        getListCategory();

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

        imgVideoSeriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermissionVideoSeriesFilm();
            }
        });

        imgSeriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermissionImageSeries();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFilm();
            }
        });



    }

    // choose image film
    private void openImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    // choose image series
    private void openImageSeriesGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncherImageSeries.launch(Intent.createChooser(intent, "Select picture"));
    }

    // choose vide trailer film
    private void openVideoGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncherVideo.launch(Intent.createChooser(intent, "Select video"));
    }

    // choose vide series film
    private void openVideoSeriesGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncherVideoSeriesFilm.launch(Intent.createChooser(intent, "Select video series film"));
    }

    // permission choose image
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

    // permission choose video trailer
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

        progressBar = (ProgressBar) findViewById(R.id.spin_kit);

        imgFilm = findViewById(R.id.img_film);
        imgVideo = findViewById(R.id.img_video);
        imgSeriesFilm = findViewById(R.id.img_series);
        imgVideoSeriesFilm = findViewById(R.id.img_video_series);

        btnCreate = findViewById(R.id.btn_create);
        rcv_choose_director = findViewById(R.id.rcv_choose_director);
        rcv_choose_category = findViewById(R.id.rcv_choose_category);
    }

    private void createFilm() {
        getVideo();
        getVideoSeriesFilm();
        getImageSeriesFilm();

        btnCreate.setVisibility(View.INVISIBLE);
        Sprite cubeGrid = new Circle();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax()) {
                    current = 0;
                }
                progressBar.setProgress(current + 10);
            }

            @Override
            public void onFinish() {
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
                                    public_idVideo_Series_Film
                                    ,url_Video_Series_Film,
                                    public_Id_ImageSeries, url_ImageSeriesFilm);

                            VideoFilm videoFilm = new VideoFilm(public_idVideo,url_Video);

                            // get list director and category from shared preference
                            SharedPreferences sharedPreferences = getSharedPreferences("AdminSharedPref", MODE_PRIVATE);
                            Gson gson = new Gson();
                            String json = sharedPreferences.getString("list", null);
                            String jsonListCategory = sharedPreferences.getString("listCategory", null);
                            Type type = new TypeToken<ArrayList<String>>() {}.getType();
                            listDirector = gson.fromJson(json, type);
                            listCategory = gson.fromJson(jsonListCategory, type);

                            Log.i("hahaha", String.valueOf(listDirector));

                            List<SeriesFilm> seriesFilms = new ArrayList<>();
                            seriesFilms.add(seriesFilm);

                            FilmRequest filmRequest = new FilmRequest(title, description, yearProduct,
                                    countryProduction, image,image,videoFilm,listDirector, listCategory,
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
                progressBar.setVisibility(View.INVISIBLE);
                onBackPressed();
            }

        };
        countDownTimer.start();
    }

    public void getImageSeriesFilm() {
        // upload new image
        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUriImageSeries);
        File fileImage = new File(strRealPath);
        requestBodyImageSeries = RequestBody.create(MediaType.parse(getContentResolver().getType(mUriImageSeries)), fileImage);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBodyImageSeries);
        Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageFilm(
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken),
                multipartBody);
        responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                public_Id_ImageSeries = response.body().getPublic_id();
                url_ImageSeriesFilm = response.body().getUrl();
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getVideo() {
        // upload new video
        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUriVideo);
        File fileVideo = new File(strRealPath);
        requestBodyVideo = RequestBody.create(MediaType.parse(getContentResolver().getType(mUriVideo)), fileVideo);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileVideo.getName(), requestBodyVideo);
        Call<UploadVideoResponse> responseDTOCall = ApiClient.getFilmService().uploadVideoFilm(
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken),
                multipartBody);
        responseDTOCall.enqueue(new Callback<UploadVideoResponse>() {
            @Override
            public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {
                public_idVideo = response.body().getPublic_id();
                url_Video = response.body().getUrl();
            }

            @Override
            public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getVideoSeriesFilm() {
        // upload new image
        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUriVideoSeriesFilm);
        File fileVideo = new File(strRealPath);
        requestBodyVideoSeriesFilm = RequestBody.create(MediaType.parse(getContentResolver().getType(mUriVideoSeriesFilm)), fileVideo);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileVideo.getName(), requestBodyVideoSeriesFilm);
        Call<UploadVideoResponse> responseDTOCall = ApiClient.getFilmService().uploadVideoFilm(
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken),
                multipartBody);
        responseDTOCall.enqueue(new Callback<UploadVideoResponse>() {
            @Override
            public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {
                public_idVideo_Series_Film = response.body().getPublic_id();
                url_Video_Series_Film = response.body().getUrl();
            }

            @Override
            public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void Spinner(){
//        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
//                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken));
//        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
//            @Override
//            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
//                    for (int i = 0; i < response.body().getData().toArray().length; i++) {
//                        String data = response.body().getData().get(i).getId();
//                        categoryList.add(data);
//                        ArrayAdapter adapter = new ArrayAdapter(CreateFilmActivity.this, android.R.layout.simple_spinner_item, categoryList);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spinnerCategory.setAdapter(adapter);
//                    }
//            }
//
//            @Override
//            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
//                Toast.makeText(CreateFilmActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getListDirector() {
        Call<ListDirectorResponse> listDirectorResponseCall = ApiClient.getFilmService().getListDirector(
                StoreUtil.get(CreateFilmActivity.this, Contants.accessToken));
        listDirectorResponseCall.enqueue(new Callback<ListDirectorResponse>() {
            @Override
            public void onResponse(Call<ListDirectorResponse> call, Response<ListDirectorResponse> response) {
                listDirectorAdapter = new ListDirectorCreateFilmAdapter(CreateFilmActivity.this, response.body().getData());
                rcv_choose_director.setAdapter(listDirectorAdapter);
                LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(CreateFilmActivity.this);
                linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
                rcv_choose_director.setLayoutManager(linearLayoutManagera);
            }

            @Override
            public void onFailure(Call<ListDirectorResponse> call, Throwable t) {
                Toast.makeText(CreateFilmActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListCategory() {
            Call<ListCategories> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListCategoriesFilm(
                    StoreUtil.get(CreateFilmActivity.this, Contants.accessToken));
            listFavoriteFilmResponseCall.enqueue(new Callback<ListCategories>() {
                @Override
                public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                    listCategoriesFilmAdapter = new ListCategoriesCreateFilmAdapter(CreateFilmActivity.this,response.body().getData());
                    rcv_choose_category.setAdapter(listCategoriesFilmAdapter);LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(CreateFilmActivity.this);
                    linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
                    rcv_choose_category.setLayoutManager(linearLayoutManagera);

                }

                @Override
                public void onFailure(Call<ListCategories> call, Throwable t) {
                    Toast.makeText(CreateFilmActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void fadeDirector(View view){
        Log.i("Info","selected");
        ImageView view1 = findViewById(R.id.img_change);
        ImageView view2 = findViewById(R.id.img_changed);
        if (barIsShowing){
            barIsShowing = false;
            view1.animate().alpha(0).setDuration(500);
            view2.animate().alpha(1).setDuration(500);
            rcv_choose_director.setVisibility(View.VISIBLE);

        }else{
            barIsShowing = true;
            view1.animate().alpha(1).setDuration(500);
            view2.animate().alpha(0).setDuration(500);
            rcv_choose_director.setVisibility(View.GONE);

        }
    }

    public void fadeCategory(View view){
        Log.i("Info","selected");
        ImageView view1 = findViewById(R.id.img_change_category);
        ImageView view2 = findViewById(R.id.img_changed_category);
        if (barIsShowing){
            barIsShowing = false;
            view1.animate().alpha(0).setDuration(500);
            view2.animate().alpha(1).setDuration(500);
            rcv_choose_category.setVisibility(View.VISIBLE);

        }else{
            barIsShowing = true;
            view1.animate().alpha(1).setDuration(500);
            view2.animate().alpha(0).setDuration(500);
            rcv_choose_category.setVisibility(View.GONE);

        }

    }
    public void setProgressBar(){
        btnCreate.setVisibility(View.INVISIBLE);
        Sprite cubeGrid = new Circle();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax()) {
                    current = 0;
                }
                progressBar.setProgress(current + 10);
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                finish();
            }

        };
        countDownTimer.start();
    }
}