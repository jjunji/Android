package com.example.jhjun.httpbbs;

import android.os.AsyncTask;

/**
 * Created by jhjun on 2017-06-26.
 */

public class Loader {

    public void getData(String url){
        new AsyncTask<String, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                String url = params[0];
                return null;
            }
        }.execute(url);
    }
}
