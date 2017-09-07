package com.example.jhjun.httpurlconnection2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jhjun on 2017-06-13.
 */

public class Remote {
    public static String getData(String url) throws Exception{

        String result = "";
        // 네트워크 처리
        URL serverUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
        con.setRequestMethod("GET");

        // 응답 처리
        // 2.1 응답코드 분석
        int responseCode = con.getResponseCode();
        // 2.2 정상적인 응답처리
        if(responseCode == HttpURLConnection.HTTP_OK){  // 정상적인 코드 처리
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;

            while( (temp = br.readLine()) != null){
                result += temp;
            }
            // 2.3 오류에 대한 응답처리
        } else{
            Log.e("Network", "error_code"+ responseCode);
        }

        return result;
    }
}
