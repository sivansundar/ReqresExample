package com.siv.reqresexample;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <Service> Service createService(Class<Service> serviceClass) {
        return retrofit.create(serviceClass);

    }

}
