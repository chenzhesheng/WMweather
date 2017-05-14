package com.example.jikeu.mycoupon.model.dao;

import com.example.jikeu.mycoupon.model.entity.Coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class CouponDao {
    public List<Coupon> getCouponList(){
        List<Coupon> coupons = new ArrayList<>();
        for (int i =0 ;i<2;i++){
            Coupon coupon = new Coupon();
            coupon.setCouponDate("2016/8/14-2016/9/14");
            coupon.setStoreName("极客U");
            coupons.add(coupon);
        }
        return coupons;
    }
}
