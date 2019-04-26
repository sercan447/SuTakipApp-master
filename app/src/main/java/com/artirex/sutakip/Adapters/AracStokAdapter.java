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
import android.widget.Toast;

import com.artirex.sutakip.Fragments.AracStokGunlukFragment;
import com.artirex.sutakip.Fragments.SiparisEkleFragment;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.TelefonArama;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AracStokAdapter extends RecyclerView.Adapter<AracStokAdapter.MyHolder> {

    private final int LISTE_VIEW = 1;
    private final int TABLE_VIEW = 2;
    private final int DIFFERENT_VIEW = 3;


    private Context context;
    private List<AracStok> aracStokList;
    Activity activity;
    private AlertShow alertShow;
    private TelefonArama telefonArama;
    private ChangeFragments changeFragments;

    public AracStokAdapter(Context context, List<AracStok> aracStokList, Activity activity) {
        this.context = context;
        this.aracStokList = aracStokList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_aracstok,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        alertShow = new AlertShow(context);
        telefonArama = new TelefonArama(context);
        changeFragments = new ChangeFragments(context);

        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {

        myHolder.aracstok_km.setText(aracStokList.get(i).getKilometre()+" Kilometre ");
        myHolder.aracstok_yakit.setText(aracStokList.get(i).getYakit() + " Litre");
        myHolder.aracstok_eklemetarihi.setText(aracStokList.get(i).getEklenme_tarihi());

            myHolder.cardview_item_aracstok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AracStokGunlukFragment aracStokGunlukFragment = new AracStokGunlukFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("stokId",aracStokList.get(i).getStok_id());

                    aracStokGunlukFragment.setArguments(bundle);

                    changeFragments.change(aracStokGunlukFragment,"AracStokGunlukFragmentTAG");

                }
            });
    }

    @Override
    public int getItemCount() {
        return  aracStokList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView aracstok_km,aracstok_yakit,aracstok_eklemetarihi;
        CardView cardview_item_aracstok;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            aracstok_km = itemView.findViewById(R.id.aracstok_km);
            aracstok_yakit = itemView.findViewById(R.id.aracstok_yakit);
            aracstok_eklemetarihi = itemView.findViewById(R.id.aracstok_eklemetarihi);

            cardview_item_aracstok = itemView.findViewById(R.id.cardview_item_aracstok);

        }
    }//class
}
