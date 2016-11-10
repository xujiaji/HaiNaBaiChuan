package io.xujiaji.hnbc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import io.xujiaji.hnbc.app.App;

/**
 * Created by Dmytro Denysenko on 5/6/15.
 */
public class TextViewBoldNew extends TextView {
    public TextViewBoldNew(Context context) {
        this(context, null);
    }

    public TextViewBoldNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewBoldNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.getExtraFont());
        getPaint().setFakeBoldText(true);
    }

}
