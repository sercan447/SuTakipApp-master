package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artirex.sutakip.Fragments.AracStokGunlukFragment;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.TelefonArama;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.R;

import java.util.List;

public class AracStokGunlukAdapter extends RecyclerView.Adapter<AracStokGunlukAdapter.MyHolder> {

    private final int LISTE_VIEW = 1;
    private final int TABLE_VIEW = 2;
    private final int DIFFERENT_VIEW = 3;


    private Context context;
    private List<StokBilgiShowList> aracStokList;
    Activity activity;
    private AlertShow alertShow;
    private TelefonArama telefonArama;
    private ChangeFragments changeFragments;

    public AracStokGunlukAdapter(Context context, List<StokBilgiShowList> aracStokList, Activity activity) {
        this.context = context;
        this.aracStokList = aracStokList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_aracstokgunluk,viewGroup,false);

        MyHolder myHolder = new MyHolder(view);

        alertShow = new AlertShow(context);
        telefonArama = new TelefonArama(context);
        changeFragments = new ChangeFragments(context);

        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {

            myHolder.aracstokgunluk_uruntipi.setText(aracStokList.get(i).getUrun_adi());
            myHolder.aracstokgunluk_alturunadi.setText(aracStokList.get(i).getAlturun_adi());
            myHolder.aracstokgunluk_stok.setText(""+aracStokList.get(i).getStok()+" Adet");

            myHolder.cardview_item_aracstokgunluk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

    @Override
    public int getItemCount() {
        return  aracStokList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView aracstokgunluk_uruntipi,aracstokgunluk_alturunadi,aracstokgunluk_stok;
        CardView cardview_item_aracstokgunluk;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            aracstokgunluk_uruntipi = itemView.findViewById(R.id.aracstokgunluk_uruntipi);
            aracstokgunluk_alturunadi = itemView.findViewById(R.id.aracstokgunluk_alturunadi);
            aracstokgunluk_stok = itemView.findViewById(R.id.aracstokgunluk_stok);

            cardview_item_aracstokgunluk = itemView.findViewById(R.id.cardview_item_aracstokgunluk);

        }
    }//class
}
