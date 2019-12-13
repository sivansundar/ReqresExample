package com.siv.reqresexample;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InternetStatus extends Worker {

    public RecyclerView recyclerView;
    public ReqresViewModel reqresViewModel;
    public ReqResAdapter reqResAdapter;

    ShimmerFrameLayout mShimmerView;
    ArrayList<ReqresModel> arrayListData = new ArrayList<ReqresModel>();


    Context mContext;
    View view;

    public InternetStatus(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;


    }

    @NonNull
    @Override
    public Result doWork() {

        Data outputData = new Data.Builder().putAll((Map<String, Object>) getData()).build();


        return Result.success(outputData);
    }

    private ArrayList<ReqresModel> getData() {

        reqresViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(ReqresViewModel.class);
        reqresViewModel.init();


        reqresViewModel.getReqResData().observe((LifecycleOwner) mContext, reqresResponse -> {
            List<ReqresModel> listData = reqresResponse.getData();

            Log.d(TAG, "onCreate: responsedata : " + listData.get(3));
            arrayListData.addAll(listData);
            mShimmerView.stopShimmerAnimation();
            mShimmerView.setVisibility(View.GONE);
            reqResAdapter.notifyDataSetChanged();
            Log.d(TAG, "onCreate: VALUES : " + arrayListData);
        });
        //setupRecyclerView();
        return arrayListData;
    }

    private void setupRecyclerView() {

        if (reqResAdapter == null) {
            reqResAdapter = new ReqResAdapter(mContext, arrayListData);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(reqResAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


        } else {
            reqResAdapter.notifyDataSetChanged();
        }

    }

}
