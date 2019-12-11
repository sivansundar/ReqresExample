package com.siv.reqresexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements InternetBroadcast.ConnectivityReceiverListener {

    private static final String TAG = "MainActivity";

    ArrayList<ReqresModel> arrayListData = new ArrayList<>();
    //Adapter needs to be created

    RecyclerView recyclerView;
    ReqresViewModel reqresViewModel;
    ReqResAdapter reqResAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        checkConnection();

        reqresViewModel = ViewModelProviders.of(this).get(ReqresViewModel.class);
        reqresViewModel.init();
        reqresViewModel.getReqResData().observe(this, reqresResponse ->{
            List<ReqresModel> listData = reqresResponse.getData();
            Log.d(TAG, "onCreate: responsedata : " + listData.get(3).getFirst_name());
            arrayListData.addAll(listData);
            reqResAdapter.notifyDataSetChanged();
            Log.d(TAG, "onCreate: VALUES : " + arrayListData);
        });

        //RecyclerAdapter needs to be srt up. Then move to pagination and Shimmers.

        setupRecyclerView();
    }

    private void setupRecyclerView() {

        if (reqResAdapter == null) {
            reqResAdapter = new ReqResAdapter(MainActivity.this, arrayListData);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(reqResAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        } else {
            reqResAdapter.notifyDataSetChanged();
        }

    }

    private void checkConnection() {
        boolean isConnected = InternetBroadcast.isConnected();
        logStatus(isConnected);
    }

    public void logStatus(boolean isConnected) {
        Log.d(TAG, "logStatus: Connection Status : " + isConnected);
        Toast.makeText(this, "CONNECTION STATUS : " + isConnected, Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        logStatus(isConnected);
    }

}
