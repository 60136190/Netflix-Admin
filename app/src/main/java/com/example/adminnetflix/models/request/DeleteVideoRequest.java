package com.example.adminnetflix.models.request;

public class DeleteVideoRequest {
    private String public_id;

    public DeleteVideoRequest(String public_id) {
        this.public_id = public_id;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }
}
