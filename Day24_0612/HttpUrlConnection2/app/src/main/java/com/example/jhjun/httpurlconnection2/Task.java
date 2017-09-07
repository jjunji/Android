package com.example.jhjun.httpurlconnection2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by jhjun on 2017-06-13.
 */

public class Task {
    public static void newTask(final TaskInterface taskinterface){

        new AsyncTask<String,Void,String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    result = Remote.getData(params[0]);
                    Log.i("Network", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                taskinterface.postExecute(result);
            }
        }.execute(taskinterface.getUrl());
    }



}
