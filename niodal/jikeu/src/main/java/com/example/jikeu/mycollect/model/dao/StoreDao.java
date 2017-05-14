package com.example.jikeu.mycollect.model.dao;

import com.example.jikeu.mycollect.model.entity.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */

public class StoreDao {
    public List<Store> getStoreList(){
        List<Store> stores = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Store store = new Store();
            store.setName("商店名字");
            store.setStatus("已收藏");
            store.setMessage("商店信息商店信息商店信息商店信息商店信息商店信息商店信息商店信息商店信息");
            stores.add(store);
        }
        return stores;
    }
}
