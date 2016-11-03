package io.xujiaji.developersbackpack.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import io.xujiaji.developersbackpack.app.App;

/**
 * Toast工具类
 */
public final class ToastUtil {
    private static volatile ToastUtil mToastUtil = null;
    private Toast mToast = null;
    protected Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 获取实例
     */
    public static ToastUtil getInstance() {
        if (mToastUtil == null) {
            synchronized (ToastUtil.class) {
                mToastUtil = new ToastUtil();
            }
        }
        return mToastUtil;
    }

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     * 持续时间默认为short
     * @param tips 要显示的内容
     *            {@link Toast#LENGTH_LONG}
     */
    public void showShortT(final String tips){
        showToast(tips, -1, Toast.LENGTH_SHORT);
    }

    public void showShortT(final int tips){
        showToast(null, tips, Toast.LENGTH_SHORT);
    }

    public void showLongT(final String tips){
        showToast(tips, -1, Toast.LENGTH_LONG);
    }

    public void showLongT(final int tips){
        showToast(null, tips, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     *
     * @param tips     要显示的内容
     * @param duration 持续时间，参见{@link Toast#LENGTH_SHORT}和
     *                 {@link Toast#LENGTH_LONG}
     */
    public void showToast(final String tips, final int tipi, final int duration) {
        if (android.text.TextUtils.isEmpty(tips) && tipi <= 0) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    if (android.text.TextUtils.isEmpty(tips)) {
                        mToast = Toast.makeText(App.getAppContext(), tipi, duration);
                    } else {
                        mToast = Toast.makeText(App.getAppContext(), tips, duration);
                    }
                    mToast.show();
                } else {
                    if (android.text.TextUtils.isEmpty(tips)) {
                        mToast.setText(tipi);
                    } else {
                        mToast.setText(tips);
                    }
                    mToast.setDuration(duration);
                    mToast.show();
                }
            }
        });
    }
}
