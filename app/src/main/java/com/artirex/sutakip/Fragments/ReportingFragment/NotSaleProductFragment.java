package com.artirex.sutakip.Fragments.ReportingFragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.MusteriAdapter;
import com.artirex.sutakip.Adapters.SiparisAdapter;
import com.artirex.sutakip.Fragments.MusteriEkleFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotSaleProductFragment extends Fragment {

    RecyclerView recyclerview_siparisVermeyenMusteri;
    FloatingActionButton fab_musteriEkle;
    View view;
    ChangeFragments changeFragments;

    MusteriEkleFragment musteriEkleFragment;
    List<Musteri> musteriList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    //SwipeRefreshLayout recyclerview_siparisVermeyenMusteri;

    MusteriAdapter adapter;
    TextView tv_veriBulunamadi;
    ProgressDialog progressDialog;

    EditText dpckr_basliangictarihi_musteri,dpckr_bitistarihi_musteri;
    Button btn_olmayanMusteri;


    String secilenTarih = "";
    int selectedTypeId=-1;


    public NotSaleProductFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_not_sale_product, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Dönem bazlı Sipariş Vermeyen Müşteriler");
            ViewComponentInitialize();
            ClickState();



        return view;
    }

    private void ViewComponentInitialize()
    {

        dpckr_basliangictarihi_musteri = view.findViewById(R.id.dpckr_basliangictarihi_musteri);
        dpckr_bitistarihi_musteri = view.findViewById(R.id.dpckr_bitistarihi_musteri);
        btn_olmayanMusteri = view.findViewById(R.id.btn_olmayanMusteri);


        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_siparisVermeyenMusteri = view.findViewById(R.id.recyclerview_siparisVermeyenMusteri);

        linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerview_siparisVermeyenMusteri.setLayoutManager(linearLayoutManager);


        getNotMusteriiProduct("","");

    }


    public void ClickState()
    {
        dpckr_basliangictarihi_musteri.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_basliangictarihi_musteri.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_bitistarihi_musteri.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_bitistarihi_musteri.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_olmayanMusteri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_basliangictarihi_musteri.getText().toString().trim()) && !TextUtils.isEmpty(dpckr_bitistarihi_musteri.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                    String bas_tarih =dpckr_basliangictarihi_musteri.getText().toString();
                    String bit_tarih = dpckr_bitistarihi_musteri.getText().toString();

                    musteriList.clear();
                    getNotMusteriiProduct(bas_tarih,bit_tarih);
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_siparisVermeyenMusteri),"Tarih Aralığı Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }//FUNC


    private void getNotMusteriiProduct(String baslangic_tarihi, String bitis_tarihi)
    {
        Call<List<Musteri>> musReq = ManagerALL.getInstance().getNotMusteriiProduct(baslangic_tarihi,bitis_tarihi);
        musReq.enqueue(new Callback<List<Musteri>>() {
            @Override
            public void onResponse(Call<List<Musteri>> call, Response<List<Musteri>> response) {
                if (response.isSuccessful())
                {
                    Log.e("MUSTERI LIST :",response.toString());
                    Log.e("Musteri list",response.body().toString());
                    musteriList = response.body();
                    adapter = new MusteriAdapter(recyclerview_siparisVermeyenMusteri,getContext(),musteriList,getActivity());

                    if(musteriList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_siparisVermeyenMusteri.setAdapter(adapter);
                    }
                   // progressDialog.cancel();


                }else{
                    Log.e("MUSTERI LIST else:",response.toString());
                    Log.e("Musteri list else",response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<List<Musteri>> call, Throwable t) {
                Log.e("MUSTERI onFailure :",t.getLocalizedMessage());

            }
        });
    }

}
