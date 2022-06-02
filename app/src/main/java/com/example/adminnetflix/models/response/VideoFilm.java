package com.example.adminnetflix.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoFilm {
    @SerializedName("public_id_video")
    @Expose
    private String publicIdVideo;
    @SerializedName("url_video")
    @Expose
    private String urlVideo;

    public VideoFilm(String publicIdVideo, String urlVideo) {
        this.publicIdVideo = publicIdVideo;
        this.urlVideo = urlVideo;
    }

    public String getPublicIdVideo() {
        return publicIdVideo;
    }

    public void setPublicIdVideo(String publicIdVideo) {
        this.publicIdVideo = publicIdVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

}
