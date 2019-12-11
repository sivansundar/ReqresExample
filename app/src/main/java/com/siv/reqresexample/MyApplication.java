package com.siv.reqresexample;

import android.app.Application;

import BroadcastReceivers.InternetBroadcast;

public class MyApplication extends Application {


    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(InternetBroadcast.ConnectivityReceiverListener listener) {
        InternetBroadcast.connectivityReceiverListener = listener;
    }
}