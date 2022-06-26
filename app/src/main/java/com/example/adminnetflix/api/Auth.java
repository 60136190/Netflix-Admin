package com.example.adminnetflix.api;


import com.example.adminnetflix.models.request.DeleteImageRequest;
import com.example.adminnetflix.models.request.ForgetPasswordRequest;
import com.example.adminnetflix.models.request.LoginGoogleRequest;
import com.example.adminnetflix.models.request.LoginRequest;
import com.example.adminnetflix.models.request.RefreshTokenResponse;
import com.example.adminnetflix.models.request.RegisterRequest;
import com.example.adminnetflix.models.request.UpdateAdminRequest;
import com.example.adminnetflix.models.response.CustomerUncheckResponse;
import com.example.adminnetflix.models.response.DetailUserResponse;
import com.example.adminnetflix.models.response.ListAdminResponse;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.models.response.LoginGoogleResponse;
import com.example.adminnetflix.models.response.LoginResponse;
import com.example.adminnetflix.models.response.MonthlyRevenueResponse;
import com.example.adminnetflix.models.response.ProfileResponse;
import com.example.adminnetflix.models.response.RegisterResponse;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.models.response.UserSubRecentlyResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Auth {
    // register admin
    @POST("api/auth/admin/register")
    Call<RegisterResponse> register(@Body RegisterRequest register);

    // register customer
    @POST("api/auth/customer/register")
    Call<RegisterResponse> registerCustomer(@Body RegisterRequest register);

    // get list customer uncheck
    @GET("api/auth/admin/getAllCustomerUncheck")
    Call<CustomerUncheckResponse> getListCustomerUncheck();

    // login
    @POST("api/auth/admin/login")
    Call<LoginResponse> login(@HeaderMap HashMap<String, String> hashMap, @Body LoginRequest loginRequest);

    // logout
    @GET("api/auth/admin/logout")
    Call<ResponseDTO> logout(@Header("Cookie") String accessToken);

    // forget Password
    @POST("api/auth/admin/forget")
    Call<ResponseDTO> forgetPassword(@HeaderMap HashMap<String, String> hashMap, @Body ForgetPasswordRequest forgetPasswordRequest);

//    // changePassword
//    @PATCH("api/auth/customer/changePassword")
//    Call<ResponseDTO> changePassword(@HeaderMap HashMap<String, String> hashMap, @Body ChangePasswordRequest changePasswordRequest);
//
    // getProfile
    @GET("api/auth/admin/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String authorization);

    // uploadImage
    @Multipart
    @POST("api/uploadImageUser")
    Call<UploadImageResponse> uploadImage(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    // update infomation user
    @PATCH("api/auth/admin/profile/update")
    Call<ResponseDTO> updateInfo(@HeaderMap HashMap<String, String> hashMap, @Body UpdateAdminRequest updateAdminRequest);

    // delete image user
    @POST("api/destroyImageUser")
    Call<ResponseDTO> deleteImage(@Header("Authorization") String authorization, @Body DeleteImageRequest deleteImageRequest);

    // refresh token
    @GET("api/auth/admin/refresh_token")
    Call<RefreshTokenResponse> refreshToken(@Header("Cookie") String refreshToken);

    // login google
    @POST("api/auth/admin/loginGoogle")
    Call<LoginGoogleResponse> loginGoogle(@HeaderMap HashMap<String, String> hashMap, @Body LoginGoogleRequest loginGoogleRequest);

    // get list user
    @GET("api/auth/admin/getAllCustomer")
    Call<ListUserResponse> getListUser(@Header("Authorization") String authorization);

    // get list admin
    @GET("api/auth/admin/getAllAdmin")
    Call<ListAdminResponse> getListAdmin(@Header("Authorization") String authorization);

    // get detail user
    @GET("api/auth/admin/getDetailCustomer/{id_user}")
    Call<DetailUserResponse> getDetailUser(@Header("Authorization") String authorization,@Path("id_user") String idUser);

    // update information user
    @PATCH("api/auth/admin/customerAccount/{id}/update/info")
    Call<ResponseDTO> updateInformationUser(@Header("Authorization") String authorization, @Path("id") String idUser, @Body UpdateAdminRequest updateUserRequest);

    // get monthly revenue
    @GET("api/statistics/revenue/monthly")
    Call<MonthlyRevenueResponse> getMonthlyRevenue(@Header("Authorization") String authorization);

    // get user sub recently
    @GET("api/statistics/registeredUser/recently")
    Call<UserSubRecentlyResponse> getUserSubRecently(@Header("Authorization") String authorization);
}
