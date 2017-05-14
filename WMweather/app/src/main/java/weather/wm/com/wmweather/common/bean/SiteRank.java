package weather.wm.com.wmweather.common.bean;

/**
 * Created by ChenZheSheng on 2017/3/19.
 */

public class SiteRank{
    private String rank;
    private String address;
    private String pollutants;

    public SiteRank(String rank, String address, String pollutants) {
        this.rank = rank;
        this.address = address;
        this.pollutants = pollutants;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPollutants() {
        return pollutants;
    }

    public void setPollutants(String pollutants) {
        this.pollutants = pollutants;
    }

    @Override
    public String toString() {
        return "SiteRank{" +
                "rank='" + rank + '\'' +
                ", address='" + address + '\'' +
                ", pollutants='" + pollutants + '\'' +
                '}';
    }
}
