package com.example.adminnetflix.models;

public class ItemManageStatistical {
    private String title;
    private String image;
    private int amount;
    private int color;


    public ItemManageStatistical(String title, String image, int amount, int color) {
        this.title = title;
        this.image = image;
        this.amount = amount;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
