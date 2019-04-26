package com.artirex.sutakip.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.SiparisAdapter;
import com.artirex.sutakip.Adapters.SoforSiparisAdapter;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


public class AracaAitSiparisFragment extends Fragment {


    View view;
    RecyclerView recyclerview_aracaAitSiparis;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;

    SiparisAdapter siparisAdapter;
    List<SiparisGet> siparisGetList = new ArrayList<>();


    Spinner spinner_aracaaitSiparisDurum;
    EditText dpckr_basliangictarihi_siparis,dpckr_bitistarihi_siparis;
    Button btn_siparisgetir;

    TextView tv_veriBulunamadi;
    int aracId = -1;

    String secilenTarih = "";
    int selectedTypeId=-1;


    public AracaAitSiparisFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_araca_ait_siparis, container, false);

        ViewComponentInitialize();
        setHasOptionsMenu(true);

        spinner_aracaaitSiparisDurum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedPayType = (String)parent.getSelectedItem();

                switch (selectedPayType)
                {
                    case "İptal":
                        selectedTypeId = 4;
                        return;
                    case "Teslimatta":
                        selectedTypeId = 1;
                        return;
                    case "Sipariş":
                        selectedTypeId = 2;
                        return;
                    default:
                        selectedTypeId = -1;
                        return;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dpckr_basliangictarihi_siparis.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_basliangictarihi_siparis.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_bitistarihi_siparis.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_bitistarihi_siparis.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_siparisgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_basliangictarihi_siparis.getText().toString().trim()) && !TextUtils.isEmpty(dpckr_bitistarihi_siparis.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                    String bas_tarih =dpckr_basliangictarihi_siparis.getText().toString();
                    String bit_tarih = dpckr_bitistarihi_siparis.getText().toString();

                    siparisGetList.clear();
                   getSoforSiparis(aracId,bas_tarih,bit_tarih,selectedTypeId);
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_stokbilgiler),"Tarih Aralığı Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void ViewComponentInitialize()
    {

        spinner_aracaaitSiparisDurum = view.findViewById(R.id.spinner_aracaaitSiparisDurum);
        dpckr_basliangictarihi_siparis = view.findViewById(R.id.dpckr_basliangictarihi_siparis);
        dpckr_bitistarihi_siparis = view.findViewById(R.id.dpckr_bitistarihi_siparis);
        btn_siparisgetir = view.findViewById(R.id.btn_siparisgetir);


        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_aracaAitSiparis = view.findViewById(R.id.recyclerview_aracaAitSiparis);

        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getActivity(),3);

        recyclerview_aracaAitSiparis.setLayoutManager(linearLayoutManager);

        if(getArguments() != null)
        {
            Bundle bundle = getArguments();
            aracId = bundle.getInt("aracId",-1);
            getSoforSiparis(aracId,"","",0);

        }
    }

    private void getSoforSiparis(int siparis_arac_id,String baslangic_tarihi, String bitis_tarihi,int selectedDurumId)
    {
        Call<List<SiparisGet>> request = ManagerALL.getInstance().aracaAitSiparisler(siparis_arac_id,baslangic_tarihi, bitis_tarihi, selectedDurumId);
        request.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {

                if(response.isSuccessful())
                {
                    siparisGetList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(),siparisGetList,getActivity());
                    recyclerview_aracaAitSiparis.setAdapter(siparisAdapter);

                    Log.e("CEVAP ",response.body().toString());
                }else
                {
                }
            }
            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("hata siparis araca ait ",t.toString());
            }
        });
    }//FUNC
}
