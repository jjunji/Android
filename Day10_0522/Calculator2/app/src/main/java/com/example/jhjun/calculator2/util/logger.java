package com.example.jhjun.calculator2.util;

import com.example.jhjun.calculator2.BuildConfig;

/**
 * Created by jhjun on 2017-05-23.
 */

public class logger {
    //public static final boolean DEBUG = true;
    public static final boolean DEBUG = BuildConfig.DEBUG_MODE;

    public void d(String tag, String msg){
        if(DEBUG){
            log.d(tag,msg);
        }
    }
}
