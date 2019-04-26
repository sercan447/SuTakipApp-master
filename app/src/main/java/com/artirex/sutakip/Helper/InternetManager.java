package com.artirex.sutakip.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetManager {

    private Context context;

    public InternetManager(Context context)
    {
        this.context = context;
    }


    //internet baglantısı kontrol ediliyor
    public boolean InternetIsConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }else {
            return false;
        }
    }

}
