package com.example.jikeu.myorder.model.entity;

import android.graphics.Bitmap;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */

public class Order {
    private String orderNumber;
    private String status;
    private Bitmap image;
    private String name;
    private String price;
    private String message;
    private String goodsNumber;
    private String num;
    private String numPrice;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNumPrice() {
        return numPrice;
    }

    public void setNumPrice(String numPrice) {
        this.numPrice = numPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", status='" + status + '\'' +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", message='" + message + '\'' +
                ", goodsNumber='" + goodsNumber + '\'' +
                ", num='" + num + '\'' +
                ", numPrice='" + numPrice + '\'' +
                '}';
    }
}
