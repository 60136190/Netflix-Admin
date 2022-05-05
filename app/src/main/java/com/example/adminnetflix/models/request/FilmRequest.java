package com.example.adminnetflix.models.request;

import com.example.adminnetflix.models.response.Image;
import com.example.adminnetflix.models.response.SeriesFilm;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmRequest {
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
    @SerializedName("image_film")
    @Expose
    private Image imageFilm;
    @SerializedName("director")
    @Expose
    private List<String> director = null;
    @SerializedName("category")
    @Expose
    private List<String> category = null;
    @SerializedName("seriesFilm")
    @Expose
    private List<SeriesFilm> seriesFilm = null;
    @SerializedName("ageLimit")
    @Expose
    private Integer ageLimit;
    @SerializedName("filmLength")
    @Expose
    private String filmLength;
    @SerializedName("price")
    @Expose
    private Integer price;

    public FilmRequest(String title, String description, String yearProduction, String countryProduction, Image imageFilm, List<String> director, List<String> category, List<SeriesFilm> seriesFilm, Integer ageLimit, String filmLength, Integer price) {
        this.title = title;
        this.description = description;
        this.yearProduction = yearProduction;
        this.countryProduction = countryProduction;
        this.imageFilm = imageFilm;
        this.director = director;
        this.category = category;
        this.seriesFilm = seriesFilm;
        this.ageLimit = ageLimit;
        this.filmLength = filmLength;
        this.price = price;
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

    public Image getImageFilm() {
        return imageFilm;
    }

    public void setImageFilm(Image imageFilm) {
        this.imageFilm = imageFilm;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<SeriesFilm> getSeriesFilm() {
        return seriesFilm;
    }

    public void setSeriesFilm(List<SeriesFilm> seriesFilm) {
        this.seriesFilm = seriesFilm;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
