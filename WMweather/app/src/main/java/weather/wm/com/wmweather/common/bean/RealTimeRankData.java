package weather.wm.com.wmweather.common.bean;

/**
 * Created by HelloKiki on 2017/3/31.
 */

public class RealTimeRankData implements Comparable<RealTimeRankData> {
    private int rank;
    private String stationName;
    private String stationId;
    private int value;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RealTimeRankData{" +
                "rank=" + rank +
                ", stationName='" + stationName + '\'' +
                ", stationId='" + stationId + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public int compareTo(RealTimeRankData realTimeRankData) {
        return this.getValue()>realTimeRankData.getValue()?1
                :(this.getValue()==realTimeRankData.getValue())?0:-1;
    }
}
