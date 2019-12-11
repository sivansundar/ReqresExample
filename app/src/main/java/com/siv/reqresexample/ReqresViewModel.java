package com.siv.reqresexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReqresViewModel extends ViewModel {

    private MutableLiveData<ReqResResponse> mutableLiveData;
    private ReqresRepository reqresRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }

        reqresRepository = ReqresRepository.getInstance();
        mutableLiveData = reqresRepository.getList(1, 3);
    }


    public LiveData<ReqResResponse> getReqResData() {
        return mutableLiveData;
    }
}
