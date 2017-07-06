package com.example.jhjun.parsingtest.domain;

/**
 * Created by jhjun on 2017-07-06.
 */

public class RESULT
{
    private String MESSAGE;

    private String CODE;

    public String getMESSAGE ()
    {
        return MESSAGE;
    }

    public void setMESSAGE (String MESSAGE)
    {
        this.MESSAGE = MESSAGE;
    }

    public String getCODE ()
    {
        return CODE;
    }

    public void setCODE (String CODE)
    {
        this.CODE = CODE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [MESSAGE = "+MESSAGE+", CODE = "+CODE+"]";
    }
}