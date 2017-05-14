package weather.wm.com.wmweather.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import weather.wm.com.wmweather.R;

/**
 * Created by HelloKiki on 2017/3/10.
 */

public class DoubleTextView extends RelativeLayout {

    private TextView mTextViewKey;
    private TextView mTextViewValue;

    public DoubleTextView(Context context) {
        this(context, null);
    }

    public DoubleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleTextView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.layout_double_text_view, this);
        mTextViewKey = (TextView) findViewById(R.id.text_view_key);
        mTextViewValue = (TextView) findViewById(R.id.text_view_value);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DoubleTextView, defStyle, 0);
        String key = ta.getString(R.styleable.DoubleTextView_key_text);
        if (!TextUtils.isEmpty(key)) {
            mTextViewKey.setText(key);
        }
        String value = ta.getString(R.styleable.DoubleTextView_value_text);
        if (!TextUtils.isEmpty(value)) {
            mTextViewValue.setText(value);
        }
        Drawable keyIcon = ta.getDrawable(R.styleable.DoubleTextView_key_icon);
        if (keyIcon != null) {
            keyIcon.setBounds(0, 0, keyIcon.getMinimumWidth(), keyIcon.getMinimumHeight());
            mTextViewKey.setCompoundDrawables(keyIcon, null, null, null);
        }
        Drawable valueIcon = ta.getDrawable(R.styleable.DoubleTextView_value_icon);
        if (valueIcon != null) {
            valueIcon.setBounds(0, 0, valueIcon.getMinimumWidth(), valueIcon.getMinimumHeight());
            mTextViewValue.setCompoundDrawables(null, null, valueIcon, null);
        }
    }

    public void setValueText(String text) {
        mTextViewValue.setText(text);
    }

    public String getValueText(){
        return  mTextViewValue.getText().toString();
    }
}
