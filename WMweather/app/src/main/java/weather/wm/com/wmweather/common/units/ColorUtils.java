package weather.wm.com.wmweather.common.units;

import android.graphics.Color;

/**
 * Created by HelloKiki on 2017/4/5.
 */

public class ColorUtils {

    public static int getColorForValue(int value) {
        if (value > 0 && value <= 50) {
            return Color.parseColor("#49db00");
        } else if (value > 50 && value <= 100) {
            return Color.parseColor("#ffff00");
        } else if (value > 100 && value <= 150) {
            return Color.parseColor("#ff6d00");
        } else if (value > 150 && value <= 200) {
            return Color.parseColor("#ff0000");
        } else if (value > 200 && value <= 300) {
            return Color.parseColor("#9b004c");
        } else if (value > 300) {
            return Color.parseColor("#920024");
        } else {
            return Color.parseColor("#49db00");
        }
    }
}
