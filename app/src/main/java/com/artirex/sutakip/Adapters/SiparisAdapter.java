package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Fragments.SiparisEkleFragment;
import com.artirex.sutakip.HaritaMapsActivity;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.TelefonArama;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiGet;
import com.artirex.sutakip.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SiparisAdapter extends RecyclerView.Adapter<SiparisAdapter.MyHolder> {


    private Context context;
    private List<SiparisGet> siparisList;
    Activity activity;
    private AlertShow alertShow;
    private TelefonArama telefonArama;

    public SiparisAdapter(Context context, List<SiparisGet> siparisList, Activity activity) {
        this.context = context;
        this.siparisList = siparisList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_siparisler,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        alertShow = new AlertShow(context);
        telefonArama = new TelefonArama(context);

        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {


           myHolder.siparis_tv_adet.setText(siparisList.get(i).getSiparis().size()+ " Adet");
              myHolder.siparis_tv_arac.setText(siparisList.get(i).getArac_plaka());
        myHolder.siparis_zaman.setText(siparisList.get(i).getSiparis_eklenmeTarihi());

             if(siparisList.get(i).getSiparis_durum() == 0)  //iptal eedilen
                  myHolder.tab_imgProfile.setImageResource(R.drawable.productcancel);
              else if (siparisList.get(i).getSiparis_durum() == 1) //alınan siparis
                  myHolder.tab_imgProfile.setImageResource(R.drawable.product);
              else if(siparisList.get(i).getSiparis_durum() == 2) //teslim edilen siparis
                  myHolder.tab_imgProfile.setImageResource(R.drawable.productok);


              myHolder.siparis_tv_adsoyad.setText(siparisList.get(i).getMusteri_adi().toLowerCase()+
                                                    " "+siparisList.get(i).getMusteri_soyadi().toUpperCase());
                    final StringBuilder  bt = new StringBuilder();

                      myHolder.cardview_item_araclar.setOnLongClickListener(new View.OnLongClickListener() {
                          @Override
                          public boolean onLongClick(View v) {
                              //string builder içerisini temizliyorum. yoksa her seferinde üst üste append yapıyor.
                              bt.setLength(0);
                              for (StokBilgiGet b : siparisList.get(i).getSiparis())
                              {
                                  bt.append(b.getUrun_adi().toUpperCase()+"-"+b.getAlturun_adi().toLowerCase()+" = "+b.getStok()+"\n");
                              }

                              //alertShow.AlertDialogSiparisDetay(siparisList.get(i),bt.toString());
                              alertShow.AlertDialogSiparisDetay(siparisList.get(i),siparisList.get(i).getSiparis());

                              Toast.makeText(context,"UUZN TIKLAMA",Toast.LENGTH_SHORT).show();
                              return true;
                          }
                      });


         /*   myHolder.cardview_item_araclar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //siparisId bilgisini yollayark onu düzenleme kısmına gotuyorum
                    SiparisEkleFragment siparisEkleFragment = new SiparisEkleFragment();
                    ChangeFragments changeFragments = new ChangeFragments(context);

                    Bundle bundle = new Bundle();
                    bundle.putInt("siparisId",siparisList.get(i).getSiparis_id());

                    siparisEkleFragment.setArguments(bundle);
                    if((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                    {
                        changeFragments.change(siparisEkleFragment,"siparisEkleFragmentTag");
                    }else
                    {
                        changeFragments.changeNavbar(siparisEkleFragment,"siparisEkleFragmentTag",null);
                    }
                }
            });*/

                myHolder.tab_imgadres.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!siparisList.get(i).getLat().equals("LAT"))
                        {
                            double lat = Double.valueOf(siparisList.get(i).getLat().toString());
                            double lng = Double.valueOf(siparisList.get(i).getLng().toString());

                            Log.e("KONUM GELIYO","-"+lat+"-"+lng);

                            Intent intent = new Intent(context, HaritaMapsActivity.class);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lng", lng);
                            intent.putExtra("adres",""+siparisList.get(i).getMusteri_adres1());
                            context.startActivity(intent);
                        }else
                        {
                           // alertShow.AlertDialogSiparisDetay(siparisList.get(i),bt.toString());
                        }
                    }
                });

                if(siparisList.get(i).getSiparis_durum() == 1) {
                    SureTanimlamaIslemleri(myHolder, siparisList.get(i));
                }
    }

    private void SureTanimlamaIslemleri(final MyHolder myHolder, final SiparisGet siparisGet)
    {
         String strDate = siparisGet.getSiparis_eklenmeTarihi();
        String pattern = "yyyy-MM-dd hh:mm:ss";

        DateFormat df = new SimpleDateFormat(pattern);
        Date siparisDate = null;
        try {
            siparisDate = df.parse(strDate);
        }catch (ParseException e)
        {
            Log.e("HATA ",e.getLocalizedMessage());
        }
        //SİPARİS ZAMANI
        Calendar siparisCalendar = Calendar.getInstance();
        siparisCalendar.set(Calendar.YEAR,siparisDate.getYear());
        siparisCalendar.set(Calendar.MONTH,siparisDate.getMonth());
        siparisCalendar.set(Calendar.DAY_OF_WEEK,siparisDate.getDay());
        siparisCalendar.set(Calendar.HOUR_OF_DAY,siparisDate.getHours());
        siparisCalendar.set(Calendar.MINUTE,siparisDate.getMinutes()+60);
        siparisCalendar.set(Calendar.SECOND,siparisDate.getSeconds());

        long siparisSaati = siparisCalendar.getTimeInMillis();

        String bugun = df.format(System.currentTimeMillis());
        Date bugunDate=null;
        try {
            bugunDate = df.parse(bugun);

        }catch (ParseException e)
        {
        }


        new CountDownTimer(System.currentTimeMillis()-siparisSaati,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    //sipariş gunu bugunun siparişi ise gösteriyorum
                    //yoksa göstermiyorum.
                    //if(finalSiparisDate.getDay() == finalBugunDate.getDay())
                    //{
                        int hours = (int)Math.floor((millisUntilFinished % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                        int minutes =   (int)Math.floor((millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60)) ;
                        int seconds = (int)Math.floor((millisUntilFinished % (1000 * 60)) / 1000);

                        myHolder.siparis_zaman.setText(minutes+" "+seconds+" || "+siparisGet.getSiparis_eklenmeTarihi());

                }


                @Override
                public void onFinish() {


                    //zaman sonunda sipariş iptal mı yoksa teslim mi edilmiş bu gibi bilgiler veriyorum
                    if(siparisGet.getSiparis_durum() == 1)
                    {
                        ///sipariş ulaştırılamadı
                        myHolder.siparis_zaman.setText("Sipariş Verildi. "+siparisGet.getSiparis_eklenmeTarihi());
                    }else if(siparisGet.getSiparis_durum() == 4){
                        //if sipariş iptal edildi
                        myHolder.siparis_zaman.setText("Sipariş İptal Edildi ");
                    }else if(siparisGet.getSiparis_durum() == 2)
                    {
                        myHolder.siparis_zaman.setText("Sipariş Teslim edildi.");
                    }else if(siparisGet.getSiparis_durum() == 14)
                    {
                        myHolder.siparis_zaman.setText("Sipariş Onaylandı");
                    }else{
                        myHolder.siparis_zaman.setText(siparisGet.getSiparis_eklenmeTarihi());
                    }

                }
            }.start();



    }//FUNC


    @Override
    public int getItemCount() {
        return  siparisList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView siparis_tv_arac,siparis_tv_adet,siparis_tv_boyutu,siparis_tv_adsoyad,siparis_zaman;
        ImageView tab_imgProfile,tab_imgadres;
        CardView cardview_item_araclar;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            siparis_tv_arac = itemView.findViewById(R.id.siparis_tv_arac);
            siparis_tv_adet = itemView.findViewById(R.id.siparis_tv_adet);
            siparis_tv_boyutu = itemView.findViewById(R.id.siparis_tv_boyutu);
            tab_imgProfile = itemView.findViewById(R.id.tab_imgProfile);
            siparis_tv_adsoyad = itemView.findViewById(R.id.siparis_tv_adsoyad);
            siparis_zaman = itemView.findViewById(R.id.siparis_zaman);
            tab_imgadres = itemView.findViewById(R.id.tab_imgadres);

            cardview_item_araclar = itemView.findViewById(R.id.cardview_item_araclar);

        }
    }//class
}
