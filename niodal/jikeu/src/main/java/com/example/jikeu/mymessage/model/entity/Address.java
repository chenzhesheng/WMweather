package com.example.jikeu.mymessage.model.entity;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class Address {
    private String name;
    private String number;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", address='" + address + '\'' +
                '}';
    }
}
