package com.example.jhjun.httpdataconnection.domain;

/**
 * Created by jhjun on 2017-06-13.
 */

public class Row
{
    private String INSERTDATE;

    private String CNAME;

    private String POI_ID;

    private String FNAME;

    private String UPDATEDATE;

    private String CENTER_Y1;

    private String CENTER_X1;

    private String Y_WGS84;

    private String X_WGS84;

    private String ANAME;

    public String getINSERTDATE ()
    {
        return INSERTDATE;
    }

    public void setINSERTDATE (String INSERTDATE)
    {
        this.INSERTDATE = INSERTDATE;
    }

    public String getCNAME ()
    {
        return CNAME;
    }

    public void setCNAME (String CNAME)
    {
        this.CNAME = CNAME;
    }

    public String getPOI_ID ()
    {
        return POI_ID;
    }

    public void setPOI_ID (String POI_ID)
    {
        this.POI_ID = POI_ID;
    }

    public String getFNAME ()
    {
        return FNAME;
    }

    public void setFNAME (String FNAME)
    {
        this.FNAME = FNAME;
    }

    public String getUPDATEDATE ()
    {
        return UPDATEDATE;
    }

    public void setUPDATEDATE (String UPDATEDATE)
    {
        this.UPDATEDATE = UPDATEDATE;
    }

    public String getCENTER_Y1 ()
    {
        return CENTER_Y1;
    }

    public void setCENTER_Y1 (String CENTER_Y1)
    {
        this.CENTER_Y1 = CENTER_Y1;
    }

    public String getCENTER_X1 ()
    {
        return CENTER_X1;
    }

    public void setCENTER_X1 (String CENTER_X1)
    {
        this.CENTER_X1 = CENTER_X1;
    }

    public String getY_WGS84 ()
    {
        return Y_WGS84;
    }

    public void setY_WGS84 (String Y_WGS84)
    {
        this.Y_WGS84 = Y_WGS84;
    }

    public String getX_WGS84 ()
    {
        return X_WGS84;
    }

    public void setX_WGS84 (String X_WGS84)
    {
        this.X_WGS84 = X_WGS84;
    }

    public String getANAME ()
    {
        return ANAME;
    }

    public void setANAME (String ANAME)
    {
        this.ANAME = ANAME;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [INSERTDATE = "+INSERTDATE+", CNAME = "+CNAME+", POI_ID = "+POI_ID+", FNAME = "+FNAME+", UPDATEDATE = "+UPDATEDATE+", CENTER_Y1 = "+CENTER_Y1+", CENTER_X1 = "+CENTER_X1+", Y_WGS84 = "+Y_WGS84+", X_WGS84 = "+X_WGS84+", ANAME = "+ANAME+"]";
    }
}
