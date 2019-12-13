package com.siv.reqresexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements InternetBroadcast.ConnectivityReceiverListener {

    private static final String TAG = "MainActivity";

    ArrayList<ReqresModel> arrayListData = new ArrayList<ReqresModel>();
    //Adapter needs to be created

    RecyclerView recyclerView;
    ReqresViewModel reqresViewModel;
    ReqResAdapter reqResAdapter;

    ShimmerFrameLayout mShimmerView;

    TextView status;
    boolean isConnected;

    Constraints constraints;
    OneTimeWorkRequest oneTimeWorkRequest;
    WorkManager mWorkManager;

    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        status = findViewById(R.id.status);
        mWorkManager = WorkManager.getInstance(this);
        mShimmerView = findViewById(R.id.shimmer_view_container);
        MyApplication.getInstance().setConnectivityListener(this);
        root = findViewById(R.id.root);
        checkConnection();

    }


    private void loadData() {
        if (isConnected) {

            reqresViewModel = ViewModelProviders.of(this).get(ReqresViewModel.class);
            reqresViewModel.init();


            reqresViewModel.getReqResData().observe(this, reqresResponse -> {
                List<ReqresModel> listData = reqresResponse.getData();

                Log.d(TAG, "onCreate: responsedata : " + listData.get(3));
                arrayListData.addAll(listData);
                mShimmerView.stopShimmerAnimation();
                mShimmerView.setVisibility(View.GONE);
                reqResAdapter.notifyDataSetChanged();
                Log.d(TAG, "onCreate: VALUES : " + arrayListData);
            });
            setupRecyclerView();
        } else if (!isConnected) {
            Log.d(TAG, "onCreate: NOT CONNECTED!  Cannot load content : " + isConnected);
        }

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
        if (isConnected) {

            loadData();
        }
        logStatus(isConnected);

    }

    public void logStatus(boolean isConnected) {
        if (isConnected) {
            status.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(root, "Connected to the internet", Snackbar.LENGTH_LONG);

            snackbar.show();


        } else {
            status.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar
                    .make(root, "No internet Connection", Snackbar.LENGTH_LONG);

            snackbar.show();
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
