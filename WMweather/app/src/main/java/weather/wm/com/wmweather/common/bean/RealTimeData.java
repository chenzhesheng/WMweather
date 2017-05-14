package weather.wm.com.wmweather.common.bean;

/**
 * Created by HelloKiki on 2017/3/15.
 */

public class RealTimeData implements Comparable<RealTimeData> {

    private int rank;

    private String address;

    private int pollutant;
    private int pm25;
    private int pm10;
    private int so2;
    private int o3;
    private int co;
    private int no2;
    private int aqi;
    private int aqiFirst;
    private String time;
    private String stationName;
    private double lat;
    private double lng;
    private String index;
    private String stationId;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPollutant() {
        return pollutant;
    }

    public void setPollutant(int pollutant) {
        this.pollutant = pollutant;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getSo2() {
        return so2;
    }

    public void setSo2(int so2) {
        this.so2 = so2;
    }

    public int getO3() {
        return o3;
    }

    public void setO3(int o3) {
        this.o3 = o3;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public int getNo2() {
        return no2;
    }

    public void setNo2(int no2) {
        this.no2 = no2;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getAqiFirst() {
        return aqiFirst;
    }

    public void setAqiFirst(int aqiFirst) {
        this.aqiFirst = aqiFirst;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "RealTimeData{" +
                "rank=" + rank +
                ", address='" + address + '\'' +
                ", pollutant='" + pollutant + '\'' +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", so2=" + so2 +
                ", o3=" + o3 +
                ", co=" + co +
                ", no2=" + no2 +
                ", aqi=" + aqi +
                ", aqiFirst=" + aqiFirst +
                ", time='" + time + '\'' +
                ", stationName='" + stationName + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", index='" + index + '\'' +
                ", stationId='" + stationId + '\'' +
                '}';
    }

    @Override
    public int compareTo(RealTimeData realTimeData) {
        return this.getPollutant() > realTimeData.getPollutant() ? 1
                : (this.getPollutant() == realTimeData.getPollutant()) ? 0 : -1;
    }
}
