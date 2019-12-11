package com.siv.reqresexample;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements InternetBroadcast.ConnectivityReceiverListener {

    private static final String TAG = "MainActivity";

    ArrayList<ReqresModel> arrayListData = new ArrayList<>();
    //Adapter needs to be created

    RecyclerView recyclerView;
    ReqresViewModel reqresViewModel;
    ReqResAdapter reqResAdapter;

    ShimmerFrameLayout mShimmerView;


    TextView status;
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        status = findViewById(R.id.status);
        mShimmerView = findViewById(R.id.shimmer_view_container);

        checkConnection();

        if (isConnected) {

            reqresViewModel = ViewModelProviders.of(this).get(ReqresViewModel.class);
            reqresViewModel.init();


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    mShimmerView.stopShimmerAnimation();
                    mShimmerView.setVisibility(View.GONE);
                }
            }, 2000);

            reqresViewModel.getReqResData().observe(this, reqresResponse -> {
                List<ReqresModel> listData = reqresResponse.getData();

                Log.d(TAG, "onCreate: responsedata : " + listData.get(3).getFirst_name());
                arrayListData.addAll(listData);
                reqResAdapter.notifyDataSetChanged();
                Log.d(TAG, "onCreate: VALUES : " + arrayListData);
            });
            setupRecyclerView();
        }
        //RecyclerAdapter needs to be srt up. Then move to pagination and Shimmers.


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
        isConnected = InternetBroadcast.isConnected();
        logStatus(isConnected);
    }

    public void logStatus(boolean isConnected) {
        if (isConnected) {
            status.setVisibility(View.GONE);
        } else {
            status.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "logStatus: Connection Status : " + isConnected);
        Toast.makeText(this, "CONNECTION STATUS : " + isConnected, Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
        mShimmerView.startShimmerAnimation();
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

    @Override
    protected void onPause() {
        mShimmerView.stopShimmerAnimation();
        super.onPause();
    }
}
