package com.example.jhjun.parsingtest;

import android.os.AsyncTask;
import android.util.Log;

import static com.example.jhjun.parsingtest.Remote.getData;

/**
 * Created by jhjun on 2017-07-06.
 */

public class Task {

    // thread 를 생성
    public static void newTask(final TaskInterface taskInterface) {

        new AsyncTask<String, Void, String>() {
            // 백그라운드 처리 함수
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    // getData 함수로 데이터를 가져온다.
                    result = getData(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                // 결과처리
                taskInterface.postExecute(result);
            }
        }.execute(taskInterface.getUrl());
    }
}
