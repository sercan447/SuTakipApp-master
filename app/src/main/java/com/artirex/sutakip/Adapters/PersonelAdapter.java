package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artirex.sutakip.Fragments.PersonelEkleFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.R;

import java.util.List;

public class PersonelAdapter extends RecyclerView.Adapter<PersonelAdapter.MyHolder> {


    private Context context;
    private List<Personel> personelList;
    Activity activity;
    ChangeFragments changeFragments;


    public PersonelAdapter(Context context, List<Personel> personelList, Activity activity) {
        this.context = context;
        this.personelList = personelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_personeller,viewGroup,false);

        changeFragments = new ChangeFragments(context);

        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {

        myHolder.personel_textviewAdiSoyadi.setText(personelList.get(i).getAdi()+" "+personelList.get(i).getSoyadi());
        myHolder.personel_textViewAdres.setText(personelList.get(i).getKayit_tarihi());
       // myHolder.personel_textViewTarih.setText(personelList.get(i).getBilgi().substring(0,20));
        if(personelList.get(i).getCinsiyet() == 0)
        {
            myHolder.personel_imageViewProfile.setImageResource(R.drawable.man);
        }else {
            myHolder.personel_imageViewProfile.setImageResource(R.drawable.women);
        }

        if(personelList.get(i).getDurum() == 0) {
            myHolder.personel_textViewDurumu.setText("Pasif");
            myHolder.personel_textViewDurumu.setTextColor(Color.RED);
        }else{
            myHolder.personel_textViewDurumu.setText("Aktif");
            myHolder.personel_textViewDurumu.setTextColor(Color.GREEN);

        }
        myHolder.personel_imageViewArama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPhone(personelList.get(i).getTelefon());
            }
        });

        myHolder.cardview_item_personeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersonelEkleFragment personelekleFragment = new PersonelEkleFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("personelId",personelList.get(i).getPersonel_id());

                personelekleFragment.setArguments(bundle);

                if((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(personelekleFragment,"personelEkleFragmentTag");
                }else
                {
                    changeFragments.changeNavbar(personelekleFragment,"personelEkleFragmentTag",null);
                }

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
        return  personelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        CardView cardview_item_personeller;
        TextView personel_textviewAdiSoyadi,personel_textViewAdres,personel_textViewTarih,personel_textViewDurumu;
        ImageView personel_imageViewProfile,personel_imageViewArama;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            cardview_item_personeller = itemView.findViewById(R.id.cardview_item_personeller);
            personel_textviewAdiSoyadi = itemView.findViewById(R.id.personel_textviewAdiSoyadi);
            personel_textViewAdres = itemView.findViewById(R.id.personel_textViewAdres);
            personel_imageViewProfile = itemView.findViewById(R.id.personel_imageViewProfile);
            personel_textViewTarih = itemView.findViewById(R.id.personel_textViewTarih);
            personel_textViewDurumu = itemView.findViewById(R.id.personel_textViewDurumu);
            personel_imageViewArama = itemView.findViewById(R.id.personel_imageViewArama);


        }
    }//class
}
