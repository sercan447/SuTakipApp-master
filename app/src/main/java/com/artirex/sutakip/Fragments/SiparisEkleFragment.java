package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.artirex.sutakip.Adapters.AracBaseAdapter;
import com.artirex.sutakip.Adapters.StokListeAdapter;
import com.artirex.sutakip.Adapters.UrunBoyutBaseAdapter;
import com.artirex.sutakip.Adapters.UrunTipiBaseAdapter;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.Model.Siparis;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;
import com.onesignal.OneSignal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SiparisEkleFragment extends Fragment {

    View view;
    private ChangeFragments changeFragments;
    private InternetManager internetManager;
    private AlertShow alertShow;
    private LinearLayout linearlayout_siparisEkle;

    TextInputLayout til_siparisMusteriAdSoyad,til_siparisMusteriBilgi;
    TextInputEditText ed_siparisMusteriAdSoyad,ed_siparisMusteriBilgi;
    Spinner spi_siparisUrunTipi,spi_siparisUrunBoyutu,spi_siparisArac;


    StokListeAdapter siparisListeAdapter;
    List<StokBilgi> siparisEkle = new ArrayList<>();
    List<StokBilgiShowList> siparisBilgiShowLists = new ArrayList<>();

    private EditText ed_siparisbilgi;
    private ListView listview_siparisliste;
    private Button btn_siparisListeyeEkle;


    private Button btn_siparisEkle;

    UrunTipiBaseAdapter urunTipiBaseAdapter;
    UrunBoyutBaseAdapter urunBoyutBaseAdapter;
    AracBaseAdapter aracBaseAdapter;

    List<Urun> urunList = new ArrayList<>();
    List<AltUrun> altUrunList = new ArrayList<AltUrun>();
    List<AracGet> aracList = new ArrayList<>();

    private String urunTipiAdi="",altUrunAdi="",plakaAdi="";
    private int urunTipId=0,urunBoyutId=0,aracId=0;

    private int siparisId = -1;
    private int musteriId = -1;
    private String musteriAdi="";
    private String musteriSoyadi="";

    Bundle bundle;

    public SiparisEkleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_siparis_ekle, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Sipariş Modülü");

        ViewComponentsInitialize();

        if(getArguments() != null) {
            bundle = getArguments();
            siparisId = bundle.getInt("siparisId", -1);

            musteriId = 0;
            musteriAdi = "";
            musteriSoyadi = "";

            //kayıtlı olan MÜŞTERİNIN BILGILERI CEKILIYOR.

            musteriId = bundle.getInt("musteriId",-1);
            musteriAdi = bundle.getString("musteriAdi","");
            musteriSoyadi = bundle.getString("musteriSoyadi","");

            if(musteriId != -1)
            {
                ed_siparisMusteriAdSoyad.setText(musteriAdi+" "+musteriSoyadi);
                Log.e("SIPAIRS EKLEYE ","GITTI");
                Log.e("SiparisEkle",""+musteriId);
                Log.e("musteriAdi",""+musteriAdi);
                Log.e("musteriSoyadi",""+musteriSoyadi);
            }else{
                Log.e("SIPAIRS EKLEYE ","verı getiremedi.");
            }

        }


        if(siparisId != -1)
        {
            getOrder(siparisId);
        }
        EventState();
        return view;
    }

    private void SiparisKayit(final Siparis siparis)
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setTitle("Kayıt");
            dialog.setMessage("Sipariş Kaydı oluşturuluyor");
            dialog.show();

        Call<Result> request = ManagerALL.getInstance().orderInsert(siparis);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    dialog.dismiss();

                    Log.e("SIPARIS KAYIT SUCCES:",response.toString());
                    Log.e("SIPARIS KAYIT SUCES2:",response.body().toString());
                    Log.e("SIPARIS KAYIT SOFORID",response.body().getHatadurumu());

                    //hızlı bir şekildehalletmek için tokenı hataDurumu değişkeni içerisinden alıyorum şimdilik... :)
                    String soforTokenBilgisi = response.body().getToken();

                  //  Log.e("TOKEN KAYIT ISTEnIYOR",""+soforTokenBilgisi);

                    try {
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'"+siparis.getStokBilgiList().size()+" Adet Sipariniz Oluşturuldu.'}, 'include_player_ids': ['" + soforTokenBilgisi + "']}"), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    dialog.dismiss();

                    Log.e("SIPARIS KAYIT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dialog.dismiss();
                Log.e("SIPARIS KAYIT HATA :",t.getLocalizedMessage());
                Log.e("SIPARIS KAYIT HATA2 :",t.toString());
            }
        });
    }

   /*
    private void SiparisKayit(int siparis_urun_tipi,int siparis_urun_boyutu, String siparis_bilgi, int siparis_musteri_id,
                              int siparis_arac_id, int siparis_durum, int ekleyenId)
    {
        Call<Result> request = ManagerALL.getInstance().orderInsert(siparis_urun_tipi,siparis_urun_boyutu,siparis_bilgi,siparis_musteri_id,
                                                                             siparis_arac_id,siparis_durum,ekleyenId);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("SIPARIS KAYIT SUCCES:",response.toString());
                    Log.e("SIPARIS KAYIT SUCES2:",response.body().toString());
                    Log.e("SIPARIS KAYIT SOFORID",response.body().getHatadurumu());

                    //hızlı bir şekildehalletmek için tokenı hataDurumu değişkeni içerisinden alıyorum şimdilik... :)
                    String soforTokenBilgisi = response.body().getHatadurumu();

                    try {
                        OneSignal.postNotification(new JSONObject("{'contents': {'en':'Sipariş var.'}, 'include_player_ids': ['" + soforTokenBilgisi + "']}"), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("SIPARIS KAYIT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("SIPARIS KAYIT HATA :",t.getLocalizedMessage());
                Log.e("SIPARIS KAYIT HATA2 :",t.toString());
            }
        });
    }
    */

    private void ViewComponentsInitialize() {

        changeFragments  = new ChangeFragments(getContext());
        internetManager = new InternetManager(getContext());
        alertShow = new AlertShow(getContext());

        linearlayout_siparisEkle = view.findViewById(R.id.linearlayout_siparisEkle);

        til_siparisMusteriAdSoyad = view.findViewById(R.id.til_siparisMusteriAdSoyad);
        til_siparisMusteriBilgi = view.findViewById(R.id.til_siparisMusteriBilgi);

        ed_siparisMusteriAdSoyad = view.findViewById(R.id.ed_siparisMusteriAdSoyad);
        ed_siparisMusteriBilgi = view.findViewById(R.id.ed_siparisMusteriBilgi);

        spi_siparisUrunTipi = view.findViewById(R.id.spinner_siparisUrunTipi);
        spi_siparisUrunBoyutu = view.findViewById(R.id.spinner_siparisUrunBoyutu);
        spi_siparisArac = view.findViewById(R.id.spinner_siparisArac);

        btn_siparisEkle = view.findViewById(R.id.btn_siparisEkle);


        ed_siparisbilgi = view.findViewById(R.id.ed_siparisbilgi);
        listview_siparisliste = view.findViewById(R.id.listview_siparisliste);
        btn_siparisListeyeEkle = view.findViewById(R.id.btn_siparisListeyeEkle);

        listview_siparisliste.setVisibility(View.GONE);

        getProductList();
        getCarList();
    }
    private void SiparisViewValidate() {
        ed_siparisMusteriAdSoyad.setError(null);

        boolean durumMusteriAdsoyad = TextUtils.isEmpty(ed_siparisMusteriAdSoyad.getText());
        if(durumMusteriAdsoyad)
        {
            if(durumMusteriAdsoyad)
                ed_siparisMusteriAdSoyad.setError("Müşteri alanı boş.");

        }else
        {
            Siparis siparis = new Siparis();
            siparis.setSiparis_arac_id(aracId);
            siparis.setSiparis_bilgi(ed_siparisMusteriBilgi.getText().toString());
            siparis.setSiparis_durum(1); // 1-siparis alındı / 0-siparis iptal  / 2 - siparis-teslim edildi
         //   siparis.setSiparis_musteri_id(Integer.valueOf(ed_siparisMusteriAdSoyad.getText().toString()));
            if(musteriId != -1){
                siparis.setSiparis_musteri_id(musteriId);
            }else{
                siparis.setSiparis_musteri_id(Integer.valueOf(ed_siparisMusteriAdSoyad.getText().toString()));
            }

            siparis.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

            siparis.setStokBilgiList(siparisEkle);

          /*  SiparisKayit(siparis.getSiparis_urun_tipi(),siparis.getSiparis_urun_boyutu(),siparis.getSiparis_bilgi(),siparis.getSiparis_musteri_id(),
                                        siparis.getSiparis_arac_id(),siparis.getSiparis_durum(),siparis.getEkleyenId());
            */
    if(siparisEkle.size() != 0)
    {
        SiparisKayit(siparis);
    }else
    {
        Snackbar.make(linearlayout_siparisEkle, "Sipariş için Ürün girmelisiniz.",Snackbar.LENGTH_LONG).show();
    }

        }
    }

    private void EventState()
    {
        btn_siparisEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internetManager.InternetIsConnected())
                {
                    SiparisViewValidate();
                }else
                {
                    Snackbar.make(linearlayout_siparisEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });

        spi_siparisUrunTipi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Urun tiklananTip = (Urun)parent.getSelectedItem();
                urunTipId = tiklananTip.getUrun_id();
                urunTipiAdi = tiklananTip.getUrun_adi();
                //Toast.makeText(getContext(),"tiklanan"+tiklananTip.getUrun_adi(),Toast.LENGTH_SHORT).show();
                getSubProductList(tiklananTip.getUrun_id());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spi_siparisUrunBoyutu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AltUrun tiklananBoyut = (AltUrun)parent.getSelectedItem();
                urunBoyutId = tiklananBoyut.getAlturun_id();
                altUrunAdi = tiklananBoyut.getAlturun_adi();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spi_siparisArac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AracGet tiklananArac = (AracGet) parent.getSelectedItem();
                plakaAdi = tiklananArac.getArac_plaka();
                aracId = tiklananArac.getArac_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_siparisListeyeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LISTEYE ELEMAN EKLENECEGI ZAMAN LİSTEYI GORUNUR YAPIYORUM

                listview_siparisliste.setVisibility(View.VISIBLE);

                int stokSayisi = Integer.valueOf(ed_siparisbilgi.getText().toString());

                Log.e("TAG","listeye ekleniyor..");

                //BU SINIF LİSTEDE EKLENEN SIPARIS BILGILERINI GOSTERMEK ICIN
                StokBilgiShowList stokshow = new StokBilgiShowList(plakaAdi,urunTipiAdi,altUrunAdi,stokSayisi);
                //BU ISE VERITABANINI EKLEME YAPMAK ICIN OLUSTURULAN SINIF
                StokBilgi stokBilgi = new StokBilgi(aracId,urunTipId,urunBoyutId,stokSayisi);

                siparisBilgiShowLists.add(stokshow);
                siparisEkle.add(stokBilgi);

                siparisListeAdapter = new StokListeAdapter(siparisBilgiShowLists,getContext());

                Log.e("DATALAR",""+ siparisEkle.toString());

                listview_siparisliste.setAdapter(siparisListeAdapter);
                siparisListeAdapter.notifyDataSetChanged();
            }
        });
    }//FUNC

    //listview içerisindeki adapter da silinen Item bbadaki liste içerisinde de silinmek zorunda yoksa kayıt işleminde Veritabanını kayıt yapıyor. siz liste içerisinde silmiş oldl,uğunuzu düşünseninzde

    @Subscribe(sticky = true)
    public void ListViewItemDelete(StokListeAdapter.ListeItemDeleteEvent listeItemDeleteEvent)
    {
        // stokBilgiShowLists.remove(listeItemDeleteEvent.getItemId());
        siparisEkle.remove(listeItemDeleteEvent.getItemId());
    }


    private void getProductList()
    {
        Call<List<Urun>> request = ManagerALL.getInstance().getProductList();
        request.enqueue(new Callback<List<Urun>>() {
            @Override
            public void onResponse(Call<List<Urun>> call, Response<List<Urun>> response) {

                if(response.isSuccessful())
                {
                    urunList = response.body();
                    urunTipiBaseAdapter = new UrunTipiBaseAdapter(urunList,getContext());
                    spi_siparisUrunTipi.setAdapter(urunTipiBaseAdapter);
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
                    urunBoyutBaseAdapter = new UrunBoyutBaseAdapter(altUrunList,getContext());

                    spi_siparisUrunBoyutu.setAdapter(urunBoyutBaseAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<AltUrun>> call, Throwable t) {
            }
        });
    }//func

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
                    spi_siparisArac.setAdapter(aracBaseAdapter);
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<AracGet>> call, Throwable t) {

            }
        });
    }//FUNC

    private void getOrder(int siparisId)
    {
        Call<SiparisGet> req = ManagerALL.getInstance().getOrder(siparisId);
        req.enqueue(new Callback<SiparisGet>() {
            @Override
            public void onResponse(Call<SiparisGet> call, Response<SiparisGet> response) {
                SiparisGet siparisget = response.body();

                if(response.isSuccessful())
                {
                    ed_siparisMusteriAdSoyad.setText(siparisget.getMusteri_adi()+" "+siparisget.getMusteri_soyadi());
                    ed_siparisMusteriBilgi.setText(siparisget.getSiparis_bilgi());

                    int selectTipIndis = -1;
                    for (int i=0;i<urunList.size();i++)
                    {
                        /// Log.e("t",urunList.get(i).getUrun_tipi().toUpperCase() +"--"+ aracGet.getArac_model().toUpperCase());
                        /*if(urunList.get(i).getUrun_tipi().equalsIgnoreCase(siparisget.getUrun_tipi()))
                        {
                            selectTipIndis = i;
                            Log.e("t","İF");
                        }else{
                            Log.e("t","else");
                        }
                        */
                    }
                    spi_siparisUrunTipi.setSelection(selectTipIndis);

                    int selectBoyutIndis = -1;
                    for (int i=0;i<altUrunList.size();i++)
                    {
                        /// Log.e("t",urunList.get(i).getUrun_tipi().toUpperCase() +"--"+ aracGet.getArac_model().toUpperCase());
                      /*  if(altUrunList.get(i).getAlturun_adi().equalsIgnoreCase(siparisget.getAlturun_adi()))
                        {
                            selectBoyutIndis = i;
                            Log.e("BOYUT","İF");
                        }else{
                            Log.e("BOYUT else","else");
                        }*/
                        Log.e("BOYUT ",""+selectBoyutIndis);
                    }
                    spi_siparisUrunBoyutu.setSelection(selectBoyutIndis);


                    int selectAracIndis = -1;
                    for (int i=0;i<aracList.size();i++)
                    {
                        /// Log.e("t",urunList.get(i).getUrun_tipi().toUpperCase() +"--"+ aracGet.getArac_model().toUpperCase());
                        if(aracList.get(i).getArac_plaka().equalsIgnoreCase(siparisget.getArac_plaka()))
                        {
                            selectAracIndis = i;
                            Log.e("t","İF");
                        }else{
                            Log.e("t","else");
                        }

                    }
                    spi_siparisArac.setSelection(selectAracIndis);
                }
            }

            @Override
            public void onFailure(Call<SiparisGet> call, Throwable t) {

            }
        });
    }//FUNC



    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }
}
