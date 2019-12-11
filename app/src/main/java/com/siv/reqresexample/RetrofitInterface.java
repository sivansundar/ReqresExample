package com.siv.reqresexample;

import com.siv.reqresexample.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //users?page=1&delay=3/

    int page = 1;
    @GET("users?")
    Call<ReqResResponse> getDataList(
            @Query("page") int page, @Query("delay") int delay);
}
