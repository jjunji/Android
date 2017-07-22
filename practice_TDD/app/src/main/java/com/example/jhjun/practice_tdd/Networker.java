package com.example.jhjun.practice_tdd;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jhjun on 2017-07-21.
 */

public class Networker {
    Context context;

    public void setDefault(){}

    public Networker(Context context) {
        this.context = context;
    }

    public String doNetwork(){
        try {
            URL url = new URL("http://localhost:3000");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            return br.readLine() + context.getString(R.string.app_name);

        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
    }
}
