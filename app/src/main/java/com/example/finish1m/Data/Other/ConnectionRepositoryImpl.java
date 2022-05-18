package com.example.finish1m.Data.Other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.finish1m.Domain.Interfaces.ConnectionRepository;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private Context context;

    public ConnectionRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
