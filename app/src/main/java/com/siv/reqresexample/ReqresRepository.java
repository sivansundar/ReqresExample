package com.siv.reqresexample;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqresRepository extends PageKeyedDataSource {

    public static long FIRST_PAGE = 1;

    private static ReqresRepository reqresRepository;

    public static ReqresRepository getInstance() {
        if (reqresRepository == null) {
            reqresRepository = new ReqresRepository();
        }
        return reqresRepository;
    }

    private RetrofitInterface retrofitInterface;
    MutableLiveData<PageResponse> responseData;

    public ReqresRepository() {
        retrofitInterface = RetrofitService.createService(RetrofitInterface.class);

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {

        getList(FIRST_PAGE);
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        Call<PageResponse> call = retrofitInterface.getDataList((Long) params.key);
        call.enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
                if (response.isSuccessful()) {
                    responseData.setValue(response.body());


                }
            }

            @Override
            public void onFailure(Call<PageResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    public MutableLiveData<PageResponse> getList(long page) {
        responseData = new MutableLiveData<>();
        MutableLiveData<Boolean> shimmerStatus = new MutableLiveData<>();
        retrofitInterface.getDataList(FIRST_PAGE).enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
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
            public void onFailure(Call<PageResponse> call, Throwable t) {
                responseData.setValue(null);
            }
        });

        return responseData;
    }


}
