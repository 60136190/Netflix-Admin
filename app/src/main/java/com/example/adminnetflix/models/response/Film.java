package com.example.adminnetflix.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable {
    @SerializedName("image_film")
    @Expose
    private Image imageFilm;

    @SerializedName("image_title")
    @Expose
    private Image imageTitle;

    @SerializedName("video_film")
    @Expose
    private Video videoFilm;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("year_production")
    @Expose
    private String yearProduction;
    @SerializedName("country_production")
    @Expose
    private String countryProduction;
    @SerializedName("director")
    @Expose
    private List<Director> director = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("seriesFilm")
    @Expose
    private List<SeriesFilm> seriesFilm = null;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("filmLength")
    @Expose
    private String filmLength;
    @SerializedName("ageLimit")
    @Expose
    private String ageLimit;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Image getImageFilm() {
        return imageFilm;
    }

    public void setImageFilm(Image imageFilm) {
        this.imageFilm = imageFilm;
    }

    public Image getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(Image imageTitle) {
        this.imageTitle = imageTitle;
    }

    public Video getVideoFilm() {
        return videoFilm;
    }

    public void setVideoFilm(Video videoFilm) {
        this.videoFilm = videoFilm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYearProduction() {
        return yearProduction;
    }

    public void setYearProduction(String yearProduction) {
        this.yearProduction = yearProduction;
    }

    public String getCountryProduction() {
        return countryProduction;
    }

    public void setCountryProduction(String countryProduction) {
        this.countryProduction = countryProduction;
    }

    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<SeriesFilm> getSeriesFilm() {
        return seriesFilm;
    }

    public void setSeriesFilm(List<SeriesFilm> seriesFilm) {
        this.seriesFilm = seriesFilm;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
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
