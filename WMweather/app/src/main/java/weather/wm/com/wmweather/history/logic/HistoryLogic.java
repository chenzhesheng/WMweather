package weather.wm.com.wmweather.history.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.HistoryData;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;

/**
 * Created by HelloKiki on 2017/3/29.
 */

public class HistoryLogic {

    Context context;
    private Paint mPaint;
    private List<HistoryData> mData;

    public HistoryLogic(Context context) {
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setFakeBoldText(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(28f);
        mData = new ArrayList<>();
    }

    public void saveData(List<HistoryData> datas) {
        mData.clear();
        mData.addAll(datas);
    }

    public Bitmap createBitmap(int value) {
        Bitmap bitmap;

        if (value > 0 && value <= 50) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b1).copy(Bitmap.Config.ARGB_8888, true);
        } else if (value > 50 && value <= 100) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b2).copy(Bitmap.Config.ARGB_8888, true);
        } else if (value > 100 && value <= 150) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b3).copy(Bitmap.Config.ARGB_8888, true);
        } else if (value > 150 && value <= 200) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b4).copy(Bitmap.Config.ARGB_8888, true);
        } else if (value > 200 && value <= 300) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b5).copy(Bitmap.Config.ARGB_8888, true);
        } else if (value > 300) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b6).copy(Bitmap.Config.ARGB_8888, true);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_map_b1).copy(Bitmap.Config.ARGB_8888, true);
        }
        Canvas canvas = new Canvas(bitmap);
        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawText(value + "", rectF.centerX(), rectF.centerY(), mPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

    public void loadArea(String cityId, Response.Listener<String> listener, Response.ErrorListener errorListener){
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", cityId);
        RequestUtils.post(UrlUtils.AREA, header, body, listener, errorListener);
    }

    public void loadType(String cityId, Response.Listener<String> listener, Response.ErrorListener errorListener){
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", cityId);
        RequestUtils.post(UrlUtils.TYPE, header, body, listener, errorListener);
    }

    public List<Integer> getValueFormAQI() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getAqi());
        }
        return data;
    }

    public List<Integer> getValueFormPM25() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getPm25());
        }
        return data;
    }

    public List<Integer> getValueFormPM10() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getPm10());
        }
        return data;
    }

    public List<Integer> getValueFormCO() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getCo());
        }
        return data;
    }

    public List<Integer> getValueFormNO2() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getNo2());
        }
        return data;
    }

    public List<Integer> getValueFormSO2() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getSo2());
        }
        return data;
    }

    public List<Integer> getValueFormO3() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            data.add(mData.get(i).getO3());
        }
        return data;
    }

}
