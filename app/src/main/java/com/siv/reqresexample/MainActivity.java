package com.siv.reqresexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import BroadcastReceivers.InternetBroadcast;

public class MainActivity extends AppCompatActivity implements InternetBroadcast.ConnectivityReceiverListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection();
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
