package com.example.jhjun.listview;

/**
 * Created by jhjun on 2017-06-06.
 */

public class SingerItem {

    String name;
    String mobile;
    int age;
    int resId;

    public SingerItem(String name, String mobile){
        this.name = name;
        this.mobile = mobile;
    }

    public SingerItem(String name, String mobile, int age, int resId){
        this.age = age;
        this.resId = resId;
        this.name = name;
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
