package com.siv.reqresexample;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {


    private static HttpLoggingInterceptor logger =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder().addInterceptor(logger);




    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build();

    public static <Service> Service createService(Class<Service> serviceClass) {
        return retrofit.create(serviceClass);

    }

}
