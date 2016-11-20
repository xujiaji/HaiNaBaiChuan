package io.xujiaji.hnbc.utils;

import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import io.xujiaji.hnbc.R;

/**
 * Activity工具类
 */
public class ActivityUtils {
    /**
     * 是否退出了Activity
     */
    private static boolean isExit = false;

    /**
     * 作用如：2s内双击两次返回则退出程序
     * @return 是否退出程序
     */
    public static boolean exitBy2Click() {
        return exitBy2Click(2000);
    }

    /**
     * 在某个时间段内双击两次
     * @param space 两次点击最大时间间隔
     * @return 是否退出
     */
    public static boolean exitBy2Click(int space) {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, space); // 如果space毫秒内没有按下返回键，则启动定时器取消掉刚才执行的任务
            return false;
        } else {
            return true;
        }
    }


    /**
     * 初始化Toolbar，添加返回按钮，set title
     * @param toolbar
     */
    public static void initBar(Toolbar toolbar, @StringRes int title) {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        TextView titleView = (TextView) LayoutInflater.from(toolbar.getContext()).inflate(R.layout.text_view_new_title, null);
        titleView.setText(title);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        toolbar.addView(titleView, params);
    }


    public static void initSatus(View view) {
        int statusHeight = ScreenUtils.getStatusHeight(view.getContext());
        ViewGroup.LayoutParams statusParams = view.getLayoutParams();
        statusParams.height = statusHeight;
        view.setLayoutParams(statusParams);
    }

    private static long lastClickTime;
    public static boolean isFastClick() {
        return isFastClick(false);
    }

    public static boolean isFastClick(boolean isPrompt) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD < 2000) {
            if (isPrompt) {
                ToastUtil.getInstance().showShortT("重复点击！");
            }
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
