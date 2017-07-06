package com.example.jhjun.parsingtest.domain;

/**
 * Created by jhjun on 2017-07-06.
 */

public class Row
{
    private String SUBWAYCODE;

    private String DESTSTATION_CODE;

    private String DESTSTATION_NAME;

    private String STATION_CD;

    private String SUBWAYNAME;

    private String LEFTTIME;

    private String TRAINCODE;

    private String ARRIVETIME;

    private String FR_CODE;

    public String getSUBWAYCODE ()
    {
        return SUBWAYCODE;
    }

    public void setSUBWAYCODE (String SUBWAYCODE)
    {
        this.SUBWAYCODE = SUBWAYCODE;
    }

    public String getDESTSTATION_CODE ()
    {
        return DESTSTATION_CODE;
    }

    public void setDESTSTATION_CODE (String DESTSTATION_CODE)
    {
        this.DESTSTATION_CODE = DESTSTATION_CODE;
    }

    public String getDESTSTATION_NAME ()
    {
        return DESTSTATION_NAME;
    }

    public void setDESTSTATION_NAME (String DESTSTATION_NAME)
    {
        this.DESTSTATION_NAME = DESTSTATION_NAME;
    }

    public String getSTATION_CD ()
    {
        return STATION_CD;
    }

    public void setSTATION_CD (String STATION_CD)
    {
        this.STATION_CD = STATION_CD;
    }

    public String getSUBWAYNAME ()
    {
        return SUBWAYNAME;
    }

    public void setSUBWAYNAME (String SUBWAYNAME)
    {
        this.SUBWAYNAME = SUBWAYNAME;
    }

    public String getLEFTTIME ()
    {
        return LEFTTIME;
    }

    public void setLEFTTIME (String LEFTTIME)
    {
        this.LEFTTIME = LEFTTIME;
    }

    public String getTRAINCODE ()
    {
        return TRAINCODE;
    }

    public void setTRAINCODE (String TRAINCODE)
    {
        this.TRAINCODE = TRAINCODE;
    }

    public String getARRIVETIME ()
    {
        return ARRIVETIME;
    }

    public void setARRIVETIME (String ARRIVETIME)
    {
        this.ARRIVETIME = ARRIVETIME;
    }

    public String getFR_CODE ()
    {
        return FR_CODE;
    }

    public void setFR_CODE (String FR_CODE)
    {
        this.FR_CODE = FR_CODE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SUBWAYCODE = "+SUBWAYCODE+", DESTSTATION_CODE = "+DESTSTATION_CODE+", DESTSTATION_NAME = "+DESTSTATION_NAME+", STATION_CD = "+STATION_CD+", SUBWAYNAME = "+SUBWAYNAME+", LEFTTIME = "+LEFTTIME+", TRAINCODE = "+TRAINCODE+", ARRIVETIME = "+ARRIVETIME+", FR_CODE = "+FR_CODE+"]";
    }
}