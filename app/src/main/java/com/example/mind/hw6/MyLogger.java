package com.example.mind.hw6;

import android.util.Log;

public class MyLogger {
    private static final boolean isDebugMode= false;
    private static boolean canWriteToFile = false;
    public static void d(String tag , String text){
        if (isDebugMode){
            Log.d(tag,text);
        }



    }



}
