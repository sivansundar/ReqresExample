package com.siv.reqresexample;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqresRepository {

    private static ReqresRepository reqresRepository;

    public static ReqresRepository getInstance() {
        if (reqresRepository == null) {
            reqresRepository = new ReqresRepository();
        }
        return reqresRepository;
    }

    private RetrofitInterface retrofitInterface;

    public ReqresRepository() {
        retrofitInterface = RetrofitService.createService(RetrofitInterface.class);

    }

    public MutableLiveData<ReqResResponse> getList(int page, int delay) {
        MutableLiveData<ReqResResponse> responseData = new MutableLiveData<>();
        MutableLiveData<Boolean> shimmerStatus = new MutableLiveData<>();
        retrofitInterface.getDataList(page, delay).enqueue(new Callback<ReqResResponse>() {
            @Override
            public void onResponse(Call<ReqResResponse> call, Response<ReqResResponse> response) {
                if (response.isSuccessful()) {
                    responseData.setValue(response.body());
                    Log.d("TAG", "onResponse: RESPONSE CODE : " + response.body());
                    shimmerStatus.setValue(false);
                }
                else {
                    shimmerStatus.setValue(true);
                    Log.d("TAG", "onResponse: RESPONSE : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ReqResResponse> call, Throwable t) {
                responseData.setValue(null);
            }
        });

        return responseData;
    }
}
