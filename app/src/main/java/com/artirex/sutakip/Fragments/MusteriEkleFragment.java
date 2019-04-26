package com.artirex.sutakip.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.artirex.sutakip.Adapters.AracModelBaseAdapter;
import com.artirex.sutakip.Adapters.FirmaBaseAdapter;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracModel;
import com.artirex.sutakip.Model.Firma;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


public class MusteriEkleFragment extends Fragment {


    View view;
    LinearLayout linearlayout_musteriEkle;
    TextInputLayout til_musteriAdi,til_musteriSoyadi,til_musteriTel1,til_musteriTel2,
            til_musteriTel3,til_musteriAdres1,til_musteriAdres2,til_musteriBilgi;
    TextInputEditText ed_musteriAdi,ed_musteriSoyadi,ed_musteriTel1 ,ed_musteriTel2,
                    ed_musteriTel3,ed_musteriAdres1,ed_musteriAdres2,ed_musteriBilgi;

    Button btn_musteriEkle;
    RadioGroup grp_cinsiyet;
    RadioButton rdb_erkek,rdb_kadin;

    ChangeFragments changeFragments;
    InternetManager internetManager;
    AlertShow alertShow;

    List<Firma> firmaList = new ArrayList<>();
    FirmaBaseAdapter firmaBaseAdapter;

    private int firmaId=0;
    private int musteriId = -1;
    private String musteriNumarasi="";
    private Bundle bundle;

    Musteri musteri = new Musteri();
    RequestQueue requestQueue;
    String late1 = "LAT";
    String lng1 = "LNG";


    public MusteriEkleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_musteri_ekle, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Müşteri Ekle Modülü");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewInitialize();

        if(getArguments() != null) {

            //müşteri güncellemesi için gerekli datalar

            Log.e("MUSTERI GUNCELLEMESI","");
            bundle = getArguments();
            musteriId = bundle.getInt("musteriId", -1);



            //olmayan müşteriyi cagrılar bölümünden bu fragmente yönlendirip eklemesini yaptırmak istiyoruz.
            musteriNumarasi = bundle.getString("telefonNumarasi","");
            if(!musteriNumarasi.equals(""))
            {
                ed_musteriTel1.setText(musteriNumarasi);

                Log.e("MUSTERIYI GELEN CAGRIYA","GORE YONLENDİRME ALANINDASINIZ "+musteriNumarasi);
            }

        }else
        {
            Log.e("MUSTERIYI GELEN CAGRIYA","ELSE ");
        }


        //Musteri bilgileri cekiliyor.
        if(musteriId != -1)
        {
            getCustomer(musteriId);
        }
        EventState();

    }

    private void ViewInitialize()
    {

        requestQueue = Volley.newRequestQueue(getContext());

        linearlayout_musteriEkle = view.findViewById(R.id.linearlayout_musteriEkle);

        til_musteriAdi = view.findViewById(R.id.til_musteriAdi);
        til_musteriSoyadi=view.findViewById(R.id.til_musteriSoyadi);
        til_musteriTel1=view.findViewById(R.id.til_musteriTel1);
        til_musteriTel2=view.findViewById(R.id.til_musteriTel2);
        til_musteriTel3=view.findViewById(R.id.til_musteriTel3);
        til_musteriAdres1=view.findViewById(R.id.til_musteriAdres1);
        til_musteriAdres2=view.findViewById(R.id.til_musteriAdres2);
        til_musteriBilgi=view.findViewById(R.id.til_musteriBilgi);

        ed_musteriAdi=view.findViewById(R.id.ed_musteriAdi);
        ed_musteriSoyadi=view.findViewById(R.id.ed_musteriSoyadi);
        ed_musteriTel1=view.findViewById(R.id.ed_musteriTel1);
        ed_musteriTel2=view.findViewById(R.id.ed_musteriTel2);
        ed_musteriTel3=view.findViewById(R.id.ed_musteriTel3);
        ed_musteriAdres1=view.findViewById(R.id.ed_musteriAdres1);
        ed_musteriAdres2=view.findViewById(R.id.ed_musteriAdres2);
        ed_musteriBilgi=view.findViewById(R.id.ed_musteriBilgi);


        btn_musteriEkle=view.findViewById(R.id.btn_musteriEkle);

        grp_cinsiyet = view.findViewById(R.id.grp_cinsiyet);
        rdb_erkek = view.findViewById(R.id.rdb_erkek);
        rdb_kadin = view.findViewById(R.id.rdb_kadin);

        changeFragments = new ChangeFragments(getContext());
        alertShow = new AlertShow(getContext());
        internetManager = new InternetManager(getContext());

       // getFirmaList();

        for(int i =0; i < firmaList.size();i++)
        {
            if(firmaList.get(i).getFirma_id() == 2)
            {
                Log.e("goster",""+firmaList.get(i).getFirma_adi()+"--"+i);
            }
        }



        EdittextTelefonKontrol();
    }//func

    private void EdittextTelefonKontrol()
    {

        ed_musteriTel1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                int i = 10 - start;
                if(start > 10)
                {
                    btn_musteriEkle.setVisibility(View.GONE);
                }else
                {
                    btn_musteriEkle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_musteriTel2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 10 - start;
                if(start > 10)
                {
                    btn_musteriEkle.setVisibility(View.GONE);
                }else
                {
                    btn_musteriEkle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_musteriTel3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 10 - start;
                if(start > 10)
                {
                    btn_musteriEkle.setVisibility(View.GONE);
                }else
                {
                    btn_musteriEkle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }//func


    private void EventState()
    {
        btn_musteriEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetManager.InternetIsConnected())
                {
                    //kayit yap
                    MusteriViewValidate();
                }else
                {
                    Snackbar.make(linearlayout_musteriEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }//fnc
    private void MusteriKayit(String musteri_adi, String musteri_soyadi, String musteri_tel1, String musteri_tel2,
                              String musteri_tel3, String musteri_adres1, String musteri_adres2, int musteri_firma_id,
                              String musteri_bilgi, int cinsiyet, int ekleyenId, String late,String lng)
    {


        Call<Result> request = ManagerALL.getInstance().customerInsert( musteri_adi, musteri_soyadi,  musteri_tel1, musteri_tel2,
                musteri_tel3, musteri_adres1,  musteri_adres2, musteri_firma_id, musteri_bilgi,  cinsiyet,  ekleyenId,late,lng);

        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {



                    Log.e("MUSTERI KAYIT SUCCES:",response.toString());
                    Log.e("MUSTERI KAYIT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("MUSTERI KAYIT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().toString(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("MUSTERI KAYIT HATA :",t.getLocalizedMessage());
            }
        });
    }//FUNC

    private void MusteriGuncelleme(int musteri_id,String musteri_adi,String musteri_soyadi, String musteri_tel1,String musteri_tel2,
                              String musteri_tel3,String musteri_adres1, String musteri_adres2,int musteri_firma_id,
                              String musteri_bilgi, int cinsiyet, int ekleyenId)
    {
        Call<Result> request = ManagerALL.getInstance().customerUpdate( musteri_id,musteri_adi, musteri_soyadi,  musteri_tel1, musteri_tel2,
                musteri_tel3, musteri_adres1,  musteri_adres2, musteri_firma_id, musteri_bilgi,  cinsiyet,  ekleyenId);

        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("MUSTERI GUNCESUCCES:",response.toString());
                    Log.e("MUSTERI GUNCEUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("MUSTERIGUNCEl NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().toString(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("MSTRI GUNCESCCS HATA :",t.getLocalizedMessage());
            }
        });
    }//FUNC

    private void MusteriViewValidate()
    {

        ed_musteriAdi.setError(null);
        ed_musteriSoyadi.setError(null);
        ed_musteriTel1.setError(null);
        ed_musteriAdres1.setError(null);


        boolean durumAdi = TextUtils.isEmpty(ed_musteriAdi.getText());
        boolean durumSoyadi = TextUtils.isEmpty(ed_musteriSoyadi.getText());
        boolean durumTel1 =  TextUtils.isEmpty(ed_musteriTel1.getText());
        boolean durumAdres1 = TextUtils.isEmpty(ed_musteriAdres1.getText());
        boolean durumCinsiyet = grp_cinsiyet.getCheckedRadioButtonId() == -1 ? true : false;

        if(durumAdi || durumSoyadi || durumTel1   || durumAdres1 || durumCinsiyet)
        {
            if(durumAdi)
                ed_musteriAdi.setError("Adı alanı boş.");

            if(durumSoyadi)
                ed_musteriSoyadi.setError("Soyadı alanı boş.");

            if(durumTel1)
                ed_musteriTel1.setError("Tel-1 alanı boş.");

            if(durumCinsiyet)
                Snackbar.make(linearlayout_musteriEkle,"Cinsiyet Seçiniz.",Snackbar.LENGTH_LONG).show();

            if(durumAdres1)
                ed_musteriAdres1.setError("Adres alanı boş.");

        }else {

                musteri.setMusteri_adi(ed_musteriAdi.getText().toString().trim());
                musteri.setMusteri_soyadi(ed_musteriSoyadi.getText().toString().trim());
                musteri.setMusteri_tel1(ed_musteriTel1.getText().toString().trim());
                musteri.setMusteri_tel2(ed_musteriTel2.getText().toString().trim());
                musteri.setMusteri_tel3(ed_musteriTel3.getText().toString().trim());
                musteri.setMusteri_adres1(ed_musteriAdres1.getText().toString().trim());
                musteri.setMusteri_adres2(ed_musteriAdres2.getText().toString().trim());

                switch (grp_cinsiyet.getCheckedRadioButtonId())
                {
                    case R.id.rdb_erkek:
                        musteri.setMusteri_cinsiyet(0);
                        break;
                    case R.id.rdb_kadin:
                        musteri.setMusteri_cinsiyet(1);
                        break;
                }
                musteri.setMusteri_bilgi(ed_musteriBilgi.getText().toString().trim());
                musteri.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

                        //MUSTERI KAYIT VEYA GUNCELLEME ISLEMI LOKASYON ALINDIKTAN SONRA YAPILIYOR.
                        getMusteriLokasyon(musteri.getMusteri_adres1());

        }

    }//Func

   /* private void getFirmaList()
    {

        Call<List<Firma>> req = ManagerALL.getInstance().getFirmaList();
        req.enqueue(new Callback<List<Firma>>() {
            @Override
            public void onResponse(Call<List<Firma>> call, Response<List<Firma>> response) {
                if(response.isSuccessful())
                {
                    firmaList = response.body();
                    firmaBaseAdapter = new FirmaBaseAdapter(firmaList,getContext());

                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<Firma>> call, Throwable t) {

            }
        });
    }//FUNC
    */

    public void getMusteriLokasyon(String inp)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(),"Konum","Lokasyon bilgileri toplanıyor..");
        progressDialog.show();

       // String input ="turgut reis mahallesi karaosmanoğlu caddesi 434 sokak no:2 daire:7 esenler Atışalanı İstanbul".replace(" ","+");
        //String input ="bakırkoy anadolu ticaret meslek liaesi kartaltepe bakırköy istanbul".replace(" ","+");
        //String input ="Zafer mahallesi doğru sokak no:15 Yenibosna istanbul".replace(" ","+");
        String input = inp;
        String inputtype ="textquery";
        String fields = "formatted_address,name,opening_hours,geometry";
        String key = "AIzaSyCwc21CGkrX5SFPHwt7F5SqNgsqKk1Y7Rw";

        String URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+input+"&inputtype="+inputtype+"&fields="+fields+"&key="+key;


        StringRequest request = new StringRequest(Request.Method.GET, URL , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.e("TAG",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if(status.equals("OK"))
                    {
                        progressDialog.dismiss();

                        JSONArray jsonArray = jsonObject.getJSONArray("candidates");

                        String a = jsonArray.getString(0);
                        JSONObject geometry = jsonArray.getJSONObject(0);
                        JSONObject location = geometry.getJSONObject("geometry");
                        JSONObject lat = location.getJSONObject("location");

                        String late = lat.getString("lat");
                        String lute =lat.getString("lng");
                        late1 = late;
                        lng1 = lute;

                        Log.e("KONUM BULUNDU : ","*"+late+" - "+lute);
                    }else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setTitle("Konum Bilgisi");
                        alertDialog.setMessage("Adrese Ait lokasyon bilgisi alınamadı.");
                        alertDialog.show();

                        progressDialog.dismiss();
                        Log.e("KONUM BULUNAMADI : ","--"+status);
                    }

                    if(musteriId != -1)
                    {
                        Log.e("GUNCELLEME YAPILIYOR","IF");
                        //müsteri güncellemesi yapılıyor.
                        //müşteri kaydı yapılıyor
                        MusteriGuncelleme(musteriId,musteri.getMusteri_adi(),
                                musteri.getMusteri_soyadi(),
                                musteri.getMusteri_tel1(),
                                musteri.getMusteri_tel2(),
                                musteri.getMusteri_tel3(),
                                musteri.getMusteri_adres1(),
                                musteri.getMusteri_adres2(),
                                musteri.getMusteri_firma_id(),
                                musteri.getMusteri_bilgi(),
                                musteri.getMusteri_cinsiyet(),
                                musteri.getEkleyenId());
                    }else if(!musteriNumarasi.equals(""))
                    {
                        Log.e("EKLEME YAPILIYOR","ELSE IF");
                        //müşteri kaydı yapılıyor
                        MusteriKayit(musteri.getMusteri_adi(),
                                musteri.getMusteri_soyadi(),
                                musteri.getMusteri_tel1(),
                                musteri.getMusteri_tel2(),
                                musteri.getMusteri_tel3(),
                                musteri.getMusteri_adres1(),
                                musteri.getMusteri_adres2(),
                                musteri.getMusteri_firma_id(),
                                musteri.getMusteri_bilgi(),
                                musteri.getMusteri_cinsiyet(),
                                musteri.getEkleyenId(),late1,lng1);
                    }else
                    {

                        Log.e("EKLEME YAPILIYOR","ELSE ");
                        //müşteri kaydı yapılıyor
                        MusteriKayit(musteri.getMusteri_adi(),
                                musteri.getMusteri_soyadi(),
                                musteri.getMusteri_tel1(),
                                musteri.getMusteri_tel2(),
                                musteri.getMusteri_tel3(),
                                musteri.getMusteri_adres1(),
                                musteri.getMusteri_adres2(),
                                musteri.getMusteri_firma_id(),
                                musteri.getMusteri_bilgi(),
                                musteri.getMusteri_cinsiyet(),
                                musteri.getEkleyenId(),late1,lng1);
                    }

                }catch (JSONException e)
                {

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("KONUM BULMA HATASI :",error.toString());
            }
        });

        requestQueue.add(request);

    }

    private void getCustomer(int musteriId)
    {
        Call<Musteri> req = ManagerALL.getInstance().getCustomer(musteriId);
        req.enqueue(new Callback<Musteri>() {
            @Override
            public void onResponse(Call<Musteri> call, Response<Musteri> response) {
                Musteri musteri = response.body();
                ed_musteriAdi.setText(musteri.getMusteri_adi());
                ed_musteriSoyadi.setText(musteri.getMusteri_soyadi());
                ed_musteriTel1.setText(musteri.getMusteri_tel1());
                ed_musteriTel2.setText(musteri.getMusteri_tel2());
                ed_musteriTel3.setText(musteri.getMusteri_tel3());
                ed_musteriAdres1.setText(musteri.getMusteri_adres1());
                ed_musteriAdres2.setText(musteri.getMusteri_adres2());

                if(musteri.getMusteri_cinsiyet() == 1)
                {
                    rdb_kadin.setChecked(true);
                    rdb_erkek.setChecked(false);
                }else{
                    rdb_erkek.setChecked(true);
                    rdb_kadin.setChecked(false);
                }

                int selectIndis = -1;
                for (int i=0;i<firmaList.size();i++)
                {
                    if(firmaList.get(i).getFirma_id() == musteri.getMusteri_firma_id())
                    {
                        selectIndis = i;
                    }
                }
               ed_musteriBilgi.setText(musteri.getMusteri_bilgi());
            }

            @Override
            public void onFailure(Call<Musteri> call, Throwable t) {

            }
        });
    }//func
}
