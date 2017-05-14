package com.example.jikeu.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ChenZheSheng on 2016/3/14.
 * 说明：时间格式
 */
public class TimeUtils {

    /**
     *  时间格式转换为 mm：ss
     * @param date
     * @return
     */
    public static String getTime(long date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(new Date(date));
    }
}
