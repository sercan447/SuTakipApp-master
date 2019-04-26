package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.AracStokAdapter;
import com.artirex.sutakip.Adapters.AracStokGunlukAdapter;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AracStokGunlukFragment extends Fragment {

    View view;
    RecyclerView recyclerview_aracStokGunluk;
    LinearLayoutManager linearLayoutManager;
    TextView tv_veriBulunamadi;

    private List<StokBilgiShowList> aracStoks = new ArrayList<>();

    AracStokGunlukAdapter aracStokGunlukAdapter;
    ProgressDialog progressDialog;

    int stokId = -1;

    public AracStokGunlukFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_aracstokgunluk, container, false);

        ViewComponentInitialize();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void ViewComponentInitialize()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Günlük stok Bilgileriniz.");
        progressDialog.setMessage("Getiriliyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_aracStokGunluk = view.findViewById(R.id.recyclerview_aracStokGunluk);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_aracStokGunluk.setLayoutManager(linearLayoutManager);

        if (getArguments() != null)
        {
            Bundle bundle = getArguments();

            stokId = bundle.getInt("stokId",-1);
            Log.e("stokId Id ",""+stokId);

        }

        getAracStokGunlukListe(stokId);

    }//func


    private void getAracStokGunlukListe( int stok_id)
    {
        Call<List<StokBilgiShowList>> request = ManagerALL.getInstance().getAracStokGunlukListe(stok_id);
        request.enqueue(new Callback<List<StokBilgiShowList>>() {
            @Override
            public void onResponse(Call<List<StokBilgiShowList>> call, Response<List<StokBilgiShowList>> response) {

                if(response.isSuccessful())
                {
                    Log.e("VERILER GELYIYOR",response.body().toString());
                    aracStoks = response.body();
                    aracStokGunlukAdapter = new AracStokGunlukAdapter(getContext(),aracStoks,getActivity());

                    if(aracStoks.size() == 0)
                    {

                        Log.e("LISTE BOS",""+aracStoks.size());
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        Log.e("LISTE DOLU",""+aracStoks.size());
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_aracStokGunluk.setAdapter(aracStokGunlukAdapter);
                    }

                    progressDialog.dismiss();
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<StokBilgiShowList>> call, Throwable t) {

            }
        });
    }//FUNC

}
