package com.example.jikeu.myorder.model.dao;

import com.example.jikeu.myorder.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */

public class OrderDao {
    public List<Order> getOrderList(){
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setOrderNumber("0088008800880088");
            order.setMessage("红色 X");
            order.setName("商品名称");
            order.setNumPrice("322.00");
            order.setStatus("未付款");
            order.setGoodsNumber("2");
            order.setPrice("161.00");
            order.setNum("2");
            orders.add(order);
        }
        return orders;
    }
}
