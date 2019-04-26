package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artirex.sutakip.R;
import com.onesignal.OneSignal;

/**
 * A simple {@link Fragment} subclass.
 */
public class navbarAyarlarFragment extends Fragment {

    View view;

    public navbarAyarlarFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_navbar_ayarlar, container, false);

        return view;
    }

}
