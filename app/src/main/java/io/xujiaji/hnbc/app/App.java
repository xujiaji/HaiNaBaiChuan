package io.xujiaji.hnbc.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import cn.bmob.v3.Bmob;
import io.xujiaji.hnbc.config.C;

/**
 * application类
 */
public class App extends Application {
    private static final String EXTRA_FONT = "fonts/mini_simple.ttf";
    private static Context mContext;
    private static Typeface extraFont = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        initTypeface();
        try {
            Bmob.initialize(this, C.CBmob.BMOB_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initTypeface() {
        if (extraFont != null) {
            return;
        }
        extraFont = Typeface.createFromAsset(getAssets(), EXTRA_FONT);
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static Typeface getExtraFont() {
        return extraFont;
    }
}
