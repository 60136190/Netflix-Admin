package com.example.adminnetflix.models.response.favourite;

import com.example.adminnetflix.models.response.comment.FilmCommentDeleted;
import com.example.adminnetflix.models.response.comment.UserCommentDeleted;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favourite {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private UserCommentDeleted user;
    @SerializedName("film")
    @Expose
    private FilmCommentDeleted film;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserCommentDeleted getUser() {
        return user;
    }

    public void setUser(UserCommentDeleted user) {
        this.user = user;
    }

    public FilmCommentDeleted getFilm() {
        return film;
    }

    public void setFilm(FilmCommentDeleted film) {
        this.film = film;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
