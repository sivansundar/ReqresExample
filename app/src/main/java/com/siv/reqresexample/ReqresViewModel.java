package com.siv.reqresexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReqresViewModel extends ViewModel {

    private MutableLiveData<PageResponse> mutableLiveData;
    private ReqresRepository reqresRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }

        reqresRepository = ReqresRepository.getInstance();
        mutableLiveData = reqresRepository.getList(1);
    }


    public LiveData<PageResponse> getReqResData() {
        return mutableLiveData;
    }
}
