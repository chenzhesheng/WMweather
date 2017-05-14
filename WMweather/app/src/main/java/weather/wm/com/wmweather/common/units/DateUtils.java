package weather.wm.com.wmweather.common.units;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by HelloKiki on 2017/3/16.
 */

public class DateUtils {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public static String formatYMD(long time) {
        return format.format(time);
    }


    public static String getMonthOfOne() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_MONTH) - 1));
        return format.format(calendar.getTime());
    }

    public static String getMonthOfOneBefore(int before) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_MONTH) - 1));
        calendar.add(Calendar.MONTH, -before);
        return format.format(calendar.getTime());
    }

    public static String getDayBefore(int before) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -before);
        return format.format(calendar.getTime());
    }

    public static String getMonthBefore(int before) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -before);
        return format.format(calendar.getTime());
    }

    public static String getYearBefore(int before) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -before);
        return format.format(calendar.getTime());
    }

    public static String getCalenderAfter(int year,int month,int day,int after){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day);
        calendar.add(Calendar.DAY_OF_MONTH,after);
        return format.format(calendar.getTime());
    }

    public static long getLongByString(String str){
        try {
            return formatDate.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("2017",e.getMessage());
        }
        return 1;
    }
}
