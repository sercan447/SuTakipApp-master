package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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


public class MusteriyeAitSiparisFragment extends Fragment {


    int musteriId=-1;
    View view;
    RecyclerView recyclerView_musteriyeAitSiparisler;
    LinearLayoutManager linearLayoutManager;

    List<SiparisGet> siparisList = new ArrayList<>();
    SiparisAdapter siparisAdapter;

    TextView tv_veriBulunamadi;

    public MusteriyeAitSiparisFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_musteriye_ait_siparis, container, false);

        ViewComponentInitialize();

        if(getArguments() != null)
        {
            Bundle bundle = getArguments();
            musteriId = bundle.getInt("musteriId",-1);

            Log.e("MUSTERI FRAGMEN",""+musteriId);
            getMusteriyeAitSiparisler(musteriId);
        }
        return view;
    }

    private void ViewComponentInitialize()
    {
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerView_musteriyeAitSiparisler = view.findViewById(R.id.recyclerView_musteriyeAitSiparisler);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_musteriyeAitSiparisler.setLayoutManager(linearLayoutManager);




    }
    private void getMusteriyeAitSiparisler(int musteri_id) {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getMusteriyeAitSiparisler(musteri_id);
        req.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {

                if (response.isSuccessful()) {

                    siparisList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(), siparisList, getActivity());

                    if (siparisList.size() == 0) {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    } else {
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerView_musteriyeAitSiparisler.setAdapter(siparisAdapter);
                    }


                    Log.e("SiparisGet1 List :", response.body().toString());
                    Log.e("SiparisGet1 List 2 :", response.toString());
                } else {
                    Log.e("SiparisGet1 List else :", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis1 hhata :", t.getLocalizedMessage());
            }
        });
    }

}
