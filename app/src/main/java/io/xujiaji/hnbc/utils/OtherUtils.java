package io.xujiaji.hnbc.utils;

import java.text.SimpleDateFormat;

/**
 * 其他工具类
 */
public class OtherUtils {
    private OtherUtils() {}
    public static String currDay() {
        return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
    }
}
