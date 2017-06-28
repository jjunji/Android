package com.example.jhjun.httpbbsdbconnect;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jhjun on 2017-06-28.
 */

public class DataSender {

    public void sendData(String url, String jsonString, final CallBack callBack){
        // 서브 thread task
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                String url = params[0];
                String jsonString = params[1];
                String success = null;
                try {
                    success= getData(url, jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return success;
            }

            @Override
            protected void onPostExecute(String result) {
                callBack.call(result);
            }
        }.execute(url, jsonString);
    }

    // OKHTTP 사용하기
    public String getData(String url,String jsonString) throws IOException {
        // 1. OKHttp 클라이언트를 생성하고
        OkHttpClient client = new OkHttpClient();
        // 2. 요청정보를 세팅
        // 2.1 요청 데이터
        RequestBody reqBody = new FormBody.Builder().add("jsonString", jsonString).build();
        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }


    public interface CallBack {
        public void call(String result);
    }
}
