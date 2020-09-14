package com.example.sakura.utils;


import com.example.sakura.BuildConfig;
import com.orhanobut.logger.Logger;

public class LoggerUtils {
    public static boolean ISOPEN = BuildConfig.APP_ISDEBUG;

    public static void v(String tag, String msg) {
        if (ISOPEN) {
            Logger.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (ISOPEN) {
            Logger.d(tag, msg);
        }

    }

    public static void i(String tag, String msg) {
        if (ISOPEN) {
            Logger.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (ISOPEN) {
            Logger.w(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (ISOPEN) {
            Logger.e(tag, msg);
        }

    }
}