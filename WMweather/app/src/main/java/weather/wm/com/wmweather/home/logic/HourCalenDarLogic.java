package weather.wm.com.wmweather.home.logic;

/**
 * Created by HelloKiki on 2017/4/12.
 */

public class HourCalenDarLogic {

    public static int getHourByDate(String date) {
        String hour = date.split(" ")[1].split(":")[0];
        return Integer.parseInt(hour);
    }

}
