package com.artirex.sutakip.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Fragments.UrunEkleFragment;
import com.artirex.sutakip.HaritaMapsActivity;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Siparis;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiGet;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;

import java.util.List;

public class SoforSiparisAdapter extends RecyclerView.Adapter<SoforSiparisAdapter.MyHolder> {


    private Context context;
    private List<SiparisGet> siparisGetList;
    Activity activity;
    ChangeFragments changeFragments;
    AlertShow alertShow;

    public SoforSiparisAdapter(Context context, List<SiparisGet> siparisGetList, Activity activity) {
        this.context = context;
        this.siparisGetList = siparisGetList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_soforsiparis,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);

        changeFragments = new ChangeFragments(context);
        alertShow = new AlertShow(context);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {

      // siparisList.get(i).getSiparis().size()+ " Adet"

        if(siparisGetList.get(i).getSiparis_durum() == 4)  //iptal eedilen
            myHolder.img_product.setImageResource(R.drawable.productcancel);
        else if (siparisGetList.get(i).getSiparis_durum() == 1) //alınan siparis
            myHolder.img_product.setImageResource(R.drawable.product);
        else if(siparisGetList.get(i).getSiparis_durum() == 2) //teslim edilen siparis
            myHolder.img_product.setImageResource(R.drawable.productok);


        myHolder.sofor_textViewsiparisAdSoyad.setText(siparisGetList.get(i).getMusteri_adi().toUpperCase()
                                                        +" "+siparisGetList.get(i).getMusteri_soyadi().toUpperCase());
        myHolder.sofor_textViewsiparisAdres.setText(siparisGetList.get(i).getMusteri_adres1());
        myHolder.sofor_textViewsiparisTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CallPhone( siparisGetList.get(i).getMusteri_tel1());
            }
        });
        myHolder.sofor_textViewsiparisMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HaritaMapsActivity.class);
                context.startActivity(intent);
            }
        });

        myHolder.cardview_item_soforsiparis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                StringBuilder bt = new StringBuilder();
                for (StokBilgiGet b : siparisGetList.get(i).getSiparis())
                {
                    bt.append(b.getUrun_adi().toUpperCase()+"-"+b.getAlturun_adi().toLowerCase()+" = "+b.getStok()+"\n");
                }

               // alertShow.AlertDialogSiparisDetay(siparisGetList.get(i),bt.toString());
                        //Toast.makeText(context,"tiklama"+siparisGetList.get(i).getMusteri_soyadi(),Toast.LENGTH_SHORT).show();


                return true;
            }
        });
    }

    private void CallPhone(String numara)
    {
        //izin alınacak
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+numara));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return  siparisGetList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView sofor_textViewsiparisAdres,sofor_textViewsiparisAdSoyad;
        ImageView sofor_textViewsiparisTelefon,sofor_textViewsiparisMap,img_product;
        CardView cardview_item_soforsiparis;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sofor_textViewsiparisAdSoyad = itemView.findViewById(R.id.sofor_textViewsiparisAdSoyad);
            sofor_textViewsiparisAdres = itemView.findViewById(R.id.sofor_textViewsiparisAdres);
            sofor_textViewsiparisTelefon = itemView.findViewById(R.id.sofor_textViewsiparisTelefon);
            cardview_item_soforsiparis = itemView.findViewById(R.id.cardview_item_soforsiparis);
            sofor_textViewsiparisMap = itemView.findViewById(R.id.sofor_textViewsiparisMap);
            img_product = itemView.findViewById(R.id.img_product);

        }
    }//class
}
