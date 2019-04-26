package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.AracAdapter;
import com.artirex.sutakip.Adapters.SoforSiparisAdapter;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavbarIcerikFragment extends Fragment {


    View view;

    RecyclerView recyclerview_anasayfaSiparisler;
    LinearLayoutManager linearLayoutManager;
    SoforSiparisAdapter soforSiparisAdapter;
    List<SiparisGet> siparisGetList = new ArrayList<>();

    SwipeRefreshLayout refreshlayout;

    private int aracId = -1;
    private int SiparisDurum = -1;

    public NavbarIcerikFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_navbar_icerik, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Siparişler");

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            SiparisDurum = bundle.getInt("durum", 1);
            Toast.makeText(getContext(), ""+SiparisDurum, Toast.LENGTH_SHORT).show();
        }
        ViewComponentsInitialize();
        setHasOptionsMenu(true);

        return view;
    }

    public void ViewComponentsInitialize()
    {
        refreshlayout = view.findViewById(R.id.refreshlayout);

        recyclerview_anasayfaSiparisler = view.findViewById(R.id.recyclerview_anasayfaSiparisler);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_anasayfaSiparisler.setLayoutManager(linearLayoutManager);
        aracId = SharedPreferenceManager.getSharedPrefere(getContext()).getInt("aracId",-1);

        if(aracId != -1)
        {
            getSoforSiparis(aracId,SiparisDurum);
            Log.e("sofor Id :",""+aracId);
            refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //siparisGetList.clear();
                    getSoforSiparis(aracId,SiparisDurum);
                    refreshlayout.setRefreshing(false);
                }
            });

        }
    }

    private void getSoforSiparis(int aracId,int siparis_durum)
    {
        Call<List<SiparisGet>> request = ManagerALL.getInstance().getSoforSiparis(aracId,siparis_durum);
        request.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {

                if(response.isSuccessful())
                {
                    siparisGetList = response.body();
                    soforSiparisAdapter = new SoforSiparisAdapter(getContext(),siparisGetList,getActivity());
                    recyclerview_anasayfaSiparisler.setAdapter(soforSiparisAdapter);

                    Log.e("CEVAP ",response.body().toString());
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("hata  ",t.toString());
            }
        });
    }//FUNC

    private void SiparisAyrinti(int siparisId)
    {

    }


}
