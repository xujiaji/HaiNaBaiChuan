package io.xujiaji.developersbackpack.utils;

import android.util.Log;

/**
 * Log打印帮助类
 */
public final class LogHelper {
    private static boolean closeLog = false;
    private static String tag = "LogHelper";

    private LogHelper() {
    }

    public static void changeTag(String tag) {
        LogHelper.tag = tag;
    }

    public static void E(String s) {
        if (closeLog) return;
        Log.e(tag, s);
    }

    public static void I(String s) {
        if (closeLog) return;
        Log.i(tag, s);
    }

    public static void D(String s) {
        if (closeLog) return;
        Log.d(tag, s);
    }

    public static void V(String s) {
        if (closeLog) return;
        Log.v(tag, s);
    }

    public static void E(String tag, String s) {
        if (closeLog) return;
        Log.e(tag, s);
    }

    public static void I(String tag, String s) {
        if (closeLog) return;
        Log.i(tag, s);
    }

    public static void D(String tag, String s) {
        if (closeLog) return;
        Log.d(tag, s);
    }

    public static void V(String tag, String s) {
        if (closeLog) return;
        Log.v(tag, s);
    }
}
