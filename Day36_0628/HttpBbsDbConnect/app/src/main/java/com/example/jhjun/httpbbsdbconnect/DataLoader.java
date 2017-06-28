package com.example.jhjun.httpbbsdbconnect;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by jhjun on 2017-06-28.
 */

public class DataLoader {

    public void getData(String url, final CallBack callback){
        new AsyncTask<String, Void, List<Bbs>>(){
            @Override
            protected List<Bbs> doInBackground(String... params) {
                // url 가져오기
                String url = params[0];
                String result = getDataFromUrl(url); // json String

                Gson gson = new Gson();
                Data data = gson.fromJson(result, Data.class);

                return data.bbsList;
            }

            @Override
            protected void onPostExecute(List<Bbs> list) {
                callback.setData(list);
            }
        }.execute(url);
    }

    public interface CallBack {
        public void setData(List<Bbs> list);
    }

    public String getDataFromUrl(String url){
        StringBuilder result = new StringBuilder();
        try {
            Log.e("URL",""+url);
            // 1. 요청처리
            URL serverUrl = new URL(url);
            // 주소에 해당하는 서버의 socket 을 연결
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
            // OutputStream으로 데이터를 요청
            con.setRequestMethod("GET");

            // 2. 응답처리
            // 응답의 유효성 검사
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = // 줄단위로 데이터를 읽기 위해서 버퍼에 담는다. 물론 속도도 빨라지겠지...
                        new BufferedReader(new InputStreamReader(con.getInputStream()));
                String temp = "";
                while( (temp = br.readLine()) != null){
                    result.append(temp+"\n");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

}
