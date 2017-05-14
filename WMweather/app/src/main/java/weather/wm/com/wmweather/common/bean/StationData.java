package weather.wm.com.wmweather.common.bean;

/**
 * Created by HelloKiki on 2017/4/4.
 */

public class StationData {

    private String province;
    private String city;
    private int firstAqi;
    private int aqi;
    private String time;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getFirstAqi() {
        return firstAqi;
    }

    public void setFirstAqi(int firstAqi) {
        this.firstAqi = firstAqi;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "StationData{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", firstAqi=" + firstAqi +
                ", aqi=" + aqi +
                ", time='" + time + '\'' +
                '}';
    }
}
