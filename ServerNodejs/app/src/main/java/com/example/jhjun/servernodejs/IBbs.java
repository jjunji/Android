package com.example.jhjun.servernodejs;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by jhjun on 2017-07-26.
 */

public interface IBbs {
    public static final String SERVER = "http://192.168.151.15/";

    @GET("bbs")
    public Observable<ResponseBody> read();

    @POST("bbs")
    public Observable<ResponseBody> write(@Body RequestBody body);

    @PUT("bbs")
    public Observable<ResponseBody> update(Bbs bbs);

    @DELETE("bbs")
    public Observable<ResponseBody> delete(Bbs bbs);
}
