package com.example.jikeu.mycollect.model.dao;

import com.example.jikeu.mycollect.model.entity.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */

public class ContentDao {
    public List<Content> getContentList(){
        List<Content> contents = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Content content = new Content();
            content.setName("商店名字");
            content.setPrice("123.00");
            content.setMessage("商店信息商店信息商店信息商店信息商店信息商店信息商店信息商店信息商店信息");
            contents.add(content);
        }
        return contents;
    }
}
