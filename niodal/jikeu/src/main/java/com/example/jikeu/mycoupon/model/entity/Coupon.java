package com.example.jikeu.mycoupon.model.entity;

import android.graphics.Bitmap;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class Coupon {
    private Bitmap storeShoto;
    private String storeName;
    private String couponDate;
    private Bitmap couponNumber;

    @Override
    public String toString() {
        return "Coupon{" +
                "storeShoto=" + storeShoto +
                ", storeName='" + storeName + '\'' +
                ", couponDate='" + couponDate + '\'' +
                ", couponNumber=" + couponNumber +
                '}';
    }

    public Bitmap getStoreShoto() {
        return storeShoto;
    }

    public void setStoreShoto(Bitmap storeShoto) {
        this.storeShoto = storeShoto;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCouponDate() {
        return couponDate;
    }

    public void setCouponDate(String couponDate) {
        this.couponDate = couponDate;
    }

    public Bitmap getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(Bitmap couponNumber) {
        this.couponNumber = couponNumber;
    }
}
