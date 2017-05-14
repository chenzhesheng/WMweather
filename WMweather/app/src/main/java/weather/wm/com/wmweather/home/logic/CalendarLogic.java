package weather.wm.com.wmweather.home.logic;

import java.util.ArrayList;
import java.util.List;

import weather.wm.com.wmweather.common.bean.Rank;

/**
 * Created by HelloKiki on 2017/4/8.
 */

public class CalendarLogic {
    private final int AQI = 0;
    private final int PM25 = 1;
    private final int PM10 = 2;
    private final int CO = 3;
    private final int SO2 = 4;
    private final int NO2 = 5;
    private final int O3 = 6;

    public List<Rank> mRankList;

    public CalendarLogic() {
        mRankList = new ArrayList<>();
    }

    public void addRankList(List<Rank> rankList) {
        mRankList.addAll(rankList);
    }

    public void clearData(){
        mRankList.clear();
    }

    public List<Rank> selectDataForDate(int start, int end) {
        return mRankList.subList(start, end);
    }

    public List<Integer> selectAQIForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getAqi());
        }
        return datas;
    }

    public List<Integer> selectPM25ForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getPm25());
        }
        return datas;
    }

    public List<Integer> selectPM10ForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getPm10());
        }
        return datas;
    }

    public List<Integer> selectCOForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getCo());
        }
        return datas;
    }

    public List<Integer> selectSO2ForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getSo2());
        }
        return datas;
    }

    public List<Integer> selectNO2ForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getNo2());
        }
        return datas;
    }
    public List<Integer> selectO3ForDate(int start, int end) {
        List<Rank> rankList = mRankList.subList(start, end);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < rankList.size(); i++) {
            datas.add(rankList.get(i).getO3());
        }
        return datas;
    }

    public List<Rank> selectDataForDate(String start, String end) {
        int s = 0;
        int e = 0;
        for (int i = 0; i < mRankList.size(); i++) {
            if (mRankList.get(i).getTime().split(" ")[0].equals(start)) {
                s = i;
            }
            if (mRankList.get(i).getTime().split(" ")[0].equals(end)) {
                e = i;
            }
        }
        return mRankList.subList(s, e);
    }

    public int getIndex(String s) {
        for (int i = 0; i < mRankList.size(); i++) {
            if (mRankList.get(i).getTime().split(" ")[0].equals(s)) {
                return i + 1;
            }
        }
        return mRankList.size();
    }

}
