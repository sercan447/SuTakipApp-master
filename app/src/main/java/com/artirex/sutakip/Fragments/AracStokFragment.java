package com.artirex.sutakip.Fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.AracAdapter;
import com.artirex.sutakip.Adapters.AracStokAdapter;
import com.artirex.sutakip.Adapters.CagriAdapter;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.Cagrilar;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AracStokFragment extends Fragment {

    View view;
    RecyclerView recyclerview_aracStok;
    LinearLayoutManager linearLayoutManager;
    TextView tv_veriBulunamadi;

    List<AracStok> aracStoks = new ArrayList<>();

    AracStokAdapter aracStokAdapter;
    ProgressDialog progressDialog;

   private EditText dpckr_basliangictarihi,dpckr_bitistarihi;
   private Button btn_stokgetir;


    int arac_id = -1;
    public AracStokFragment() {

    }

    private String secilenTarih = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_aracstok, container, false);

        ViewComponentInitialize();

        dpckr_basliangictarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int yil = c.get(Calendar.YEAR);
                int ay = c.get(Calendar.MONTH);
                int gun = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog;
                pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Log.e("SECILEN ILK TARIH",""+year+"-"+month+"-"+dayOfMonth);
                        secilenTarih = year+"-"+(month+1)+"-"+dayOfMonth+" 00:00:00";
                        dpckr_basliangictarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_bitistarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int yil = c.get(Calendar.YEAR);
                int ay = c.get(Calendar.MONTH);
                int gun = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog;
                pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Log.e("SECILEN SON TARIH",""+year+"-"+month+"-"+dayOfMonth);
                        secilenTarih = year+"-"+(month+1)+"-"+dayOfMonth+" 23:59:59";
                        dpckr_bitistarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_stokgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_basliangictarihi.getText().toString().trim()) && !TextUtils.isEmpty(dpckr_bitistarihi.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                   String bas_tarih =dpckr_basliangictarihi.getText().toString();
                    String bit_tarih = dpckr_bitistarihi.getText().toString();

                    getAracStokTarihListe(arac_id,bas_tarih,bit_tarih);
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_stokbilgiler),"Tarih Aralığı Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }


            }
        });
        return view;
    }
    private String TarihSec()
    {
        Calendar c = Calendar.getInstance();
        int yil = c.get(Calendar.YEAR);
        int ay = c.get(Calendar.MONTH);
        int gun = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog;
        pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Log.e("SECILEN TARIH",""+year+"-"+(month+1)+"-"+dayOfMonth);
                secilenTarih = year+"-"+month+"-"+dayOfMonth+" 00:00:00";
            }
        },yil,ay,gun);

        pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
        pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
        pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
        pickerDialog.show();

        return secilenTarih;
    }//FUNC

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void ViewComponentInitialize()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Arac Stok Listesi");
        progressDialog.setMessage("Getiriliyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_aracStok = view.findViewById(R.id.recyclerview_aracStok);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_aracStok.setLayoutManager(linearLayoutManager);

        dpckr_basliangictarihi = view.findViewById(R.id.dpckr_basliangictarihi);
        dpckr_bitistarihi = view.findViewById(R.id.dpckr_bitistarihi);
        btn_stokgetir = view.findViewById(R.id.btn_stokgetir);


        if (getArguments() != null)
        {
            Bundle bundle = getArguments();

            arac_id = bundle.getInt("aracId",-1);
            Log.e("arac Id ",""+arac_id);

        }

        getAracStokListe(arac_id);

        /*
        if(aracStoks.size() == 0)
        {
            tv_veriBulunamadi.setVisibility(View.VISIBLE);
        }else
        {
            tv_veriBulunamadi.setVisibility(View.INVISIBLE);
        }
        */
    }//func


    private void getAracStokListe( int arac_id)
    {
        Call<List<AracStok>> request = ManagerALL.getInstance().getAracStokListe(arac_id);
        request.enqueue(new Callback<List<AracStok>>() {
            @Override
            public void onResponse(Call<List<AracStok>> call, Response<List<AracStok>> response) {


                if(response.isSuccessful())
                {
                    Log.e("VERILER GELYIYOR",response.body().toString());
                    aracStoks = response.body();
                    aracStokAdapter = new AracStokAdapter(getContext(),aracStoks,getActivity());

                    if(aracStoks.size() == 0)
                    {
                        Log.e("LISTE BOS",""+aracStoks.size());
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        Log.e("LISTE DOLU",""+aracStoks.size());
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_aracStok.setAdapter(aracStokAdapter);
                    }

                    progressDialog.cancel();
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<AracStok>> call, Throwable t) {

            }
        });
    }//FUNC

    private void getAracStokTarihListe( int arac_id,String baslangic_tarihi,String bitis_tarihi)
    {
        Call<List<AracStok>> request = ManagerALL.getInstance().getAracStokTarihListe(arac_id,baslangic_tarihi,bitis_tarihi);
        request.enqueue(new Callback<List<AracStok>>() {
            @Override
            public void onResponse(Call<List<AracStok>> call, Response<List<AracStok>> response) {

                if(response.isSuccessful())
                {
                    Log.e("LOGUM",response.toString());
                    Log.e("LOGUM2",response.body().toString());
                    Log.e("VERILER TARIHLI GELYOR",response.body().toString());
                    aracStoks = response.body();
                    aracStokAdapter = new AracStokAdapter(getContext(),aracStoks,getActivity());

                    if(aracStoks.size() == 0)
                    {
                        Log.e("LISTE TARIH BOS",""+aracStoks.size());
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        Log.e("LISTE TARIH DOLU",""+aracStoks.size());
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_aracStok.setAdapter(aracStokAdapter);
                    }

                    progressDialog.cancel();
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<AracStok>> call, Throwable t) {

            }
        });
    }//FUNC

}
