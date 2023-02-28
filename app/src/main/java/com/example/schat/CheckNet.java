package com.example.schat;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

public class CheckNet {
    private static boolean isFirstLaunch = MainActivity.isFirstLaunch;

    public static void checkNetwork(Activity activity){

        NetworkRequest networkRequest=new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        ConnectivityManager.NetworkCallback networkCallback=new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                if (!isFirstLaunch) {
                    Snackbar snackbar=Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content),"online",Snackbar.LENGTH_SHORT);
                    View view=snackbar.getView();
                    view.setBackground(activity.getResources().getDrawable(R.drawable.bg_green));
                    snackbar.show();
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isFirstLaunch=false;
                Snackbar snackbar=Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content),"you r offline!",Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }

        };


        ConnectivityManager connectivityManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager = activity.getSystemService(ConnectivityManager.class);
        }else{
            connectivityManager= (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        connectivityManager.requestNetwork(networkRequest,networkCallback);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(connectivityManager.getActiveNetwork() == null){
                Snackbar snackbar=Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content),"you r offline!",Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        }else{
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                Snackbar snackbar=Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content),"you r offline!",Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }

        }


    }

}
