package com.example.jhjun.httpbbs;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jhjun on 2017-06-27.
 */

public class DataSender {

    public void sendData(String url, String jsonString, final CallBack callBack){
        // 서브 thread task
        new AsyncTask<String, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(String... params) {
                String url = params[0];
                String jsonString = params[1];
                boolean success = sendJsonStringByUrl(url, jsonString);
                return success;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                callBack.call(result);
            }
        }.execute(url, jsonString);
    }

    // 데이터 전송 처리하고, 결과
    private boolean sendJsonStringByUrl(String url, String jsonString){
        try {
            // 1. 서버와 연결하기
            URL serverUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
            // 2. 전송방식 결정
            con.setRequestMethod("POST");
            // 3. 데이터 전송
            con.setDoOutput(true); // 전송할 데이터가 있다고 알려줌
            // 키=값의 형태로 전송할 데이터 모양을 만들어주고,
            String data = "jsonString=" + jsonString;
            OutputStream os = con.getOutputStream(); // 스트림을 열고
            os.write(data.getBytes()); // 데이터를 byte 형으로 변환하여 전송한다.
            os.flush(); // 버퍼가 가득차지 않았어도 데이터를 전송하기 위해서 호출
            os.close();

            // 4. 전송결과 체크
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
            Log.e("HttpError", "errorCode="+responseCode);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public interface CallBack {
        public void call(boolean result);
    }
}