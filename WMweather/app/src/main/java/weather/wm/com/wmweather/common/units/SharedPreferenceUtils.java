package weather.wm.com.wmweather.common.units;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HelloKiki on 2017/4/9.
 */

public class SharedPreferenceUtils {

    static SharedPreferences preferences;

    public static void clear(Context context){
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("id");
        editor.remove("username");
        editor.remove("status");
        editor.remove("sessionId");
        editor.commit();
    }

    public static void setAccountId(Context context, String id) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.commit();
    }

    public static String getAccountId(Context context) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        return preferences.getString("id", "");
    }

    public static void setUserName(Context context,String username){
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public static String getUsername(Context context) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }

    public static void setStatus(Context context,String status){
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("status", status);
        editor.commit();
    }

    public static String getStatus(Context context) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        return preferences.getString("status", "");
    }

    public static void setSessionId(Context context,String sessionId){
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sessionId", sessionId);
        editor.commit();
    }

    public static String getSessionId(Context context) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        return preferences.getString("sessionId", "");
    }

    public static void setCurrentCityId(Context context,String id){
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("current_city_id", id);
        editor.commit();
    }

    public static String getCurrentCityId(Context context) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
            return preferences.getString("current_city_id", "110100");
    }

    public static void setCurrentCityName(Context context,String city){
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("current_city", city);
        editor.commit();
    }

    public static String getCurrentCityName(Context context) {
        preferences = context.getSharedPreferences("weather", context.MODE_PRIVATE);
        return preferences.getString("current_city", "北京市");
    }


}
