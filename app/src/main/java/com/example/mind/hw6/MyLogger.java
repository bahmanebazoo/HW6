package com.example.mind.hw6;

import android.util.Log;

public class MyLogger {
    private static final boolean isDebugMode = false;
    private static boolean canWriteToFile = false;
    public static final String DEBUGIGNG_TAG="debugging_tag";
    public static final String ERROR_TAG = "error_tag";
    public static final String INFO_TAG = "information_tag";
    public static final String WARN_TAG= "warning_tag";
    public static final String ASSERT_TAG = "what_Terrible_failure";
    public static void d(String tag, String text) {
        if (isDebugMode) {
            Log.d(tag, text);
        }
    }

    public static void i(String tag, String text) {
        if (isDebugMode) {
            Log.d(tag, text);
        }
    }

    public static void w(String tag, String text) {
        if (isDebugMode) {
            Log.d(tag, text);
        }
    }

    public static void e(String tag, String text) {
        if (isDebugMode) {
            Log.d(tag, text);
        }
    }

    public static void wtf(String tag, String text) {
        if (isDebugMode) {
            Log.d(tag, text);
        }
    }


}



