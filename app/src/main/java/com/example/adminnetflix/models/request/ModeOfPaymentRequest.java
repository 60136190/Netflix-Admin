package com.example.adminnetflix.models.request;

import com.example.adminnetflix.models.response.Image;

public class ModeOfPaymentRequest {
    private String name;
    private Image image;

    public ModeOfPaymentRequest(String name, Image image) {
        this.name = name;
        this.image = image;
    }
}
