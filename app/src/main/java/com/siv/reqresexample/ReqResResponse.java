package com.siv.reqresexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReqResResponse {

    @SerializedName("data")
    @Expose
    private List<ReqresModel> data = null;

    public List<ReqresModel> getData() {
        return data;
    }

    public void setData(List<ReqresModel> data) {
        this.data = data;
    }
}
