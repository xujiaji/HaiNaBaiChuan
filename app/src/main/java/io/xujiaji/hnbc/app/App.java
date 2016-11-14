package io.xujiaji.hnbc.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.squareup.leakcanary.LeakCanary;

import cn.bmob.v3.Bmob;
import io.xujiaji.hnbc.config.C;
import me.drakeet.library.CrashWoodpecker;
import me.drakeet.library.PatchMode;

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
        initLeak();
        initCrashWoodpecker();
        initTypeface();
        try {
            Bmob.initialize(this, C.CBmob.BMOB_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 崩溃提示
     */
    private void initCrashWoodpecker() {
        CrashWoodpecker.instance()
                .withKeys("widget", "me.drakeet")
                .setPatchMode(PatchMode.SHOW_LOG_PAGE)
                .setPatchDialogUrlToOpen("https://drakeet.me")
                .setPassToOriginalDefaultHandler(true)
                .flyTo(this);
    }

    /**
     * 内存泄露检测
     */
    private void initLeak() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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
