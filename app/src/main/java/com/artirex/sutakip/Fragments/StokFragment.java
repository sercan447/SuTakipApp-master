package com.artirex.sutakip.Fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.AracBaseAdapter;
import com.artirex.sutakip.Adapters.AracStokAdapter;
import com.artirex.sutakip.Adapters.AracUrunStokAdapter;
import com.artirex.sutakip.Adapters.UrunBoyutBaseAdapter;
import com.artirex.sutakip.Adapters.UrunTipiBaseAdapter;
import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StokFragment extends Fragment {


    View view;
    RecyclerView recyclerview_Stok;
    LinearLayoutManager linearLayoutManager;
    TextView tv_veriBulunamadi;
    List<StokBilgiShowList> aracUrunStoks = new ArrayList<>();
    AracUrunStokAdapter aracUrunStokAdapter;
    UrunTipiBaseAdapter urunTipiBaseAdapter;
    UrunBoyutBaseAdapter urunBoyutBaseAdapter;
    ProgressDialog progressDialog;

    Button btn_toplamstokgetir;
    private Spinner spinner_aracplakafiltre,spinner_urunfiltre,spinner_alturunfiltre;

    private AracBaseAdapter aracBaseAdapter;
    private   List<AracGet> aracList = new ArrayList<>();
    List<Urun> urunList = new ArrayList<>();
    List<AltUrun> altUrunList = new ArrayList<AltUrun>();

    int aracId = 0;
    int urunId = 0;
    int altUrunid = 0;

    public StokFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stok, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Araç Toplam Stok Modülü");
                ViewComponentInitialize();

        return view;
    }

    private void ViewComponentInitialize()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Arac Stok Listesi");
        progressDialog.setMessage("Getiriliyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_Stok = view.findViewById(R.id.recyclerview_Stok);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_Stok.setLayoutManager(linearLayoutManager);

        btn_toplamstokgetir = view.findViewById(R.id.btn_toplamstokgetir);
        spinner_aracplakafiltre = view.findViewById(R.id.spinner_aracplakafiltre);
        spinner_urunfiltre = view.findViewById(R.id.spinner_urunfiltre);
        spinner_alturunfiltre = view.findViewById(R.id.spinner_alturunfiltre);

        getAracStokToplam(0,0,0);

        getCarList();
        getProductList();

        spinner_aracplakafiltre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AracGet tiklananArac = (AracGet) parent.getSelectedItem();
                aracId = tiklananArac.getArac_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_urunfiltre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Urun tiklanaUrun = (Urun) parent.getSelectedItem();
                urunId = tiklanaUrun.getUrun_id();

              //  spinner_alturunfiltre.setVisibility(View.VISIBLE);
                getSubProductList(urunId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_alturunfiltre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AltUrun tiklananBoyut = (AltUrun)parent.getSelectedItem();
                altUrunid = tiklananBoyut.getAlturun_id();
               // altUrunAdi = tiklananBoyut.getAlturun_adi();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_toplamstokgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ISTENILEN ARAC","--"+aracId);

                if(aracId != 0)
                {
                    getAracStokToplam(aracId,urunId,altUrunid);

                    if(aracUrunStoks.size() != 0) {
                        aracUrunStokAdapter = new AracUrunStokAdapter(getContext(), aracUrunStoks, getActivity());
                        aracUrunStokAdapter.notifyDataSetChanged();
                        recyclerview_Stok.setAdapter(aracUrunStokAdapter);
                    }
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_toplamStok),"Filtre için Araç Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }
            }
        });//onclick

    }//func



    private void getAracStokToplam( int arac_id,int urun_id,int alturun_id)
    {
        Call<List<StokBilgiShowList>> request = ManagerALL.getInstance().getAracStokToplam(arac_id,urun_id,alturun_id);
        request.enqueue(new Callback<List<StokBilgiShowList>>() {
            @Override
            public void onResponse(Call<List<StokBilgiShowList>> call, Response<List<StokBilgiShowList>> response) {

                if(response.isSuccessful())
                {
                    Log.e("VERILER GELIYOR",response.body().toString());
                    aracUrunStoks = response.body();
                    aracUrunStokAdapter = new AracUrunStokAdapter(getContext(),aracUrunStoks,getActivity());

                    if(aracUrunStoks.size() == 0)
                    {
                        Log.e("LISTE BOS",""+aracUrunStoks.size());
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        Log.e("LISTE DOLU",""+aracUrunStoks.size());
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_Stok.setAdapter(aracUrunStokAdapter);
                    }

                    progressDialog.cancel();
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<StokBilgiShowList>> call, Throwable t) {
                Log.e("STOK HATA ",""+t.getMessage().toString());
            }
        });
    }//FUNC


    private void getCarList()
    {

        Call<List<AracGet>> request = ManagerALL.getInstance().getCarList();
        request.enqueue(new Callback<List<AracGet>>() {
            @Override
            public void onResponse(Call<List<AracGet>> call, Response<List<AracGet>> response) {

                if(response.isSuccessful())
                {
                    aracList = response.body();
                    aracList.add(0,new AracGet(0,"Araç Seçiniz.","b","c","d",1,false));


                    aracBaseAdapter = new AracBaseAdapter(aracList,getContext());
                    spinner_aracplakafiltre.setAdapter(aracBaseAdapter);
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<AracGet>> call, Throwable t) {

            }
        });
    }//FUNC

    private void getProductList()
    {
        Call<List<Urun>> request = ManagerALL.getInstance().getProductList();
        request.enqueue(new Callback<List<Urun>>() {
            @Override
            public void onResponse(Call<List<Urun>> call, Response<List<Urun>> response) {

                if(response.isSuccessful())
                {
                    urunList = response.body();
                    urunList.add(0,new Urun(0,0,"Ürün Seçiniz.","","",0,"",0,"",0));

                    urunTipiBaseAdapter = new UrunTipiBaseAdapter(urunList,getContext());
                    spinner_urunfiltre.setAdapter(urunTipiBaseAdapter);
                }else
                {
                }
            }
            @Override
            public void onFailure(Call<List<Urun>> call, Throwable t) {

            }
        });
    }//FUNC

    private void getSubProductList(int urun_id)
    {
        Call<List<AltUrun>> request = ManagerALL.getInstance().getSubProductList(urun_id);
        request.enqueue(new Callback<List<AltUrun>>() {
            @Override
            public void onResponse(Call<List<AltUrun>> call, Response<List<AltUrun>> response) {

                if(response.isSuccessful())
                {
                    altUrunList = response.body();
                    altUrunList.add(0,new AltUrun(0,"Alt ürün Seçiniz.",0));

                    urunBoyutBaseAdapter = new UrunBoyutBaseAdapter(altUrunList,getContext());

                    spinner_alturunfiltre.setAdapter(urunBoyutBaseAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<AltUrun>> call, Throwable t) {
            }
        });
    }//func

}
