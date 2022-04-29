package com.example.adminnetflix.api;

import com.example.adminnetflix.models.response.Category;
import com.example.adminnetflix.models.response.DetailFilmResponse;
import com.example.adminnetflix.models.response.FeedbackResponse;
import com.example.adminnetflix.models.response.FilmResponse;
import com.example.adminnetflix.models.response.ListCategories;
import com.example.adminnetflix.models.response.ListDirectorResponse;
import com.example.adminnetflix.models.response.ModeOfPaymentResponse;
import com.example.adminnetflix.models.response.RatingResponse;
import com.example.adminnetflix.models.response.ResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Film {

    //get film follow category
    @GET("api/film/all")
    Call<FilmResponse> getAllFilm(@Header("Authorization") String authorization);

    // get detail film
    @GET("api/film/detail/{id}")
    Call<DetailFilmResponse> detailFilm(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // get series film
    @GET("api/film/detail/{id}")
    Call<DetailFilmResponse> getSeries(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // get rating film
    @GET("api/rating/all")
    Call<RatingResponse> getAllRating(@Header("Authorization") String authorization);

    // get mode of payment
    @GET("api/modeOfPayment/all")
    Call<ModeOfPaymentResponse> getModeOfPayment(@Header("Authorization") String authorization);

    // delete mode of payment
    @DELETE("api/modeOfPayment/delete/{id}")
    Call<ResponseDTO> deleteModeOfPayment(@Header("Authorization") String authorization, @Path("id") String idModeOfPayment);

    // get list feedback
    @GET("api/feedback/all")
    Call<FeedbackResponse> getAllFeedback(@Header("Authorization") String authorization);

    // get list categories film
    @GET("api/category/all")
    Call<ListCategories> getListCategoriesFilm(@Header("Authorization") String authorization);

    // get list director
    @GET("api/director/all")
    Call<ListDirectorResponse> getListDirector(@Header("Authorization") String authorization);

    // delete director
    @DELETE("api/director/delete/{id}")
    Call<ResponseDTO> deleteDirector (@Header("Authorization") String authorization, @Path("id") String idDirector);
}
