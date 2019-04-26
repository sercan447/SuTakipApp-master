package com.artirex.sutakip.Fragments.AnasayfaTabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.SiparisAdapter;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabIkiFragment extends Fragment {



    View view;
    RecyclerView recyclerview_iptalSiparisler;
    LinearLayoutManager linearLayoutManager;

    List<SiparisGet> siparisList = new ArrayList<>();
    SiparisAdapter siparisAdapter;
    TextView tv_veriBulunamadi;

    public TabIkiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_iki, container, false);

        ViewComponentInitialize();
        setHasOptionsMenu(true);

        return view;
    }

    private void ViewComponentInitialize()
    {
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_iptalSiparisler = view.findViewById(R.id.recyclerview_iptalSiparisler);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_iptalSiparisler.setLayoutManager(linearLayoutManager);

        getOrdersList("4");


    }

    private void getOrdersList(String durumcode)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersList(durumcode);
        req.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {

                if(response.isSuccessful()) {

                    siparisList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(),siparisList,getActivity());

                    if(siparisList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_iptalSiparisler.setAdapter(siparisAdapter);
                    }


                    Log.e("SiparisGet 0 List :",response.body().toString());
                    Log.e("SiparisGet 0  List 2 :",response.toString());
                }else
                {
                    Log.e("SiparisGet0 List else :",response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis 0  hhata :",t.getLocalizedMessage());
            }
        });
    }//Func
}
