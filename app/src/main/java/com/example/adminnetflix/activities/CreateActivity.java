package com.example.adminnetflix.activities;

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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.adminnetflix.R;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.request.CategoryRequest;
import com.example.adminnetflix.models.request.ModeOfPaymentRequest;
import com.example.adminnetflix.models.request.RegisterRequest;
import com.example.adminnetflix.models.request.UpdateDirectorRequest;
import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.RegisterResponse;
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

public class CreateActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationAdminActivity.class.getName();
    private Uri mUri;
    RequestBody requestBody;

    // category
    private EditText edtCategory;
    private TextView tvTitleCreate;

    // director
    private TextView tvDescription;
    private EditText edtNameOfDirector, edtDescription;
    private ImageView imgDirctor;

    // mode of payment
    private EditText edtModeOfPayment;
    private ImageView imgModeOfPayment;

    // admin
    ConstraintLayout ctAdmin;
    EditText edtHoTen, edtNgaySinh, edtDienThoai, edtEmail, edtPassword, edtConfrimPassword;
    TextView tvValidateSex;
    TextInputLayout tilFullName, tilDateofBirth, tilPhoneNumber, tilEmail, tilPassword, tilConfirmPassword;
    int sex = 0;

    private RadioGroup radioGroup;
    private RadioButton rdbMale, rdbFemale;

    private Button btnCreate;
    private ImageView imgBack;
    private ProgressBar progressBar;

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
        if (b.get("btn").equals("category")) {
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
        if (b.get("btn").equals("admin")) {
            ctAdmin.setVisibility(View.VISIBLE);
            tvTitleCreate.setText("Create Admin");
            edtNgaySinh.addTextChangedListener(new TextWatcher() {
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

                        if (clean.length() < 8){
                            clean = clean + ddmmyyyy.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day  = Integer.parseInt(clean.substring(0,2));
                            int mon  = Integer.parseInt(clean.substring(2,4));
                            int year = Integer.parseInt(clean.substring(4,8));

                            if(mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon-1);

                            year = (year<1900)?1900:(year>2100)?2100:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            clean = String.format("%02d%02d%02d", day,mon,year);
                        }

                        clean = String.format("%s-%s-%s", clean.substring(0, 2),
                                clean.substring(2, 4),
                                clean.substring(4, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        edtNgaySinh.setText(current);
                        edtNgaySinh.setSelection(sel < current.length() ? sel : current.length());

                    }
                }


                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void afterTextChanged(Editable s) {}
            });
            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerAdmin();
                }
            });

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Male:
                            sex = 1;
                            break;
                        case R.id.Female:
                            sex = 0;
                            break;
                    }
                }
            });
        }

        // create user
        if (b.get("btn").equals("user")){
            ctAdmin.setVisibility(View.VISIBLE);
            tvTitleCreate.setText("Create Customer Account");
            edtNgaySinh.addTextChangedListener(new TextWatcher() {
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

                        if (clean.length() < 8){
                            clean = clean + ddmmyyyy.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day  = Integer.parseInt(clean.substring(0,2));
                            int mon  = Integer.parseInt(clean.substring(2,4));
                            int year = Integer.parseInt(clean.substring(4,8));

                            if(mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon-1);

                            year = (year<1900)?1900:(year>2100)?2100:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            clean = String.format("%02d%02d%02d", day,mon,year);
                        }

                        clean = String.format("%s-%s-%s", clean.substring(0, 2),
                                clean.substring(2, 4),
                                clean.substring(4, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        edtNgaySinh.setText(current);
                        edtNgaySinh.setSelection(sel < current.length() ? sel : current.length());

                    }
                }


                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void afterTextChanged(Editable s) {}
            });
            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerCustomer();
                }
            });
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Male:
                            sex = 1;
                            break;
                        case R.id.Female:
                            sex = 0;
                            break;
                    }
                }
            });
        }

        // create director
        if (b.get("btn").equals("director")){
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
        if (b.get("btn").equals("mode")){
            tvTitleCreate.setText("Create Mode of Payment");
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
        tvDescription = findViewById(R.id.tv_description_director);
        edtNameOfDirector = findViewById(R.id.edt_nname_of_director);
        edtDescription = findViewById(R.id.edt_description_director);
        imgDirctor = findViewById(R.id.img_direction);

        progressBar = (ProgressBar) findViewById(R.id.spin_kit);

        edtModeOfPayment = findViewById(R.id.edt_name_of_payment);
        imgModeOfPayment = findViewById(R.id.img_mode_of_payment);

        imgBack = findViewById(R.id.img_back);
        btnCreate = findViewById(R.id.btn_create);

        // admin
        ctAdmin = findViewById(R.id.ct_admin);
        edtHoTen = findViewById(R.id.edt_ho_ten);
        edtNgaySinh = findViewById(R.id.edt_ngay_sinh);
        tvValidateSex = findViewById(R.id.tv_validateSex);
        edtDienThoai = findViewById(R.id.edt_phone_number);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfrimPassword = findViewById(R.id.edt_confirm_password);
        radioGroup = findViewById(R.id.radioGroup);
        rdbMale = findViewById(R.id.Male);
        rdbFemale = findViewById(R.id.Female);
        tilFullName = findViewById(R.id.til_full_name);
        tilDateofBirth = findViewById(R.id.til_date_of_birth);
        tilPhoneNumber = findViewById(R.id.til_phone_number);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_pass);
        tilConfirmPassword = findViewById(R.id.til_confirm_pass);
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

    private void registerAdmin() {
        String hoTen = edtHoTen.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String dienThoai = edtDienThoai.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfrimPassword.getText().toString();

        if (validateFullName() && validateDateofBirth() && validateSex() && validatePhoneNumber()
                && validateEmail() && validatePassword() && validateConfirmPassword()) {
            RegisterRequest userRegister = new RegisterRequest(hoTen, email, password,sex,ngaySinh,dienThoai);
            Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().register(userRegister);
            registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        setProgressBar();

                    } else {
                        String message = "Try again....";
                        Toast.makeText(CreateActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(CreateActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void registerCustomer(){
        String hoTen = edtHoTen.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String dienThoai = edtDienThoai.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfrimPassword.getText().toString();

        if (validateFullName() && validateDateofBirth() && validateSex() && validatePhoneNumber()
                && validateEmail() && validatePassword() && validateConfirmPassword()) {
            RegisterRequest userRegister = new RegisterRequest(hoTen, email, password,sex,ngaySinh,dienThoai);
            Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().registerCustomer(userRegister);
            registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        setProgressBar();

                    } else {
                        String message = "Try again....";
                        Toast.makeText(CreateActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(CreateActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                }
            });
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

    private boolean validateFullName() {
        String fullname = edtHoTen.getText().toString().trim();
        if (fullname.length() < 8){
            tilFullName.setError("Minimum 8 Character");
            return false;
        }else if (!fullname.matches(".*[A-Z].*")){
            tilFullName.setError("Must contain 1 upper-case Character");
            return false;
        }else if (!fullname.matches(".*[a-z].*")) {
            tilFullName.setError("Must contain 1 Lower-case Character");
            return false;
        }else if (fullname.matches(".*[0-9].*")) {
            tilFullName.setError("Not number");
            return false;
        }else if (fullname.matches(".*[@!#$%^&*()_+=<>?/|].*")) {
            tilFullName.setError("Not special character");
            return false;
        }
        else {
            tilFullName.setError(null);
            return true;
        }
    }

    private boolean validateDateofBirth() {
        String dateofbirth = edtNgaySinh.getText().toString().trim();
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
        String phonenumber = edtDienThoai.getText().toString().trim();
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

    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()){
            tilEmail.setError("Email can't empty");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Please enter a valid email address");
            return false;
        }
        else {
            tilEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pass = edtPassword.getText().toString().trim();
        if (pass.length() < 8){
            tilPassword.setError("Minimum 8 Chac");
            return false;
        }else if (!pass.matches(".*[A-Z].*")){
            tilPassword.setError("Must contain 1 upper-case Character");
            return false;
        }else if (!pass.matches(".*[a-z].*")) {
            tilPassword.setError("Must contain 1 Lower-case Character");
            return false;
        }else if (!pass.matches(".*[@!#$%^&*()_+=<>?/|].*")) {
            tilPassword.setError("Must contain 1 special character (@!#$%^&*()_+=<>?/|)");
            return false;
        }else if (!pass.matches(".*[0-9].*")) {
            tilPassword.setError("Must contain at least 1 number");
            return false;
        }
        else if (!pass.matches("\\S+$")) {
            tilPassword.setError("Must be no white space");
            return false;
        }
        else {
            tilPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String password = edtPassword.getText().toString().trim();
        String confirmPass = edtConfrimPassword.getText().toString().trim();
        if (confirmPass.equals(password)){
            tilConfirmPassword.setError(null);
            return true;
        }
        else {
            tilConfirmPassword.setError("Password and Confirm are not match");
            return false;
        }
    }

}