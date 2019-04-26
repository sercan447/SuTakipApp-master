package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artirex.sutakip.Fragments.UrunEkleFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;

import java.util.List;

public class UrunAdapter extends RecyclerView.Adapter<UrunAdapter.MyHolder> {


    private Context context;
    private List<Urun> urunList;
    Activity activity;
    ChangeFragments changeFragments;

    public UrunAdapter(Context context, List<Urun> urunList, Activity activity) {
        this.context = context;
        this.urunList = urunList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_urunler,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        changeFragments = new ChangeFragments(context);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {

        myHolder.urun_textviewUrunismi.setText(urunList.get(i).getUrun_tipi());
        myHolder.urun_textviewUrunTuru.setText(urunList.get(i).getUrun_boyutu());
        myHolder.urun_textviewUrunKayitTarihi.setText(urunList.get(i).getKayit_tarihi());
        myHolder.urun_textviewUrunStok.setText(""+urunList.get(i).getStok()+" Adet");
        myHolder.cardview_item_urunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UrunEkleFragment urunEkleFragment = new UrunEkleFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("urunId",urunList.get(i).getUrun_id());
                bundle.putInt("alturunId",urunList.get(i).getAlturun_id());
                urunEkleFragment.setArguments(bundle);

                if((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(urunEkleFragment,"urnEkleFragmnetTAG");
                }else
                {
                    changeFragments.changeNavbar(urunEkleFragment,"urnEkleFragmnetTAG",null);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return  urunList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView urun_textviewUrunismi,urun_textviewUrunTuru,urun_textviewUrunStok,urun_textviewUrunKayitTarihi;
        CardView cardview_item_urunler;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            urun_textviewUrunismi = itemView.findViewById(R.id.urun_textviewUrunismi);
            urun_textviewUrunTuru = itemView.findViewById(R.id.urun_textviewUrunTuru);
            urun_textviewUrunKayitTarihi = itemView.findViewById(R.id.urun_textviewUrunKayitTarihi);
            urun_textviewUrunStok = itemView.findViewById(R.id.urun_textviewUrunStok);

            cardview_item_urunler = itemView.findViewById(R.id.cardview_item_urunler);
        }
    }//class
}
