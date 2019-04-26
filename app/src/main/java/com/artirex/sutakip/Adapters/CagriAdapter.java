package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.artirex.sutakip.Fragments.MusteriEkleFragment;
import com.artirex.sutakip.Fragments.SiparisEkleFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Cagrilar;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CagriAdapter extends RecyclerView.Adapter<CagriAdapter.MyHolder> {

    private final int LISTE_VIEW = 1;
    private final int TABLE_VIEW = 2;
    private final int DIFFERENT_VIEW = 3;


    private Context context;
    private List<Cagrilar> cagrilarList;
    Activity activity;
    ChangeFragments changeFragments;

    public CagriAdapter(Context context, List<Cagrilar> cagrilarList, Activity activity) {
        this.context = context;
        this.cagrilarList = cagrilarList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_cagrilar,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);

        changeFragments = new ChangeFragments(context);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {

        if(cagrilarList.get(i).getTelefon_no().equalsIgnoreCase("100"))
        {
            //100-101 	: 	Sistem hatası, sınır aşımı.(dakikada en fazla 2 kez sorgulanabilir.)

            myHolder.btn.setText("100 gldi");
            return;
        }else if(cagrilarList.get(i).getTelefon_no().equalsIgnoreCase("70")){

            //Hatalı sorgulama. Gönderdiğiniz parametrelerden birisi hatalı veya zorunlu alanlardan birinin eksik olduğunu ifade eder.
            myHolder.btn.setText("100 gldi");
        }else if(cagrilarList.get(i).getTelefon_no().equalsIgnoreCase("40")){

            //Arama kriterlerinize göre listelenecek kayıt olmadığını ifade eder.
            return;
          //  myHolder.btn.setText("40 gldi");

        }else if(cagrilarList.get(i).getTelefon_no().equalsIgnoreCase("50")){

            // 	: 	Arama kriterlerindeki tarih formatının hatalı olduğunu ifade eder. myHolder.btn.setText("100 gldi");
            myHolder.btn.setText("50 gldi");

        }else if(cagrilarList.get(i).getTelefon_no().equalsIgnoreCase("30")) {

            // 	 	Geçersiz kullanıcı adı , şifre veya kullanıcınızın API erişim izninin olmadığını gösterir.
            //Ayrıca eğer API erişiminizde IP sınırlaması yaptıysanız ve sınırladığınız ip dışında gönderim sağlıyorsanız 30 hata kodunu alırsınız.
            // API erişim izninizi veya IP sınırlamanızı , web arayüzümüzden; sağ üst köşede bulunan ayarlar> API işlemleri menüsunden kontrol edebilirsiniz.
            myHolder.btn.setText("30 gldi");
        }else{

        /*
    0: Giden Arama
  	1: Gelen Arama
  	2: Gelen Cevapsız Arama
  	3: Giden Cevapsız Arama
  	4: İç Arama
  	5: İç Cevapsız Arama
     */
           if(cagrilarList.get(i).getAramaTipi() == 0)
           {
               myHolder.cagri_aramaSayisi.setText("Giden Arama");
           }else if(cagrilarList.get(i).getAramaTipi() == 1)
           {
               myHolder.cagri_aramaSayisi.setText("Gelen Arama");
           }else if(cagrilarList.get(i).getAramaTipi() == 2)
           {
               myHolder.cagri_aramaSayisi.setText("Gelen Cevapsız Arama");
           }else if(cagrilarList.get(i).getAramaTipi() == 3)
           {
               myHolder.cagri_aramaSayisi.setText("Giden Cevapsız Arama");
           }else if(cagrilarList.get(i).getAramaTipi() == 4)
           {
               myHolder.cagri_aramaSayisi.setText("İç Arama");
           }else if(cagrilarList.get(i).getAramaTipi() == 5)
            {
                myHolder.cagri_aramaSayisi.setText("İç Cevapsız Arama");
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this)
                    {
                        getMusteriKim(cagrilarList.get(i).getTelefon_no(),myHolder);
                    }

                }
            });
           thread.setDaemon(true);
           thread.start();

            myHolder.cagri_aramaTarihi.setText(cagrilarList.get(i).getAramaZamani());


        }
    }


    private void getMusteriKim(final String telefon, final MyHolder myHolder)
    {
        final Musteri[] musteri = new Musteri[1];
        Call<Musteri> req = ManagerALL.getInstance().getMusteriKim(telefon);
        req.enqueue(new Callback<Musteri>() {
            @Override
            public void onResponse(Call<Musteri> call, Response<Musteri> response) {

                musteri[0] = response.body();
                String musteriKim ="-";
                if(response.isSuccessful())
                {
                    if(musteri[0].isTf())
                    {
                        musteriKim = musteri[0].getMusteri_adi()+ " "+ musteri[0].getMusteri_soyadi();
                        myHolder.cagri_telefonNo.setText(musteriKim+" | "+telefon);

                        Log.e("AAAA",myHolder.btn.getText()+"");

                        myHolder.btn.setTag("siparis");
                        myHolder.btn.setText("Sipariş Ekle");

                    }else
                    {
                        myHolder.btn.setTag("musteri");
                        myHolder.btn.setText("Müşteri Ekle");
                        myHolder.cagri_telefonNo.setText(telefon);

                    }
                }else{

                }

                Log.e("TELEFON TANIMLAMA ",response.body().toString());
            }

            @Override
            public void onFailure(Call<Musteri> call, Throwable t) {
                Log.e("TELEFON TANIMLAMA ",t.getLocalizedMessage());
            }
        });

        myHolder.cardview_item_cagrilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=null;
                if(myHolder.btn.getTag().equals("musteri"))
                {
                    MusteriEkleFragment musteriEkleFragment = new MusteriEkleFragment();
                    bundle  = new Bundle();
                    bundle.putString("telefonNumarasi",telefon);
                    musteriEkleFragment.setArguments(bundle);
                    changeFragments.change(musteriEkleFragment,"MusteriEkleFragmentTAG");

                }else if(myHolder.btn.getTag().equals("siparis"))
                {
                    SiparisEkleFragment siparisEkleFragment = new SiparisEkleFragment();
                    bundle = new Bundle();
                    bundle.putInt("musteriId", musteri[0].getMusteri_id());
                    bundle.putString("musteriAdi","*"+ musteri[0].getMusteri_adi());
                    bundle.putString("musteriSoyadi","*"+ musteri[0].getMusteri_soyadi());

                    siparisEkleFragment.setArguments(bundle);

                    changeFragments.change(siparisEkleFragment,"SiparisEkleFragmentTag");
                }
            }
        });
    }//func


    @Override
    public int getItemCount() {
        return  cagrilarList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView cagri_telefonNo,cagri_aramaTarihi,cagri_aramaSayisi;
        CardView cardview_item_cagrilar;
        Button btn;


        public MyHolder(@NonNull View itemView) {
            super(itemView);


            cagri_telefonNo = itemView.findViewById(R.id.cagri_telefonNo);
            cagri_aramaTarihi = itemView.findViewById(R.id.cagri_aramaTarihi);
            cagri_aramaSayisi = itemView.findViewById(R.id.cagri_aramaSayisi);

            cardview_item_cagrilar = itemView.findViewById(R.id.cardview_item_cagrilar);
            btn = itemView.findViewById(R.id.btnMusteriDurumu);



        }
    }//class
}
