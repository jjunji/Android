package com.example.jhjun.airbnbsearch;

/**
 * Created by jhjun on 2017-07-27.
 */

public class Search {
    public static final int TYPE_ONE = 10;
    public static final int TYPE_TWO = 20;
    public static final int TYPE_ENTIRE = 30;

    public static final String AN_WIFI     = "Wifi";
    public static final String AN_AIRCON   = "Air Conditioner";
    public static final String AN_REFRIGE  = "Refrigerator";
    public static final String AN_PARKING  = "Parking";
    public static final String AN_ELEVATOR = "Elevator";

    public String checkinDate = null;
    public String checkoutDate = null;

    public int guests = 1;
    public int type;
    public int price_min;
    public int price_max;
    public String amenitie[];
}
