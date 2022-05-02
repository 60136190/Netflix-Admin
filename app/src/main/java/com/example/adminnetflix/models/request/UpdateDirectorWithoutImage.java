package com.example.adminnetflix.models.request;

public class UpdateDirectorWithoutImage {
    private String name;
    private String description;

    public UpdateDirectorWithoutImage(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
