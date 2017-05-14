package weather.wm.com.wmweather.common.bean;

import android.text.TextUtils;

/**
 * Created by HelloKiki on 2017/3/12.
 */

public class Rank {

    private String[] first = {"pm25", "pm10", "co", "no2", "so2", "o3"};

    private int rank;
    private String province;
    private String city;
    private int aqi;

    private String pollutants;
    private int pm10;
    private int pm25;
    private int co;
    private int so2;
    private int no2;
    private int o3;
    private String firstAqi = first[0];

    private String time;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

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

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getPollutants() {
        return pollutants;
    }

    public void setPollutants(String pollutants) {
        this.pollutants = pollutants;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public int getSo2() {
        return so2;
    }

    public void setSo2(int so2) {
        this.so2 = so2;
    }

    public int getNo2() {
        return no2;
    }

    public void setNo2(int no2) {
        this.no2 = no2;
    }

    public int getO3() {
        return o3;
    }

    public void setO3(int o3) {
        this.o3 = o3;
    }

    public String getFirstAqi() {
        return firstAqi;
    }

    public void setFirstAqi(String s) {
        if(TextUtils.isEmpty(s)){
            return;
        }
        int i = Integer.parseInt(s);
        if (i >= 1 && i <= 6) {
            firstAqi = first[i];
        }
    }
}
