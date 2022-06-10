package com.example.adminnetflix.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultFilm {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("data")
    @Expose
    private List<DataAllFilm> data = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<DataAllFilm> getData() {
        return data;
    }

    public void setData(List<DataAllFilm> data) {
        this.data = data;
    }

}
