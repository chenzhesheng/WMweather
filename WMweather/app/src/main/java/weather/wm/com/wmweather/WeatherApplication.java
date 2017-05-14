package weather.wm.com.wmweather;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by HelloKiki on 2017/3/8.
 */

public class WeatherApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        mContext=getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
