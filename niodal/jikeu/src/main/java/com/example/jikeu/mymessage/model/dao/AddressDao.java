package com.example.jikeu.mymessage.model.dao;

import com.example.jikeu.mymessage.model.entity.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class AddressDao {
    public List<Address> getAddressData(){
        List<Address> addresses = new ArrayList<>();
        for (int i=0;i<3;i++){
            Address address = new Address();
            address.setName("陈生");
            address.setAddress("广州市天河区岑村");
            address.setNumber("13800138000");
            addresses.add(address);
        }
        return addresses;
    }
}
