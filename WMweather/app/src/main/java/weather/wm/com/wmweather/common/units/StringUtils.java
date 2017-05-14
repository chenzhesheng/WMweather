package weather.wm.com.wmweather.common.units;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by HelloKiki on 2017/3/12.
 */

public class StringUtils {

    public static boolean IsSubscript(String text) {
        if (text.length() >= 2) {
            if (text.charAt(text.length() - 2) >= 'A' && text.charAt(text.length() - 2) <= 'Z') {
                if (text.charAt(text.length() - 1) >= '1' && text.charAt(text.length() - 1) <= '9') {
                    return true;
                }
            }
        }
        return false;
    }

    public static String listToString(List<String> lists) {
        StringBuilder string = new StringBuilder();
        for (String s : lists) {
            if (!TextUtils.isEmpty(string.toString())) {
                string.append(",");
            }
            string.append(s);
        }
        return string.toString();
    }
}
