package com.example.adminnetflix.models.request;

import com.example.adminnetflix.models.response.Image;

public class UpdateDirectorRequest {
    private String name;
    private String description;
    private Image image;

    public UpdateDirectorRequest(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
