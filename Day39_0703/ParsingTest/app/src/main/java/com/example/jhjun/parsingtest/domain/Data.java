package com.example.jhjun.parsingtest.domain;

/**
 * Created by jhjun on 2017-07-06.
 */

public class Data
{
    private SearchArrivalTimeOfLine2SubwayByIDService SearchArrivalTimeOfLine2SubwayByIDService;

    public SearchArrivalTimeOfLine2SubwayByIDService getSearchArrivalTimeOfLine2SubwayByIDService ()
    {
        return SearchArrivalTimeOfLine2SubwayByIDService;
    }

    public void setSearchArrivalTimeOfLine2SubwayByIDService (SearchArrivalTimeOfLine2SubwayByIDService SearchArrivalTimeOfLine2SubwayByIDService)
    {
        this.SearchArrivalTimeOfLine2SubwayByIDService = SearchArrivalTimeOfLine2SubwayByIDService;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchArrivalTimeOfLine2SubwayByIDService = "+SearchArrivalTimeOfLine2SubwayByIDService+"]";
    }
}
