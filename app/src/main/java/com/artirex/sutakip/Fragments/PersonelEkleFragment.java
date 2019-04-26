package com.artirex.sutakip.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.YetkiBaseAdapter;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.KullaniciYetki;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonelEkleFragment extends Fragment {

    private View view;
    private LinearLayout linearlayout_personelEkle;
    private TextInputLayout til_adi,til_soyadi,til_telefon1,til_bilgi,til_parola;
    private TextInputEditText ed_adi,ed_soyadi,ed_telefon1,ed_bilgi,ed_parola;
    private RadioGroup radioGroup;
    private RadioButton rdb_erkek,rdb_kadin;
    private Spinner spinnerYetki;
    private Button btnPersonelEkle;
    private ChangeFragments changeFragments;
    private InternetManager internetManager;
    private AlertShow alertShow;

    private int personelId = -1;

    List<KullaniciYetki> listYetki = new ArrayList<>();

    public PersonelEkleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_personel_ekle, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Personel Ekle Modülü");

         InitComponents();
            ButtonInsert();

            if(getArguments() != null)
            {
                Bundle bundle = getArguments();
                personelId = bundle.getInt("personelId",-1);
                getPersonel(personelId);
            }

        ed_parola.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 6)
                    ed_parola.setError("Parola Çok uzun");
                else
                    ed_parola.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


    private void InitComponents()
    {
        linearlayout_personelEkle = view.findViewById(R.id.linearlayout_personelEkle);

        til_adi = view.findViewById(R.id.til_personelAdi);
        til_soyadi = view.findViewById(R.id.til_personelSoyadi);
        til_telefon1 = view.findViewById(R.id.til_personelTelefon1);
        til_bilgi = view.findViewById(R.id.til_personelBilgi);
        til_parola = view.findViewById(R.id.til_personelParola);

        ed_adi = view.findViewById(R.id.ed_personelAdi);
        ed_soyadi = view.findViewById(R.id.ed_personelSoyadi);
        ed_telefon1 = view.findViewById(R.id.ed_personelTelefon1);
        ed_bilgi = view.findViewById(R.id.ed_personelBilgi);

        ed_parola = view.findViewById(R.id.ed_personelParola);

        spinnerYetki = view.findViewById(R.id.spinner_personelYetki);
        radioGroup = view.findViewById(R.id.rdgrp_personel);
        rdb_erkek = view.findViewById(R.id.rdb_personelErkek);
        rdb_kadin = view.findViewById(R.id.rdb_personelKadin);
        btnPersonelEkle = view.findViewById(R.id.btn_personelKayit);

        changeFragments = new ChangeFragments(getContext());
        internetManager = new InternetManager(getContext());
        alertShow = new AlertShow(getContext());

        if(personelId != -1)
        {
            ed_parola.setVisibility(View.GONE);
        }
        getYetkiBilgi();
    }

    private void getYetkiBilgi()
    {
        String[] yetki = getResources().getStringArray(R.array.kullanici_tipi);
        for(int i=0; i<yetki.length;i++)
        {
            listYetki.add(new KullaniciYetki(i,yetki[i]));
        }
        YetkiBaseAdapter yetkiBaseAdapter = new YetkiBaseAdapter(listYetki,getContext());
        spinnerYetki.setAdapter(yetkiBaseAdapter);

    }

    public void KullaniciKayit(String adi, String soyadi, String telefon, String bilgi, int yetki,
                               int durum, int cinsiyet, String parola, int ekleyenId)
    {
        Call<Result> result = ManagerALL.getInstance().userInsert( adi,  soyadi,  telefon, bilgi,  yetki,  durum, cinsiyet,  parola,  ekleyenId);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("KULLANICI KAYIT SUCCES:",response.toString());
                    Log.e("KULLANICI KAYIT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("KULLANICI KAYIT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.e("KULLANICI KAYIT HATA :",t.getLocalizedMessage());
            }
        });
    }//FUNC

    public void KullaniciGuncelle(int personel_id,String adi, String soyadi, String telefon, String bilgi, int yetki,
                               int durum, int cinsiyet, String parola, int ekleyenId)
    {
        Call<Result> result = ManagerALL.getInstance().userUpdate( personel_id,adi,  soyadi,  telefon, bilgi,  yetki,  durum, cinsiyet,  parola,  ekleyenId);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("KULLANICI UPDT SUCCES:",response.toString());
                    Log.e("KULLANICI UPDT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("KULLANICI UPDT NOSUCC:",response.toString());
                    Log.e("KULLANICI UPDT NOSUCC:",response.body().toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.e("KULLANICI UPDT HATA :",t.getLocalizedMessage());
            }
        });
    }//FUNC

    private void ButtonInsert() {
            btnPersonelEkle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(internetManager.InternetIsConnected())
                    {
                        PersonelViewValidate();
                    }else
                    {
                        Snackbar.make(linearlayout_personelEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                    }
                }
            });
    }//FUNC

    private void PersonelViewValidate()
    {
        ed_adi.setError(null);
        ed_soyadi.setError(null);
     //    ed_bilgi.setError(null);
        ed_telefon1.setError(null);
       // ed_parola.setError(null);
        //til_parola.setVisibility(View.GONE);

        boolean durumAdi = TextUtils.isEmpty(ed_adi.getText());
        boolean durumSoyadi = TextUtils.isEmpty(ed_soyadi.getText());
        boolean durumBilgi = TextUtils.isEmpty(ed_bilgi.getText());
        boolean durumTelefon1 = TextUtils.isEmpty(ed_telefon1.getText());
        boolean durumParola = TextUtils.isEmpty(ed_parola.getText());

        if(durumAdi || durumSoyadi || durumBilgi || durumTelefon1 )
        {
            if(durumAdi)
                ed_adi.setError("Adı alanı boş.");

            if(durumSoyadi)
                ed_soyadi.setError("Soyadı alanı boş.");

            //if(durumBilgi)
              //  ed_parola.setError("Parola alanı boş.");

            if(durumTelefon1)
                ed_telefon1.setError("Telefon Alanı boş.");

            /*if(durumParola) {
                ed_parola.setError("Parola alanı boş.");
            }
            */
        }else
        {
            Personel personel = new Personel();
            personel.setAdi(ed_adi.getText().toString().trim());
            personel.setSoyadi(ed_soyadi.getText().toString().trim());
            personel.setTelefon(ed_telefon1.getText().toString().trim());
            personel.setBilgi(ed_bilgi.getText().toString());
            personel.setYetki(spinnerYetki.getSelectedItemPosition());
            personel.setParola(ed_parola.getText().toString().trim());
            personel.setDurum(1);
            switch (radioGroup.getCheckedRadioButtonId())
            {
                case R.id.rdb_personelErkek:
                    personel.setCinsiyet(0);
                    break;
                case R.id.rdb_personelKadin:
                    personel.setCinsiyet(1);
                    break;
            }

           // personel.setParola(ed_parola.getText().toString().trim());
            personel.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId", -1));

            if(personelId != -1)
            {
                Toast.makeText(getContext(),"GUNCELLEME",Toast.LENGTH_SHORT).show();
                KullaniciGuncelle(personelId,personel.getAdi(), personel.getSoyadi(), personel.getTelefon(), personel.getBilgi(), personel.getYetki(),
                        1, personel.getCinsiyet(), personel.getParola(), personel.getEkleyenId());
            }else {
                Toast.makeText(getContext(),"kayıt",Toast.LENGTH_SHORT).show();
                KullaniciKayit(personel.getAdi(), personel.getSoyadi(), personel.getTelefon(), personel.getBilgi(), personel.getYetki(),
                        1, personel.getCinsiyet(), personel.getParola(), personel.getEkleyenId());
            }
        }
    }//View

    private void getPersonel(final int personel_id)
    {
        Call<Personel> req = ManagerALL.getInstance().getUser(personel_id);
        req.enqueue(new Callback<Personel>() {
            @Override
            public void onResponse(Call<Personel> call, Response<Personel> response) {
                if(response.isSuccessful())
                {
                    Personel personel = response.body();

                    ed_adi.setText(personel.getAdi());
                    ed_soyadi.setText(personel.getSoyadi());
                    ed_telefon1.setText(personel.getTelefon());
                    ed_bilgi.setText(personel.getBilgi());

                    if(personel.getCinsiyet() == 1)
                    {
                        //kadın
                        rdb_erkek.setChecked(true);
                        rdb_kadin.setChecked(false);
                    }else
                    {
                        //erkek
                        rdb_kadin.setChecked(true);
                        rdb_erkek.setChecked(false);
                    }

                    int selectIndis = -1;
                    for (int i=0;i<listYetki.size();i++)
                    {
                        /// Log.e("t",urunList.get(i).getUrun_tipi().toUpperCase() +"--"+ aracGet.getArac_model().toUpperCase());
                        if(listYetki.get(i).getYetki_id() == personel.getYetki() )
                        {
                            selectIndis = i;
                            Log.e("BOYUT","İF");
                        }else{
                            Log.e("BOYUT else","else");
                        }
                        Log.e("BOYUT ",""+selectIndis);
                    }
                    spinnerYetki.setSelection(selectIndis);

                }
                Log.e("PERSONELEKLE",""+response.toString());
                Log.e("PERSONELEKLE",""+response.body().toString());
            }

            @Override
            public void onFailure(Call<Personel> call, Throwable t) {

            }
        });
    }

}
