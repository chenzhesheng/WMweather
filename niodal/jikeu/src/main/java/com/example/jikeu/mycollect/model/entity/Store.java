package com.example.jikeu.mycollect.model.entity;

import android.graphics.Bitmap;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */

public class Store {
    private Bitmap logo;
    private String name;
    private String message;
    private String status;
    private Bitmap storeMessage;

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getStoreMessage() {
        return storeMessage;
    }

    public void setStoreMessage(Bitmap storeMessage) {
        this.storeMessage = storeMessage;
    }

    @Override
    public String toString() {
        return "Store{" +
                "logo=" + logo +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", storeMessage=" + storeMessage +
                '}';
    }
}
