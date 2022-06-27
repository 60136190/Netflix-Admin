package com.example.adminnetflix.api;

import com.example.adminnetflix.models.request.CategoryRequest;
import com.example.adminnetflix.models.request.DeleteImageRequest;
import com.example.adminnetflix.models.request.DeleteVideoRequest;
import com.example.adminnetflix.models.request.FeedbackRequest;
import com.example.adminnetflix.models.request.FilmRequest;
import com.example.adminnetflix.models.request.ModeOfPaymentRequest;
import com.example.adminnetflix.models.request.ModeOfPaymentWithoutImage;
import com.example.adminnetflix.models.request.UpdateDirectorRequest;
import com.example.adminnetflix.models.request.UpdateDirectorWithoutImage;
import com.example.adminnetflix.models.response.AllFilmResponse;
import com.example.adminnetflix.models.response.CheckFilmCanWatch;
import com.example.adminnetflix.models.response.CommentResponse;
import com.example.adminnetflix.models.response.DetailDirectorResponse;
import com.example.adminnetflix.models.response.DetailFilmResponse;
import com.example.adminnetflix.models.response.FeedbackResponse;
import com.example.adminnetflix.models.response.FilmResponse;
import com.example.adminnetflix.models.response.HistoryBillResponse;
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ModeOfPaymentResponse;
import com.example.adminnetflix.models.response.RatingResponse;
import com.example.adminnetflix.models.response.ResponseDTO;
import com.example.adminnetflix.models.response.SeriesFilm;
import com.example.adminnetflix.models.response.UploadImageResponse;
import com.example.adminnetflix.models.response.UploadVideoResponse;
import com.example.adminnetflix.models.response.comment.CommentDeletedResponse;
import com.example.adminnetflix.models.response.favourite.FavouriteResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Film {

    // get all film
    @GET("api/film/adult/eachCategory")
    Call<AllFilmResponse> getAllFilmAdult(@Header("Authorization") String authorization);

    //upload image film
    @Multipart
    @POST("api/uploadImageFilm")
    Call<UploadImageResponse> uploadImageFilm(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    // upload video film
    @Multipart
    @POST("api/uploadVideoFilm")
    Call<UploadVideoResponse> uploadVideoFilm(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    // delete video film
    @POST("api/destroyVideoFilm")
    Call<ResponseDTO> deleteVideoFilm(@Header("Authorization") String authorization, @Body DeleteVideoRequest deleteVideoRequest);

    // create  film
    @POST("api/film/add")
    Call<ResponseDTO> createFilm(@Header("Authorization") String authorization, @Body FilmRequest filmRequest);

    // get detail film
    @GET("api/film/detail/{id}")
    Call<DetailFilmResponse> detailFilm(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // create series film
    @POST("api/film/{id}/addEpisode")
    Call<ResponseDTO> createSeriesFilm(@Header("Authorization") String authorization
            , @Path("id") String idFilm
            , @Body SeriesFilm seriesFilm);

    // get series film
    @GET("api/film/detail/{id}")
    Call<DetailFilmResponse> getSeries(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // delete series film
    @DELETE("api/film/delete/{id_film}/deleteEpisode/{id_episode}")
    Call<ResponseDTO> deleteSeriesFilm(@Header("Authorization") String authorization
            , @Path("id_film") String idFilm
            , @Path("id_episode") String idEpisode);

    // get rating film
    @GET("api/rating/all")
    Call<RatingResponse> getAllRating(@Header("Authorization") String authorization);

    // delete film
    @DELETE("api/film/delete/{id}")
    Call<ResponseDTO> deleteFilm(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // delete mode of payment image
    @POST("api/destroyImageFilm")
    Call<ResponseDTO> deleteImageFilm(@Header("Authorization") String authorization, @Body DeleteImageRequest deleteImageRequest);

    // create director
    @POST("api/modeOfPayment/add")
    Call<ResponseDTO> createModeOfPayment(@Header("Authorization") String authorization, @Body ModeOfPaymentRequest modeOfPaymentRequest);

    // upload image mode of payment
    @Multipart
    @POST("api/uploadImagePayment")
    Call<UploadImageResponse> uploadImageModeOfPayment(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    // delete mode of payment image
    @POST("api/destroyImagePayment")
    Call<ResponseDTO> deleteImageModeOfPayment(@Header("Authorization") String authorization, @Body DeleteImageRequest deleteImageRequest);

    // update mode of payment
    @PATCH("api/modeOfPayment/update/{id}")
    Call<ResponseDTO> updateModeOfPayment(@Header("Authorization") String authorization, @Path("id") String idDirector, @Body ModeOfPaymentRequest modeOfPaymentRequest);

    // update mode of payment without image
    @PATCH("api/modeOfPayment/update/{id}")
    Call<ResponseDTO> updateModeOfPaymentWithoutImage(@Header("Authorization") String authorization, @Path("id") String idDirector, @Body ModeOfPaymentWithoutImage modeOfPaymentWithoutImage);

    // get mode of payment
    @GET("api/modeOfPayment/all")
    Call<ModeOfPaymentResponse> getModeOfPayment(@Header("Authorization") String authorization);

    // delete mode of payment
    @DELETE("api/modeOfPayment/delete/{id}")
    Call<ResponseDTO> deleteModeOfPayment(@Header("Authorization") String authorization, @Path("id") String idModeOfPayment);

    // get list feedback
    @GET("api/feedback/all")
    Call<FeedbackResponse> getAllFeedback(@Header("Authorization") String authorization);

    // reply to  feedback
    @POST("api/feedback/response/{id}")
    Call<ResponseDTO> responseFeedback(@Header("Authorization") String authorization,
                                       @Path("id") String idFeedback,
                                       @Body FeedbackRequest feedbackRequest);

    // create category film
    @POST("api/category/add")
    Call<ResponseDTO> createCategory(@Header("Authorization") String authorization, @Body CategoryRequest nameCategory);

    // get list categories film
    @GET("api/category/all")
    Call<ListCategories> getListCategoriesFilm(@Header("Authorization") String authorization);

    // delete category
    @DELETE("api/category/delete/{id}")
    Call<ResponseDTO> deleteCategory(@Header("Authorization") String authorization, @Path("id") String idCategory);

    // update category
    @PUT("api/category/update/{id}")
    Call<ResponseDTO> updateCategory(@Header("Authorization") String authorization, @Path("id") String idCategory, @Body CategoryRequest categoryRequest);

    // create director
    @POST("api/director/add")
    Call<ResponseDTO> createDirector(@Header("Authorization") String authorization, @Body UpdateDirectorRequest updateDirectorRequest);

    // upload image director
    @Multipart
    @POST("api/uploadImageDirector")
    Call<UploadImageResponse> uploadImageDirector(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    // get list director
    @GET("api/director/all")
    Call<ListDirectorResponse> getListDirector(@Header("Authorization") String authorization);

    // get detail director
    @GET("api/director/{id}")
    Call<DetailDirectorResponse> getDetailDirector(@Header("Authorization") String authorization, @Path("id") String idDirector);

    // delete director image
    @POST("api/destroyImageDirector")
    Call<ResponseDTO> deleteImageDirector(@Header("Authorization") String authorization, @Body DeleteImageRequest deleteImageRequest);

    // update director
    @PATCH("api/director/update/{id}")
    Call<ResponseDTO> updateDirector(@Header("Authorization") String authorization, @Path("id") String idDirector, @Body UpdateDirectorRequest updateDirectorRequest);

    // update director withou image
    @PATCH("api/director/update/{id}")
    Call<ResponseDTO> updateDirectorWithoutImage(@Header("Authorization") String authorization, @Path("id") String idDirector, @Body UpdateDirectorWithoutImage updateDirectorWithoutImage);

    // delete director
    @DELETE("api/director/delete/{id}")
    Call<ResponseDTO> deleteDirector(@Header("Authorization") String authorization, @Path("id") String idDirector);

    // get all comment film
    @GET("api/comment/get/{id}")
    Call<CommentResponse> getAllCommentFollowFilm(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // get all comment deleted
    @GET("api/comment/bin")
    Call<CommentDeletedResponse> getAllCommentDeleted(@Header("Authorization") String authorization);

    // restore comment
    @PATCH("api/comment/{id}/restore")
    Call<ResponseDTO> restoreComment(@Header("Authorization") String authorization, @Path("id") String idComment);

    //get list favourite film
    @GET("api/favourite/all")
    Call<FavouriteResponse> getListFavouriteFilm(@Header("Authorization") String authorization);

    // get all film
    @GET("api/film/adult")
    Call<FilmResponse> getFilmAdult(@Header("Authorization") String authorization);

    // get film follow category
    @GET("api/film/kid")
    Call<FilmResponse> getFilmKid(@Header("Authorization") String authorization);

    // get bill follow id user
    @GET("api/bill/{id}/listBill")
    Call<HistoryBillResponse> getListBillFollowIdUser(@Header("Authorization") String authorization, @Path("id") String idUser);
}
