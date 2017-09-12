package com.jjunji.android.logtest.Util;

import android.os.StrictMode;
import android.util.Log;

import com.jjunji.android.logtest.BuildConfig;

/**
 * Created by jhjun on 2017-09-12.
 */

public class Logger {
    public static void LOGV(String msg) {
        Logger(Log.VERBOSE, msg);
    }

    public static void LOGD(String msg) {
        Logger(Log.DEBUG, msg);
    }

    public static void LOGI(String msg) {
        Logger(Log.INFO, msg);
    }

    public static void LOGW(String msg) {
        Logger(Log.WARN, msg);
    }

    public static void LOGE(String msg) {
        Logger(Log.ERROR, msg);
    }

    private static void Logger(int priority, String msg) {
        if (BuildConfig.DEBUG) {
            StringBuilder msgBuilder = new StringBuilder();
            StringBuilder msgBuilder2 = new StringBuilder();

            for(int i=0; i<Thread.currentThread().getStackTrace().length; i++){
                msgBuilder2.append(Thread.currentThread().getStackTrace()[i].getMethodName() + " / ");
            }

            Log.println(priority, "test" , msgBuilder2.toString());

            msgBuilder.append("[").append(Thread.currentThread().getStackTrace()[3].getMethodName())
                    .append("()").append("]").append(" :: ").append(msg)
                    .append(" (").append(Thread.currentThread().getStackTrace()[4].getFileName()).append(":")
                    .append(Thread.currentThread().getStackTrace()[4].getLineNumber()).append(")");

            Log.println(priority, Thread.currentThread().getStackTrace()[4].getFileName()+"::", msgBuilder.toString());
        }
    }
}
