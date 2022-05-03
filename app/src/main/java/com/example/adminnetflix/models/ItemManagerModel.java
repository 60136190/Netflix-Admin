package com.example.adminnetflix.models;

public class ItemManagerModel {
    private String title;
    private String amount;
    private int image;
    private int color;

    public ItemManagerModel(String title, String amount, int image, int color) {
        this.title = title;
        this.amount = amount;
        this.image = image;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
