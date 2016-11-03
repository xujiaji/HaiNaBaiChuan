package io.xujiaji.hnbc.app;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import io.xujiaji.hnbc.config.Constant;

/**
 * application类
 */
public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        try {
            Bmob.initialize(this, Constant.CBmob.BMOB_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return mContext;
    }
}
