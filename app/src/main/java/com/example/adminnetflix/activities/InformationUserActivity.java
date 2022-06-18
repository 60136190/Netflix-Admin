package com.example.adminnetflix.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.DeleteImageRequest;
import com.example.adminnetflix.models.request.UpdateAdminRequest;
import com.example.adminnetflix.models.response.DetailUserResponse;
import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.realpath.RealPathUtil;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationUserActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    private Uri mUri;
    ImageView imgBack, imgInfo, imgListFavouriteFilm, imgHistoryBill;
    EditText edtFullName, edtDateofBirth, edtPhoneNumber;
    Button btnUpdate;
    TextInputLayout tilFullName, tilDateofBirth, tilPhoneNumber;
    TextView tvValidateSex, tvEmail;
    RequestBody requestBody;
    int male = 0;
    RadioGroup radioGroup;
    RadioButton rdbMale, rdbFemale;
    ProgressBar progressBar;

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
                            imgInfo.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        initUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            String idUser = (String) b.get("Id_user");
            getDetailUser(idUser);
            edtDateofBirth.addTextChangedListener(new TextWatcher() {
                private String current = "";
                private String ddmmyyyy = "DDMMYYYY";
                private Calendar cal = Calendar.getInstance();

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]", "");
                        String cleanC = current.replaceAll("[^\\d.]", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8) {
                            clean = clean + ddmmyyyy.substring(clean.length());
                        } else {
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day = Integer.parseInt(clean.substring(0, 2));
                            int mon = Integer.parseInt(clean.substring(2, 4));
                            int year = Integer.parseInt(clean.substring(4, 8));

                            if (mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon - 1);

                            year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                            clean = String.format("%02d%02d%02d", day, mon, year);
                        }

                        clean = String.format("%s-%s-%s", clean.substring(0, 2),
                                clean.substring(2, 4),
                                clean.substring(4, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        edtDateofBirth.setText(current);
                        edtDateofBirth.setSelection(sel < current.length() ? sel : current.length());

                    }
                }


                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            imgListFavouriteFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InformationUserActivity.this, ListDetailActivity.class);
                    intent.putExtra("manager","list_favourite");
                    startActivity(intent);
                }
            });

            imgHistoryBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InformationUserActivity.this, ListDetailActivity.class);
                    intent.putExtra("manager","history bill");
                    startActivity(intent);
                }
            });

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Male:
                            male = 1;
                            break;
                        case R.id.Female:
                            male = 0;
                            break;
                    }
                }
            });

            imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRequestPermission();
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateImage(idUser);

                }
            });
        }
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        imgInfo = findViewById(R.id.imgUserInfor);
        edtFullName = findViewById(R.id.edt_fullname_user);
        edtDateofBirth = findViewById(R.id.edt_data_of_birth);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        tvValidateSex = findViewById(R.id.tv_validateSex);
        tvEmail = findViewById(R.id.tv_email_user);
        tilFullName = findViewById(R.id.til_full_name_user);
        tilDateofBirth = findViewById(R.id.til_date_of_birth);
        tilPhoneNumber = findViewById(R.id.til_phone_number);
        btnUpdate = findViewById(R.id.btn_update);
        imgListFavouriteFilm = findViewById(R.id.img_favourite_film);
        imgHistoryBill = findViewById(R.id.img_history_bill);
        radioGroup = findViewById(R.id.radioGroup);
        rdbMale = findViewById(R.id.Male);
        rdbFemale = findViewById(R.id.Female);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
    }

    public void getDetailUser(String id) {
        Call<DetailUserResponse> proifileResponseCall = ApiClient.getUserService().getDetailUser(
                StoreUtil.get(InformationUserActivity.this, "Authorization"),id);
        proifileResponseCall.enqueue(new Callback<DetailUserResponse>() {
            @Override
            public void onResponse(Call<DetailUserResponse> call, Response<DetailUserResponse> response) {

                String fullName = response.body().getData().getFullname();
                String email = response.body().getData().getEmail();
                String im = response.body().getData().getImage().getUrl();
                String date_of_birth = response.body().getData().getDateOfBirth();
                String sdt = response.body().getData().getPhoneNumber();
                String idUser = response.body().getData().getId();

                StoreUtil.save(InformationUserActivity.this,Contants.idUser,idUser);

                edtFullName.setText(fullName);
                tvEmail.setText(email);
                edtDateofBirth.setText(date_of_birth);
                edtPhoneNumber.setText(sdt);

                if (im.isEmpty()) {
                    imgInfo.setImageResource(R.drawable.backgroundslider);
                } else {
                    Glide.with(getApplicationContext())
                            .load(im)
                            .into(imgInfo);
                }
                if (response.body().getData().getSex() == null){

                }else {
                    if (response.body().getData().getSex() == 1) {
                        rdbMale.setChecked(true);
                    } else {
                        rdbFemale.setChecked(true);
                    }
                }


            }

            @Override
            public void onFailure(Call<DetailUserResponse> call, Throwable t) {

            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }


    public void updateImage(String idUser) {
        if (validateFullName() && validateDateofBirth()
                && validatePhoneNumber() && validateSex()) {
            if (mUri != null) {
                String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                File fileImage = new File(strRealPath);
                requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
                MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

                //delete image
                deleteImage();

                // upload new image
                Call<UploadImageResponse> responseDTOCall = ApiClient.getUserService().uploadImage(
                        StoreUtil.get(InformationUserActivity.this, Contants.accessToken),
                        multipartBody);
                responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                    @Override
                    public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                        if (response.isSuccessful()) {
                            String url = response.body().getUrl();
                            String public_id = response.body().getPublic_id();
                            String hoten = edtFullName.getText().toString();
                            String ngaySinh = edtDateofBirth.getText().toString();
                            String sdt = edtPhoneNumber.getText().toString();

                            Image image = new Image(public_id, url);

                            UpdateAdminRequest updateUserRequest = new UpdateAdminRequest(hoten, image, sdt, male, ngaySinh);
                            Call<ResponseDTO> loginResponeCall = ApiClient.getUserService().updateInformationUser(
                                    StoreUtil.get(InformationUserActivity.this,Contants.accessToken),idUser ,updateUserRequest);
                            loginResponeCall.enqueue(new Callback<ResponseDTO>() {
                                @Override
                                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                    setProgressBar();
                                }

                                @Override
                                public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                    Toast.makeText(InformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                        Toast.makeText(InformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Call<DetailUserResponse> proifileResponseCall = ApiClient.getUserService().getDetailUser(
                        StoreUtil.get(InformationUserActivity.this, Contants.accessToken),idUser);
                proifileResponseCall.enqueue(new Callback<DetailUserResponse>() {
                    @Override
                    public void onResponse(Call<DetailUserResponse> call, Response<DetailUserResponse> response) {
                        if (response.isSuccessful()) {
                            String im = response.body().getData().getImage().getUrl();
                            String public_id = response.body().getData().getImage().getPublicId();
                            Image image = new Image(public_id, im);

                            String hoten = edtFullName.getText().toString();
                            String ngaySinh = edtDateofBirth.getText().toString();
                            String sdt = edtPhoneNumber.getText().toString();
                            UpdateAdminRequest updateUserRequest = new UpdateAdminRequest(hoten, image, sdt, male, ngaySinh);


                            Call<ResponseDTO> loginResponeCall = ApiClient.getUserService().updateInformationUser(
                                    StoreUtil.get(InformationUserActivity.this,Contants.accessToken),idUser ,updateUserRequest);
                            loginResponeCall.enqueue(new Callback<ResponseDTO>() {
                                @Override
                                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                    setProgressBar();
                                }

                                @Override
                                public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                    Toast.makeText(InformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }

                    @Override
                    public void onFailure(Call<DetailUserResponse> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void deleteImage() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(InformationUserActivity.this, Contants.accessToken));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String public_id = response.body().getUser().getImage().getPublicId();
                    DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);
                    Call<ResponseDTO> responseDTOCall = ApiClient.getUserService().deleteImage(
                            StoreUtil.get(InformationUserActivity.this, Contants.accessToken),deleteImageRequest);
                    responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                        @Override
                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                            Toast.makeText(InformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    public void setProgressBar(){
        btnUpdate.setVisibility(View.INVISIBLE);
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
                onBackPressed();
            }

        };
        countDownTimer.start();
    }

    private boolean validateFullName() {
        String fullname = edtFullName.getText().toString().trim();
        if (fullname.length() < 8) {
            tilFullName.setError("Minimum 8 Character");
            return false;
        } else if (!fullname.matches(".*[A-Z].*")) {
            tilFullName.setError("Must contain 1 upper-case Character");
            return false;
        } else if (!fullname.matches(".*[a-z].*")) {
            tilFullName.setError("Must contain 1 Lower-case Character");
            return false;
        } else if (fullname.matches(".*[0-9].*")) {
            tilFullName.setError("Not number");
            return false;
        } else if (fullname.matches(".*[@!#$%^&*()_+=<>?/|].*")) {
            tilFullName.setError("Not special character");
            return false;
        } else {
            tilFullName.setError(null);
            return true;
        }
    }

    private boolean validateDateofBirth() {
        String dateofbirth = edtDateofBirth.getText().toString().trim();
        if (dateofbirth.length() < 10) {
            tilDateofBirth.setError("Minimum 10 Character");
            return false;
        } else {
            tilDateofBirth.setError(null);
            return true;
        }
    }

    private boolean validateSex() {
        if (!rdbMale.isChecked() && !rdbFemale.isChecked()) {
            tvValidateSex.setError("");
            return false;
        } else {
            tvValidateSex.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phonenumber = edtPhoneNumber.getText().toString().trim();
        if (phonenumber.length() > 10) {
            tilPhoneNumber.setError("Maximum 10 Character");
            return false;
        } else if (phonenumber.matches(".*[-,._].*")) {
            tilPhoneNumber.setError("Not special character");
            return false;
        } else if (phonenumber.matches(".*[A-Z].*")) {
            tilPhoneNumber.setError("Not character");
            return false;
        } else if (phonenumber.matches(".*[a-z].*")) {
            tilPhoneNumber.setError("Not character");
            return false;
        } else if (phonenumber.length() <10) {
            tilPhoneNumber.setError("Phone number must be 10 Character");
            return false;
        } else {
            tilPhoneNumber.setError(null);
            return true;
        }
    }

}