package com.artirex.sutakip.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.OdemeTuruBaseAdapter;
import com.artirex.sutakip.Adapters.StokListeAdapter;
import com.artirex.sutakip.Model.OdemeTuru;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.Model.Siparis;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiGet;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertShow {

    Context context;
    private TelefonArama telefonArama;
    private List<OdemeTuru> odemeTuruList = new ArrayList<>();

   private Spinner spinner_odeme_turu;
   private  int  odemeId = -1;

    public AlertShow(Context context)
    {
        this.context = context;
    }

    public void AlertDialogKutusu(String baslik,String mesaj,int icon)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(baslik);
        builder.setMessage(mesaj);
        builder.setIcon(icon);
        builder.setCancelable(false);
        builder.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void AlertDialogSiparisDetay(final SiparisGet siparisGet, List<StokBilgiGet> siparisler)
    {

        telefonArama = new TelefonArama(context);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Sipariş Detay");

        View view = LayoutInflater.from(context).inflate(R.layout.alert_siparisdetay,null);
        final Date tarih = new Date();



        ListView listview_siparisDetay = view.findViewById(R.id.listview_siparisDetay);


        // SIPARIS LISTESINI LISTVIEW ICERISINE DOLDURUYORUZ.

        StokListeAdapter siparisListeAdapter;
        List<StokBilgi> siparisEkle = new ArrayList<>();
        List<StokBilgiShowList> siparisBilgiShowLists = new ArrayList<>();

        for (int i=0; i < siparisler.size();i++)
        {
            int id = siparisler.get(i).getStokbilgi_id();
            String urunadi = siparisler.get(i).getUrun_adi();
            String alturun = siparisler.get(i).getAlturun_adi();
            int stok = siparisler.get(i).getStok();

            StokBilgiShowList stokshow = new StokBilgiShowList(id,"",urunadi,alturun,stok);
            siparisBilgiShowLists.add(stokshow);
        }


        siparisListeAdapter = new StokListeAdapter(siparisBilgiShowLists,context);
        listview_siparisDetay.setAdapter(siparisListeAdapter);


        TextView siparisdetay_AdiSoyadi = view.findViewById(R.id.siparisdetay_AdiSoyadi);
        ImageView siparisdetay_telefon = view.findViewById(R.id.siparisdetay_telefon);
        TextView siparisdetay_Adres = view.findViewById(R.id.siparisdetay_Adres);
        TextView siparisdetay_siparisDurum = view.findViewById(R.id.siparisdetay_siparisDurum);
         spinner_odeme_turu = view.findViewById(R.id.spinner_odeme_turu);


        siparisdetay_AdiSoyadi.setText(siparisGet.getMusteri_adi().toUpperCase()+" "+siparisGet.getMusteri_soyadi().toUpperCase());
        siparisdetay_Adres.setText(siparisGet.getMusteri_adres1());

        if(siparisGet.getSiparis_durum() == 1)
        {
            siparisdetay_siparisDurum.setText("Teslimatta");
            siparisdetay_siparisDurum.setTextColor(Color.GRAY);
        }else if(siparisGet.getSiparis_durum() == 2)
        {
            siparisdetay_siparisDurum.setText("Teslim Edildi.");
            siparisdetay_siparisDurum.setTextColor(Color.GREEN);
        }else if(siparisGet.getSiparis_durum() == 4)
        {
            siparisdetay_siparisDurum.setText("Sipariş İptal");
            siparisdetay_siparisDurum.setTextColor(Color.RED);
        }


       siparisdetay_telefon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                telefonArama.CallPhone(siparisGet.getMusteri_tel1());
           }
       });

        Button btn_siparisTeslimEdildi = view.findViewById(R.id.btn_siparisTeslimEdildi);
        Button btn_siparisIptal = view.findViewById(R.id.btn_siparisIptal);


        getOdemeBilgi();

        //ODEME TURU SECİLİP
        spinner_odeme_turu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                OdemeTuru secilenOdeme = (OdemeTuru)parent.getSelectedItem();
                odemeId = secilenOdeme.getOdeme_id();
                Log.e("DUNYAKADINLAR",""+odemeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_siparisTeslimEdildi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("DUNYAKADINLAR2",""+odemeId);
               // Toast.makeText(context,"teslim edildi."+odemeId,Toast.LENGTH_SHORT).show();
                SiparisdurumGuncelle(2,tarih.toString(),siparisGet.getSiparis_id(),odemeId);
                dialog.create().cancel();
            }
        });

        btn_siparisIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"siparis iptal edildi.."+tarih.toString()+"-"+siparisGet.getSiparis_id(),Toast.LENGTH_LONG).show();
                SiparisdurumGuncelle(4,tarih.toString(),siparisGet.getSiparis_id(),0);
                dialog.create().cancel();
            }
        });

        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();
    }
    private void getOdemeBilgi()
    {
        odemeTuruList.clear();
        String[] odemebilgi = context.getResources().getStringArray(R.array.odeme_turu);

        if(odemebilgi.length != 0)
        {
            for(int i = 0; i < odemebilgi.length; i++)
            {
                odemeTuruList.add(new OdemeTuru(i,odemebilgi[i]));
            }
            //Odeme bilgisi spinner içerisi dolduruluyor
            OdemeTuruBaseAdapter odemeTuruBaseAdapter = new OdemeTuruBaseAdapter(odemeTuruList,context);
            spinner_odeme_turu.setAdapter(odemeTuruBaseAdapter);
        }
    }//func
    private void SiparisdurumGuncelle(int siparis_durum, String durumTarihi,int siparis_id,int odemeTuru)
    {
        Call<Result> req = ManagerALL.getInstance().siparisDurumGuncelleme(siparis_durum,durumTarihi,siparis_id,odemeTuru);

        req.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(context,"IF : "+response.body().getMesaj(),Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context,"ELSE : "+response.body().getMesaj(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.e("alertshow : ",t.getLocalizedMessage().toString());
            }
        });
    }
}
