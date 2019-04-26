package com.artirex.sutakip.Fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OdemeTuruFragment extends Fragment {



    private View view;
    private RecyclerView recyclerview_odemeturu;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<SiparisGet> siparisList = new ArrayList<>();
    private SiparisAdapter siparisAdapter;
    private TextView tv_veriBulunamadi;
    private SwipeRefreshLayout swipelayout_odemeturu;
    private ProgressDialog progressDialog;

    EditText dpckr_odemeturu_baslangictarihi,dpckr_odemeturu_bitistarihi;
    Button btn_odemeturu_stokgetir;
    Spinner spinner_odemeturu_search;

    String secilenTarih = "";
   int selectedTypeId = -1;

    public OdemeTuruFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_odeme_turu, container, false);
        setHasOptionsMenu(true);

        Initialize();
        ClickState();

        return view;
    }

    private void ClickState()
    {
        spinner_odemeturu_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPayType = (String)parent.getSelectedItem();

                 switch (selectedPayType)
                 {
                     case "Nakit":
                         selectedTypeId = 1;
                     return;
                     case "Kredi Kartı":
                         selectedTypeId = 2;
                         return;
                     case "E-Ticaret":
                         selectedTypeId = 3;
                         return;
                     case "Veresiye":
                         selectedTypeId = 4;
                         return;
                     case "Emanet":
                         selectedTypeId = 5;
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


        dpckr_odemeturu_baslangictarihi.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_odemeturu_baslangictarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_odemeturu_bitistarihi.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_odemeturu_bitistarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_odemeturu_stokgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_odemeturu_baslangictarihi.getText().toString().trim()) && !TextUtils.isEmpty(dpckr_odemeturu_bitistarihi.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                    String bas_tarih =dpckr_odemeturu_baslangictarihi.getText().toString();
                    String bit_tarih = dpckr_odemeturu_bitistarihi.getText().toString();

                    Log.e("TARIHLERR : ","-"+bas_tarih+ " -"+bit_tarih+" * "+selectedTypeId);

                    //-1 vermemin sebebi,tarih zaman ayarınnı kendimiz seçeceğimiz için
                    getOrdersListPayment(selectedTypeId,-1,bas_tarih,bit_tarih);


                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_odemeturu),"Tarih Aralığı Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    private void Initialize()
    {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
    {
           /* progressDialog = ProgressDialog.show(getContext(),"Emanet","Bekleyiniz..");
          //  progressDialog.setTitle("Emanet Kategorisi");
            //progressDialog.setMessage("Veriler Listeleniyor..");
            progressDialog.setCancelable(false);
*/

        spinner_odemeturu_search = view.findViewById(R.id.spinner_odemeturu_search);
        dpckr_odemeturu_baslangictarihi = view.findViewById(R.id.dpckr_odemeturu_baslangictarihi);
        dpckr_odemeturu_bitistarihi = view.findViewById(R.id.dpckr_odemeturu_bitistarihi);
        btn_odemeturu_stokgetir = view.findViewById(R.id.btn_odemeturu_stokgetir);


        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_odemeturu = view.findViewById(R.id.recyclerview_odemeturu);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerview_odemeturu.setLayoutManager(linearLayoutManager);
       // swipelayout_odemeturu = view.findViewById(R.id.refreshlayout_odemeturu);

    }else{

    }

       /* swipelayout_odemeturu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrdersListPayment(5,0,"","");
                swipelayout_odemeturu.setRefreshing(false);
            }
        });*/

    }//func

    //odeme_turu  = 5 ÇEŞİT ODEME türü (emanet,veresiye,nakit vs.) olan ödeme tipleri
    //tarih aralıgı ise DATALARI 1,2 HAFTALIK  YADA BIR AYLIK olarak gosterilmek üzere verilmiş parametre tipi
    private void getOrdersListPayment(int odeme_turu,int tarih_araligi,String baslangictarihi,String bitistarihi)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersListPayment(odeme_turu,tarih_araligi,baslangictarihi,bitistarihi);
        req.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {
                if(response.isSuccessful()) {
                    siparisList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(),siparisList,getActivity());
                    //Listede eleman yok ise verinin olmadıgını söylesin.

                    if(siparisList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {

                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_odemeturu.setAdapter(siparisAdapter);
                    }
                    Log.e("SiparisGet1 List :",response.body().toString());
                    Log.e("SiparisGet1 List 2 :",response.toString());
                }else
                {
                    Log.e("SiparisGet1 List else :",response.toString());
                }

                // progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis1 hhata :",t.getLocalizedMessage());

                //  progressDialog.dismiss();
            }
        });


    }//Func

}
