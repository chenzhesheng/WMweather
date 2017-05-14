package com.example.jikeu.mycollect.model.entity;

import android.graphics.Bitmap;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */

public class Content {
    private Bitmap image;
    private String name;
    private String message;
    private String price;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Content{" +
                "image=" + image +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
