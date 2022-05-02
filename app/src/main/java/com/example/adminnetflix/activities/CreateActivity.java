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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.CategoryRequest;
import com.example.adminnetflix.models.request.ModeOfPaymentRequest;
import com.example.adminnetflix.models.request.UpdateDirectorRequest;
import com.example.adminnetflix.models.request.UpdateDirectorWithoutImage;
import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.realpath.RealPathUtil;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    private Uri mUri;
    RequestBody requestBody;

    // category
    private EditText edtCategory;
    private TextView tvTitleCreate;
    private TextView tvCategory;

    // director
    private TextView tvDirector;
    private TextView tvDescription;
    private EditText edtNameOfDirector;
    private EditText edtDescription;
    private ImageView imgDirctor;

    // mode of payment
    private TextView tvModeOfPayment;
    private EditText edtModeOfPayment;
    private ImageView imgModeOfPayment;


    private Button btnCreate;
    private ImageView imgBack;

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
                            imgDirctor.setImageBitmap(bitmap);
                            imgModeOfPayment.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initUi();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();


        // create category film
        if (b.get("btn").equals("List Category")) {
            tvCategory.setVisibility(View.VISIBLE);
            edtCategory.setVisibility(View.VISIBLE);

            tvTitleCreate.setText("Create Category");
            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createCategory();
                }
            });
        }

        // create admin
        if (b.get("btn").equals("List Admin")) {
            tvTitleCreate.setText("Create Admin");

        }

        // create user
        if (b.get("btn").equals("List User")){

        }

        // create director
        if (b.get("btn").equals("List Director")){
            tvDirector.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
            edtNameOfDirector.setVisibility(View.VISIBLE);
            edtDescription.setVisibility(View.VISIBLE);
            imgDirctor.setVisibility(View.VISIBLE);

            tvTitleCreate.setText("Create Director");

            imgDirctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickRequestPermission();
                }
            });

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createDirector();
                }
            });
        }

        // create mode of payment
        if (b.get("btn").equals("List Mode of Payment")){
            tvTitleCreate.setText("Create Mode of Payment");
            tvModeOfPayment.setVisibility(View.VISIBLE);
            edtModeOfPayment.setVisibility(View.VISIBLE);
            imgModeOfPayment.setVisibility(View.VISIBLE);

            imgModeOfPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickRequestPermission();
                }
            });

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createModeOfPayment();
                }
            });
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void initUi() {
        tvTitleCreate = findViewById(R.id.tv_title_create);

        edtCategory = findViewById(R.id.edt_name_of_category);
        tvCategory = findViewById(R.id.tv_name_of_category);

        tvDirector = findViewById(R.id.tv_name_of_director);
        tvDescription = findViewById(R.id.tv_description_director);
        edtNameOfDirector = findViewById(R.id.edt_nname_of_director);
        edtDescription = findViewById(R.id.edt_description_director);
        imgDirctor = findViewById(R.id.img_direction);

        tvModeOfPayment = findViewById(R.id.tv_name_of_payment);
        edtModeOfPayment = findViewById(R.id.edt_name_of_payment);
        imgModeOfPayment = findViewById(R.id.img_mode_of_payment);

        imgBack = findViewById(R.id.img_back);
        btnCreate = findViewById(R.id.btn_create);
    }

    private void createCategory() {
        if (edtCategory.getText() == null) {
            Toast.makeText(CreateActivity.this, "Category is not null", Toast.LENGTH_SHORT).show();
        } else {
            CategoryRequest category = new CategoryRequest(edtCategory.getText().toString());
            Call<ResponseDTO> listFavoriteFilmResponseCall = ApiClient.getFilmService().createCategory(
                    StoreUtil.get(CreateActivity.this, Contants.accessToken), category);
            listFavoriteFilmResponseCall.enqueue(new Callback<ResponseDTO>() {
                @Override
                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                    edtCategory.setText("");
                }

                @Override
                public void onFailure(Call<ResponseDTO> call, Throwable t) {
                    Toast.makeText(CreateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void createDirector() {
        if (edtNameOfDirector.getText() == null && edtDescription.getText()==null && mUri == null) {
            Toast.makeText(CreateActivity.this, "Director is not null", Toast.LENGTH_SHORT).show();
        } else {
            // upload new image
            String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
            File fileImage = new File(strRealPath);
            requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);
            Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageDirector(
                    StoreUtil.get(CreateActivity.this, Contants.accessToken),
                    multipartBody);
            responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    if (response.isSuccessful()) {
                        String url = response.body().getUrl();
                        String public_id = response.body().getPublic_id();
                        Image image = new Image(public_id,url);
                        UpdateDirectorRequest updateDirectorRequest = new UpdateDirectorRequest(
                                edtNameOfDirector.getText().toString(), edtDescription.getText().toString(),image);
                        Call<ResponseDTO> updateDirectorRequestCall = ApiClient.getFilmService().createDirector(
                                StoreUtil.get(CreateActivity.this, Contants.accessToken), updateDirectorRequest);
                        updateDirectorRequestCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(CreateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                    Toast.makeText(CreateActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void createModeOfPayment() {
        if (edtModeOfPayment.getText() == null && mUri == null) {
            Toast.makeText(CreateActivity.this, "Mode of payment is not null", Toast.LENGTH_SHORT).show();
        } else {
            // upload new image
            String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
            File fileImage = new File(strRealPath);
            requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

            Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageModeOfPayment(
                    StoreUtil.get(CreateActivity.this, Contants.accessToken),
                    multipartBody);
            responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    if (response.isSuccessful()) {
                        String url = response.body().getUrl();
                        String public_id = response.body().getPublic_id();
                        Image image = new Image(public_id,url);
                        ModeOfPaymentRequest updateDirectorRequest = new ModeOfPaymentRequest(
                                edtModeOfPayment.getText().toString(), image);
                        Call<ResponseDTO> updateDirectorRequestCall = ApiClient.getFilmService().createModeOfPayment(
                                StoreUtil.get(CreateActivity.this, Contants.accessToken), updateDirectorRequest);
                        updateDirectorRequestCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(CreateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                    Toast.makeText(CreateActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}