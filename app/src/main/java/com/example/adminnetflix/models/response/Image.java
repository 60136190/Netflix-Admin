package com.example.adminnetflix.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable {

    public Image(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }

    @SerializedName("public_id")
    @Expose
    private String publicId;
    @SerializedName("url")
    @Expose
    private String url;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
