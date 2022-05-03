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

import com.bumptech.glide.Glide;
import com.example.adminnetflix.R;
import com.example.adminnetflix.adapters.ListDirectorAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.CategoryRequest;
import com.example.adminnetflix.models.request.DeleteImageRequest;
import com.example.adminnetflix.models.request.FeedbackRequest;
import com.example.adminnetflix.models.request.ModeOfPaymentRequest;
import com.example.adminnetflix.models.request.ModeOfPaymentWithoutImage;
import com.example.adminnetflix.models.request.UpdateAdminRequest;
import com.example.adminnetflix.models.request.UpdateDirectorRequest;
import com.example.adminnetflix.models.request.UpdateDirectorWithoutImage;
import com.example.adminnetflix.models.response.DetailDirectorResponse;
import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.realpath.RealPathUtil;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    private Uri mUri;
    RequestBody requestBody;
    // category
    private TextView tvCategory;
    private EditText edtCategory;

    // director
    private TextView tvDirector;
    private TextView tvDescription;
    private EditText edtDescription;
    private EditText edtDirector;
    private CircleImageView imgDirector;

    // mode of payment
    private TextView tvModeOfPayment;
    private EditText edtModeOfPayment;
    private ImageView imgModeOfPayment;

    // feedback
    private TextView fullName;
    private TextView tvFullname;
    private TextView email;
    private TextView tvEmail;
    private TextView subject;
    private TextView tvSubject;
    private TextView content;
    private TextView tvContent;
    private TextView tvSendFeedback;
    private EditText edtSendFeedback;

    private Button btnUpdate;
    private TextView tvTitleUpdate;
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
                            imgDirector.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();


        if (b.get("update").equals("Update Category")) {
            edtCategory.setVisibility(View.VISIBLE);
            tvCategory.setVisibility(View.VISIBLE);
            String id = b.get("id_category").toString();
            String name = b.get("name_category").toString();
            edtCategory.setText(name);
            tvTitleUpdate.setText("Update Category");
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateCategory(id);
                }
            });
        }

        if (b.get("update").equals("Update Director")) {
            tvDirector.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
            edtDirector.setVisibility(View.VISIBLE);
            edtDescription.setVisibility(View.VISIBLE);
            imgDirector.setVisibility(View.VISIBLE);

            String id = b.get("id_director").toString();
            String name = b.get("name_director").toString();
            String public_Id = b.get("public_Id_director").toString();
            String url = b.get("url_director").toString();

            imgDirector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickRequestPermission();
                }
            });

            if (url.isEmpty()) {
                imgDirector.setImageResource(R.drawable.backgroundslider);
            } else {
                Glide.with(getApplicationContext())
                        .load(url)
                        .into(imgDirector);
            }

            edtDirector.setText(name);
            tvTitleUpdate.setText("Update Director");

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateDirector(id,public_Id);
                }
            });
        }

        if (b.get("update").equals("Update Mode of Payment")) {
            edtModeOfPayment.setVisibility(View.VISIBLE);
            tvModeOfPayment.setVisibility(View.VISIBLE);
            imgModeOfPayment.setVisibility(View.VISIBLE);
            String id = b.get("id_mode_of_payment").toString();
            String name = b.get("name_mode_of_payment").toString();
            String public_Id = b.get("public_Id_mode_of_payment").toString();
            String url = b.get("url_mode_of_payment").toString();

            edtModeOfPayment.setText(name);
            tvTitleUpdate.setText("Update Mode of Payment");

            imgModeOfPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickRequestPermission();
                }
            });

            if (url.isEmpty()) {
                imgModeOfPayment.setImageResource(R.drawable.backgroundslider);
            } else {
                Glide.with(getApplicationContext())
                        .load(url)
                        .into(imgModeOfPayment);
            }

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateModeOfPayment(id,public_Id);
                }
            });
        }

        if (b.get("update").equals("Reply to Feedback")) {
            edtSendFeedback.setVisibility(View.VISIBLE);
            tvSendFeedback.setVisibility(View.VISIBLE);
            fullName.setVisibility(View.VISIBLE);
            tvFullname.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);
            subject.setVisibility(View.VISIBLE);
            tvSubject.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);

            String id = b.get("id_feedback").toString();
            tvFullname.setText(b.get("fullname_feedback").toString());
            tvEmail.setText(b.get("email_feedback").toString());
            tvSubject.setText(b.get("subject_feedback").toString());
            tvContent.setText(b.get("content_feedback").toString());

            tvTitleUpdate.setText("Reply to Feedback");
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replyToFeedback(id);
                }
            });
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        // category
        edtCategory = findViewById(R.id.edt_category);
        tvCategory = findViewById(R.id.tv_category);

        // director
        tvDirector = findViewById(R.id.tv_director);
        tvDescription = findViewById(R.id.tv_description_director);
        edtDirector = findViewById(R.id.edt_director);
        edtDescription = findViewById(R.id.edt_description_director);
        imgDirector = findViewById(R.id.img_direction);

        // mode of payment
        tvModeOfPayment = findViewById(R.id.tv_mode_of_payment);
        edtModeOfPayment = findViewById(R.id.edt_mode_of_payment);
        imgModeOfPayment = findViewById(R.id.img_mode_of_payment);

        // feedback
        fullName = findViewById(R.id.fullname_feedback);
        tvFullname = findViewById(R.id.tv_fullname_feedback);
        email = findViewById(R.id.email_feedback);
        tvEmail = findViewById(R.id.tv_email_feedback);
        subject = findViewById(R.id.subject_feedback);
        tvSubject = findViewById(R.id.tv_subject_feedback);
        content = findViewById(R.id.content_feedback);
        tvContent = findViewById(R.id.tv_content_feedback);
        tvSendFeedback = findViewById(R.id.tv_send_feedback);
        edtSendFeedback = findViewById(R.id.edt_send_feedback);

        btnUpdate = findViewById(R.id.btn_update);
        tvTitleUpdate = findViewById(R.id.tv_title_update);
        imgBack = findViewById(R.id.img_back);
    }

    private void UpdateCategory(String id) {
        CategoryRequest categoryRequest = new CategoryRequest(edtCategory.getText().toString());
        Call<ResponseDTO> listDirectorResponseCall = ApiClient.getFilmService().updateCategory(
                StoreUtil.get(UpdateActivity.this, Contants.accessToken), id, categoryRequest);
        listDirectorResponseCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateDirector(String idDirector,String public_id) {
        UpdateDirectorWithoutImage updateDirectorWithoutImage = new UpdateDirectorWithoutImage(
                edtDirector.getText().toString(),edtDescription.getText().toString());
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
            File fileImage = new File(strRealPath);
            requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

            //delete image
            deleteImage(public_id);

            // upload new image
            Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageDirector(
                    StoreUtil.get(UpdateActivity.this, Contants.accessToken),
                    multipartBody);
            responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    if (response.isSuccessful()) {
                        String url = response.body().getUrl();
                        String public_id = response.body().getPublic_id();
                        Image image = new Image(public_id,url);
                        UpdateDirectorRequest updateDirectorRequest = new UpdateDirectorRequest(
                                edtDirector.getText().toString(), edtDescription.getText().toString(),image);
                        Call<ResponseDTO> updateDirectorRequestCall = ApiClient.getFilmService().updateDirector(
                                StoreUtil.get(UpdateActivity.this, Contants.accessToken), idDirector, updateDirectorRequest);
                        updateDirectorRequestCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(UpdateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                    Toast.makeText(UpdateActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                }
            });

        }
        Call<ResponseDTO> updateDirectorRequestCall = ApiClient.getFilmService().updateDirectorWithoutImage(
                StoreUtil.get(UpdateActivity.this, Contants.accessToken), idDirector, updateDirectorWithoutImage);
        updateDirectorRequestCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void UpdateModeOfPayment(String idModeOfPayment,String public_id) {
        ModeOfPaymentWithoutImage updateDirectorWithoutImage = new ModeOfPaymentWithoutImage(
                edtModeOfPayment.getText().toString());
        if (mUri != null) {
            String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
            File fileImage = new File(strRealPath);
            requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

            //delete image
            deleteImageModeofPayment(public_id);

            // upload new image
            Call<UploadImageResponse> responseDTOCall = ApiClient.getFilmService().uploadImageModeOfPayment(
                    StoreUtil.get(UpdateActivity.this, Contants.accessToken),
                    multipartBody);
            responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                @Override
                public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                    if (response.isSuccessful()) {
                        String url = response.body().getUrl();
                        String public_id = response.body().getPublic_id();
                        Image image = new Image(public_id,url);
                        ModeOfPaymentRequest modeOfPaymentRequest = new ModeOfPaymentRequest(
                                 edtModeOfPayment.getText().toString(),image);
                        Call<ResponseDTO> updateDirectorRequestCall = ApiClient.getFilmService().updateModeOfPayment(
                                StoreUtil.get(UpdateActivity.this, Contants.accessToken), idModeOfPayment, modeOfPaymentRequest);
                        updateDirectorRequestCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(UpdateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                    Toast.makeText(UpdateActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                }
            });

        }
        Call<ResponseDTO> updateDirectorRequestCall = ApiClient.getFilmService().updateModeOfPaymentWithoutImage(
                StoreUtil.get(UpdateActivity.this, Contants.accessToken), idModeOfPayment, updateDirectorWithoutImage);
        updateDirectorRequestCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void deleteImage(String public_id) {
        DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);

        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteImageDirector(
                StoreUtil.get(UpdateActivity.this, Contants.accessToken), deleteImageRequest);
        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteImageModeofPayment(String public_id) {
        DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);

        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteImageModeOfPayment(
                StoreUtil.get(UpdateActivity.this, Contants.accessToken), deleteImageRequest);
        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replyToFeedback(String idFeedback){
        FeedbackRequest feedbackRequest = new FeedbackRequest(edtSendFeedback.getText().toString());
        Call<ResponseDTO> listDirectorResponseCall = ApiClient.getFilmService().responseFeedback(
                StoreUtil.get(UpdateActivity.this, Contants.accessToken), idFeedback, feedbackRequest);
        listDirectorResponseCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}