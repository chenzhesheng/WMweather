package com.andexert.calendarlistview.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HelloKiki on 2017/3/16.
 */
public class HourCalendarView extends View {

    private String mTitle = "123456789";
    private String mHourTexts[] = {"0~6", "7~12", "13~18", "19~24"};

    private int mWidth;
    private int mColWidth;
    private int mRowHeight = 32;
    private int mRows = 4;
    private int mCol = 7;
    private int mAllNum = mRows * mCol;  //总方块数
    private int mTitleHeight = 0;   //标题的高度
    private int mColorHeight;   //每个等级颜色的高度
    private int mLevel = 1;

    private int mTitleSize;
    private int mTextSize;
    private Paint mPaintBg;
    private Paint mPaintTextBg;
    private Paint mPaintText;
    private Paint mPaintColorLevel;

    private Map<Integer,Integer> mData=new HashMap<>();

    public HourCalendarView(Context context) {
        this(context, null);
    }

    public HourCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources resources = context.getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HourCalendarView);
        mRowHeight = typedArray.getDimensionPixelSize(R.styleable.HourCalendarView_col_height, resources.getDimensionPixelSize(R.dimen.col_height));
        mColorHeight = typedArray.getDimensionPixelSize(R.styleable.HourCalendarView_color_height, resources.getDimensionPixelSize(R.dimen.color_height));
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.HourCalendarView_text_size, resources.getDimensionPixelSize(R.dimen.text_size_value));
        mTitleSize = typedArray.getDimensionPixelSize(R.styleable.HourCalendarView_title_text_size, resources.getDimensionPixelSize(R.dimen.title_text_size));
        mTitleHeight = typedArray.getDimensionPixelSize(R.styleable.HourCalendarView_title_height, resources.getDimensionPixelSize(R.dimen.title_height));
        initPaint();
        initData();
    }

    public void setTitle(String title) {
        this.mTitle = title;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawTitle(canvas);
        drawBG(canvas);
        drawVerticalLine(canvas);
        drawHorizontalLine(canvas);
        drawContent(canvas);
    }

    private void initPaint() {
        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setColor(Color.WHITE);

        mPaintTextBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextBg.setColor(Color.parseColor("#CCCCCC"));
        mPaintTextBg.setStrokeWidth(10f);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(mTextSize);

        mPaintColorLevel = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColorLevel.setColor(Color.GREEN);

    }

    public void initData(){
        mData.clear();
        for (int i=0;i<28;i++){
            mData.put(i,1);
        }
    }

    public void setData(int hour,int value){
        int index=hour;
        if(hour==0){
            index=27;
        }else{
            if(hour>=19){
                index+=3;
            }else if(hour>=13){
                index+=2;
            }else if(hour>=7){
                index+=1;
            }
        }
        mData.put(index,value);
    }

    public void build(){
        invalidate();
    }

    private void drawTitle(Canvas canvas) {
        mPaintText.setTextSize(mTitleSize);
        RectF rectF = new RectF(0, 0, mWidth, mTitleHeight);
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt();
        mPaintText.setTextAlign(Paint.Align.CENTER);
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawRect(rectF, mPaintBg);
        canvas.drawText(mTitle, rectF.centerX(), baseline, mPaintText);
    }

    private void drawBG(Canvas canvas) {
        int num = 0;

        while (num < mAllNum) {
            int x = (num % mCol) * mColWidth;
            int y = (num / mCol) * mRowHeight + mTitleHeight;

            if (num % 7 == 0) {
                RectF rectF = new RectF(x, y, x + mColWidth, y + mRowHeight);
                canvas.drawRect(rectF, mPaintTextBg);
            } else {
                RectF rectF = new RectF(x, y, x + mColWidth, y + mRowHeight);
                canvas.drawRect(rectF, mPaintBg);
            }

            num++;

        }

    }

    private void drawVerticalLine(Canvas canvas) {
        mPaintTextBg.setStrokeWidth(1f);
        for (int i = 0; i < mCol; i++) {
            canvas.drawLine(i * mColWidth, mTitleHeight, i * mColWidth, mRowHeight * mRows + mTitleHeight, mPaintTextBg);
        }

    }

    private void drawHorizontalLine(Canvas canvas) {
        for (int i = 0; i < mAllNum; i++) {
            int x = (i % mCol) * mColWidth;
            int y = (i / mCol) * mRowHeight + mTitleHeight;
            if (i % mCol == 0) {
                canvas.drawLine(x, y, mWidth, y, mPaintTextBg);
            }
        }
        canvas.drawLine(0, mRowHeight * mRows + mTitleHeight, mWidth, mRowHeight * mRows + mTitleHeight, mPaintTextBg);
    }

    private void drawContent(Canvas canvas) {
        mPaintText.setTextSize(mTextSize);
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt();
        int t = 0;
        for (int i = 0; i < mAllNum; i++) {
            int x = (i % mCol) * mColWidth;
            int y = (i / mCol) * mRowHeight + mTitleHeight;
            RectF rectF = new RectF(x, y, x + mColWidth, y + mRowHeight);
            int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);

            if (i % mCol == 0) {
                canvas.drawText(mHourTexts[t], rectF.centerX(), baseline, mPaintText);
                t++;
            } else {
                int value=mData.get(i);
                mPaintColorLevel.setColor(getColorForValue(value));
                int level=getColorForLevel(value);
                canvas.drawRect(x + 1, y + mRowHeight - mColorHeight * level, x + mColWidth - 1,(y + mRowHeight - mColorHeight * level)+mColorHeight, mPaintColorLevel);  //x缩进1，让每个方块有点空隙
            }

        }

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mColWidth = mWidth / mCol;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mRows + mTitleHeight);
    }

    public int getColorForValue(int value) {
        if (value > 0 && value <= 50) {
            return Color.parseColor("#49db00");
        } else if (value > 50 && value <= 100) {
            return Color.parseColor("#ffff00");
        } else if (value > 100 && value <= 150) {
            return Color.parseColor("#ff6d00");
        } else if (value > 150 && value <= 200) {
            return Color.parseColor("#ff0000");
        } else if (value > 200 && value <= 300) {
            return Color.parseColor("#9b004c");
        } else if (value > 300) {
            return Color.parseColor("#920024");
        } else {
            return Color.parseColor("#49db00");
        }
    }
    public int getColorForLevel(int value) {
        if (value > 0 && value <= 50) {
            return 1;
        } else if (value > 50 && value <= 100) {
            return 2;
        } else if (value > 100 && value <= 150) {
            return 3;
        } else if (value > 150 && value <= 200) {
            return 4;
        } else if (value > 200 && value <= 300) {
            return 5;
        } else if (value > 300) {
            return 6;
        } else {
            return 1;
        }
    }
}
