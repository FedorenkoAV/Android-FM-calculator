package ru.fmproject.android.calculator;

import ru.fmproject.android.calculator.BuildConfig;

public final class L {
    // Для простого примера однообразно и длинновато, принцип был бы ясен
    // на примере одного метода, зато этот код можно просто копипастнуть

    private L() {
    }

    // Здесь же поселим стектрейс
    public static void printStackTrace(Throwable t) {
        if (BuildConfig.DEBUG)
            t.printStackTrace();
//        StackTraceElement[] stackTraceElements = t.getStackTrace();
//
//            for (int i = 0; i < stackTraceElements.length; i++) {
//                android.util.Log.d(tag, i + ": " + stackTraceElements[i].toString());
//            }
    }

    public static int v(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.v(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.v(tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.d(tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.d(tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.i(tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.i(tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.w(tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.w(tag, msg, tr);
    }

    public static int w(String tag, Throwable tr) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.w(tag, tr);
    }

    public static int e(String tag, String msg) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.e(tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (!BuildConfig.DEBUG)
            return 0;
        return android.util.Log.e(tag, msg, tr);
    }
}
