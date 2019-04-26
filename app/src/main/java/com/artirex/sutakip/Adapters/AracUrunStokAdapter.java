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

public class AracUrunStokAdapter extends RecyclerView.Adapter<AracUrunStokAdapter.MyHolder> {


    private Context context;
    private List<StokBilgiShowList> aracStokList;
    Activity activity;
    private AlertShow alertShow;
    private TelefonArama telefonArama;
    private ChangeFragments changeFragments;

    public AracUrunStokAdapter(Context context, List<StokBilgiShowList> aracStokList, Activity activity) {
        this.context = context;
        this.aracStokList = aracStokList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_aracstoktoplam,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        alertShow = new AlertShow(context);
        telefonArama = new TelefonArama(context);
        changeFragments = new ChangeFragments(context);

        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {


        myHolder.stoktoplam_aracplaka.setText(aracStokList.get(i).getArac_adi());
        myHolder.stoktoplam_urunAdi.setText(aracStokList.get(i).getUrun_adi());
        myHolder.stoktoplam_urunTip.setText(aracStokList.get(i).getAlturun_adi());
        myHolder.stoktoplam_adet.setText(aracStokList.get(i).getStok()+" Adet");

           /* myHolder.cardview_item_aracstok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AracStokGunlukFragment aracStokGunlukFragment = new AracStokGunlukFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("stokId",aracStokList.get(i).getStok_id());

                    aracStokGunlukFragment.setArguments(bundle);

                    changeFragments.change(aracStokGunlukFragment,"AracStokGunlukFragmentTAG");

                }
            });
            */
    }

    @Override
    public int getItemCount() {
        return  aracStokList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView stoktoplam_aracplaka,stoktoplam_urunAdi,stoktoplam_urunTip,stoktoplam_adet;
        CardView cardview_item_stoktoplam;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            stoktoplam_aracplaka = itemView.findViewById(R.id.stoktoplam_aracplaka);
            stoktoplam_urunAdi = itemView.findViewById(R.id.stoktoplam_urunAdi);
            stoktoplam_urunTip = itemView.findViewById(R.id.stoktoplam_urunTip);
            stoktoplam_adet = itemView.findViewById(R.id.stoktoplam_adet);

            cardview_item_stoktoplam = itemView.findViewById(R.id.cardview_item_stoktoplam);

        }
    }//class
}
