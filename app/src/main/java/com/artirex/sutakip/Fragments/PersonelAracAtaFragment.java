package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.AracBaseAdapter;
import com.artirex.sutakip.Adapters.PersonelAdapter;
import com.artirex.sutakip.Adapters.PersonelBaseAdapter;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.Model.Result;
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
public class PersonelAracAtaFragment extends Fragment {

    View view;
    LinearLayout linearlayout_personelAracEkle;

    private AracBaseAdapter aracBaseAdapter;
    private List<AracGet> aracList = new ArrayList<>();
    private Spinner spinner_personelAracAta_Personel,spinner_personelAracAta_Arac;

    private Button btn_soforAta;

    private PersonelBaseAdapter adapter;
    private List<Personel> personelList = new ArrayList<>();

    private int arac_id;
    private int personel_id;

    AlertShow alertShow;
    InternetManager internetManager;



    public PersonelAracAtaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_personel_arac_ata, container, false);

        ViewComponentsInitialize();

        EventState();


        return view;
    }

    private void ViewComponentsInitialize()
    {
        linearlayout_personelAracEkle = view.findViewById(R.id.linearlayout_personelAracEkle);
        spinner_personelAracAta_Personel =   view.findViewById(R.id.spinner_personelAracAta_Personel);
        spinner_personelAracAta_Arac = view.findViewById(R.id.spinner_personelAracAta_Arac);
        btn_soforAta = view.findViewById(R.id.btn_soforAta);

        getCarList();
        gerPersonsList();

        internetManager = new InternetManager(getContext());
        alertShow = new AlertShow(getContext());

    }

    private void EventState()
    {
        btn_soforAta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internetManager.InternetIsConnected())
                {
                        personelAracAta(personel_id,arac_id,
                                SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));
                }else
                {
                    Snackbar.make(linearlayout_personelAracEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(),arac_id+"-"+personel_id,Toast.LENGTH_LONG).show();
            }
        });

        spinner_personelAracAta_Arac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AracGet arac = (AracGet)parent.getSelectedItem();
                arac_id = arac.getArac_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_personelAracAta_Personel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Personel personel = (Personel)parent.getSelectedItem();
                personel_id = personel.getPersonel_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCarList()
    {
        Call<List<AracGet>> request = ManagerALL.getInstance().getCarList();
        request.enqueue(new Callback<List<AracGet>>() {
            @Override
            public void onResponse(Call<List<AracGet>> call, Response<List<AracGet>> response) {

                if(response.isSuccessful())
                {
                    aracList = response.body();
                    aracBaseAdapter = new AracBaseAdapter(aracList,getContext());
                    spinner_personelAracAta_Arac.setAdapter(aracBaseAdapter);
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<AracGet>> call, Throwable t) {

            }
        });
    }//FUNC

    private void personelAracAta(int personel_id, int arac_id ,int ekleyenId)
    {
        Call<Result> req = ManagerALL.getInstance().personelAracAta(personel_id,arac_id,ekleyenId);
        req.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                alertShow.AlertDialogKutusu("Problem","Problem oluştu baştan deneyiniz.",R.drawable.ic_call);
                Log.e("ARACATA : ",t.getLocalizedMessage());
            }
        });
    }

    private void gerPersonsList() {
        Call<List<Personel>> musReq = ManagerALL.getInstance().getPersonsList();
        musReq.enqueue(new Callback<List<Personel>>() {
            @Override
            public void onResponse(Call<List<Personel>> call, Response<List<Personel>> response) {
                if (response.isSuccessful()) {
                    Log.e("PERSONEL LIST :", response.toString());
                    Log.e("PERSONEL list", response.body().toString());
                    personelList = response.body();
                    adapter = new PersonelBaseAdapter( personelList,getContext());
                    spinner_personelAracAta_Personel.setAdapter(adapter);

                } else {
                    Log.e("PERSONEL LIST else:", response.toString());
                    Log.e("PERSONEL list else", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<List<Personel>> call, Throwable t) {
                Log.e("PERSONEL onFailure :", t.getLocalizedMessage());

            }
        });
    }//FUNC

}
