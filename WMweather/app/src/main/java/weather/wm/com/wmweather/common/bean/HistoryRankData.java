package weather.wm.com.wmweather.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2017/4/5.
 */

public class HistoryRankData {

    private String message;
    private String status;
    private List<HistoryData> mHistoryDatas;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HistoryData> getHistoryDatas() {
        return mHistoryDatas;
    }

    public void setHistoryDatas(List<HistoryData> historyDatas) {
        if(historyDatas == null){
            historyDatas = new ArrayList<>();
        }
        mHistoryDatas = historyDatas;
    }

    @Override
    public String toString() {
        return "HistoryRankData{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", mHistoryDatas=" + mHistoryDatas +
                '}';
    }
}
