package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Fragments.AracEkleFragment;
import com.artirex.sutakip.Fragments.AracStokEkleFragment;
import com.artirex.sutakip.Fragments.AracStokFragment;
import com.artirex.sutakip.Fragments.AracaAitSiparisFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.TasarimGorunum;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;

import java.util.List;

public class AracAdapter extends RecyclerView.Adapter<AracAdapter.MyViewHolder> {

    private Context context;
    private List<AracGet> aracList;
    Activity activity;
    private int tasarim;
    ChangeFragments changeFragments;

    public AracAdapter(Context context, List<AracGet> aracList, Activity activity) {
        this.context = context;
        this.aracList = aracList;
        this.activity = activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        changeFragments = new ChangeFragments(context);

        view = LayoutInflater.from(context).inflate(R.layout.item_araclar,viewGroup,false);
        MyViewHolder myHolder = new MyViewHolder(view);
        return myHolder;
       /* switch (i)
        {
            case TasarimGorunum.TASARIM_NORMAL:
                 view = LayoutInflater.from(context).inflate(R.layout.item_araclar,viewGroup,false);
                MyHolderNormal myHolder = new MyHolderNormal(view);
                return myHolder;
            case TasarimGorunum.TASARIM_RASTGELE:
                 view = LayoutInflater.from(context).inflate(R.layout.item_araclar_rastgele,viewGroup,false);
                MyHolderRastgele myHolderRastgele = new MyHolderRastgele(view);
                return myHolderRastgele;

            case TasarimGorunum.TASARIM_TABLE:
                view = LayoutInflater.from(context).inflate(R.layout.item_araclar_table,viewGroup,false);
                MyHolderTable myHolderTable = new MyHolderTable(view);
                return myHolderTable;
        }
        return null;*/

    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {

        viewHolder.arac_textviewAracPlaka.setText(aracList.get(i).getArac_plaka());

        //viewHolder.arac_textviewAracModel.setText(""+aracList.get(i).getArac_model());
        viewHolder.arac_textviewAracTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TELEFON tiklama : ",aracList.get(i).getArac_telefon());
                CallPhone(aracList.get(i).getArac_telefon());
            }
        });

        viewHolder.img_more_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context,viewHolder.img_more_popup);
                popupMenu.getMenuInflater().inflate(R.menu.menu_arac_popup,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        switch (id)
                        {
                            case R.id.menu_araca_ait_bilgiler:
                                AracStokFragment aracStokFragment = new AracStokFragment();

                                Bundle bundle = new Bundle();
                                bundle.putInt("aracId",aracList.get(i).getArac_id());
                                aracStokFragment.setArguments(bundle);

                                changeFragments.change(aracStokFragment,"AracStokFragmentTAG");

                                return true;
                            case R.id.menu_araca_ait_siparisler:
                                AracaAitSiparisFragment aracaAitSiparisFragment = new AracaAitSiparisFragment();

                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("aracId",aracList.get(i).getArac_id());
                                aracaAitSiparisFragment.setArguments(bundle1);

                                changeFragments.change(aracaAitSiparisFragment,"AracaAitSiparisFragmentTag");

                                return true;
                            case R.id.menu_araca_stokEkle:
                                AracStokEkleFragment aracStokEkleFragment = new AracStokEkleFragment();

                                Bundle bundle2 = new Bundle();
                                bundle2.putInt("plakaId",aracList.get(i).getArac_id());
                                bundle2.putString("plakaAdi",aracList.get(i).getArac_plaka());

                                aracStokEkleFragment.setArguments(bundle2);

                                changeFragments.change(aracStokEkleFragment,"AracStokEkleFragmentTAG");
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();

            }
        });
        viewHolder.cardview_item_araclar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context,""+aracList.get(i).getArac_plaka(),Toast.LENGTH_SHORT).show();

                AracEkleFragment ekleFragment = new AracEkleFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("aracId",aracList.get(i).getArac_id());
                ekleFragment.setArguments(bundle);


                if((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(ekleFragment,"ekleFragmentTAG");
                }else
                {
                changeFragments.changeNavbar(ekleFragment,"ekleFragmentTAG",null);
                }



            }
        });
       /* switch (tasarim)
        {
            case TasarimGorunum.TASARIM_NORMAL:
                ((MyHolderNormal)viewHolder).arac_textviewAracPlaka.setText(aracList.get(i).getArac_plaka());
                ((MyHolderNormal)viewHolder).arac_textviewAracTelefon.setText(aracList.get(i).getArac_telefon());
                ((MyHolderNormal)viewHolder).arac_textviewAracModel.setText(aracList.get(i).getArac_model());
                //  myHolder.arac_imageViewProfile.setImageResource(R.drawable.car);
                break;
            case TasarimGorunum.TASARIM_RASTGELE:
                ((MyHolderRastgele)viewHolder).arac_textviewAracPlaka.setText(aracList.get(i).getArac_plaka());
                ((MyHolderRastgele)viewHolder).arac_textviewAracTelefon.setText(aracList.get(i).getArac_telefon());
                ((MyHolderRastgele)viewHolder).arac_textviewAracModel.setText(aracList.get(i).getArac_model());
                //  myHolder.arac_imageViewProfile.setImageResource(R.drawable.car);
                break;
            case TasarimGorunum.TASARIM_TABLE:
                ((MyHolderTable)viewHolder).arac_textviewAracPlaka.setText(aracList.get(i).getArac_plaka());
                ((MyHolderTable)viewHolder).arac_textviewAracTelefon.setText(aracList.get(i).getArac_telefon());
                ((MyHolderTable)viewHolder).arac_textviewAracModel.setText(aracList.get(i).getArac_model());
                //  myHolder.arac_imageViewProfile.setImageResource(R.drawable.car);
                break;
        }
        */
    }
   /* @Override
    public int getItemViewType(int position) {

        switch (tasarim) {
            case 12:
                return TasarimGorunum.TASARIM_NORMAL;
            case 13:
                return TasarimGorunum.TASARIM_RASTGELE;
            case 14:
                return TasarimGorunum.TASARIM_TABLE;
        }
        return -1;
    }
    */

    @Override
    public int getItemCount() {
        return  aracList.size();
    }

    private void CallPhone(String numara)
    {
        //izin alınacak
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+numara));
       context.startActivity(intent);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView arac_textviewAracPlaka,arac_textviewAracModel;
        CardView cardview_item_araclar;
        ImageView arac_imageViewProfile,arac_textviewAracTelefon,img_more_popup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            arac_textviewAracPlaka = itemView.findViewById(R.id.arac_textviewAracPlaka);
            arac_textviewAracTelefon = itemView.findViewById(R.id.arac_textviewAracTelefon);
            arac_textviewAracModel = itemView.findViewById(R.id.arac_textviewAracModel);
            // arac_imageViewProfile = itemView.findViewById(R.id.arac_imageViewProfile);
            cardview_item_araclar = itemView.findViewById(R.id.cardview_item_araclar);
            img_more_popup = itemView.findViewById(R.id.img_more_popup);

        }
    }//class MyHolderTable
   /* public class MyHolderNormal extends RecyclerView.ViewHolder
    {
        TextView arac_textviewAracPlaka,arac_textviewAracTelefon,arac_textviewAracModel;
        ImageView arac_imageViewProfile;

        public MyHolderNormal(@NonNull View itemView) {
            super(itemView);

            arac_textviewAracPlaka = itemView.findViewById(R.id.arac_textviewAracPlaka);
            arac_textviewAracTelefon = itemView.findViewById(R.id.arac_textviewAracTelefon);
            arac_textviewAracModel = itemView.findViewById(R.id.arac_textviewAracModel);
           // arac_imageViewProfile = itemView.findViewById(R.id.arac_imageViewProfile);

        }
    }//class MyHolderNormal
    public class MyHolderRastgele extends RecyclerView.ViewHolder
    {
        TextView arac_textviewAracPlaka,arac_textviewAracTelefon,arac_textviewAracModel;
        ImageView arac_imageViewProfile;

        public MyHolderRastgele(@NonNull View itemView) {
            super(itemView);

            arac_textviewAracPlaka = itemView.findViewById(R.id.arac_textviewAracPlaka);
            arac_textviewAracTelefon = itemView.findViewById(R.id.arac_textviewAracTelefon);
            arac_textviewAracModel = itemView.findViewById(R.id.arac_textviewAracModel);
            // arac_imageViewProfile = itemView.findViewById(R.id.arac_imageViewProfile);

        }
    }//class MyHolderRastgele

    public class MyHolderTable extends RecyclerView.ViewHolder
    {
        TextView arac_textviewAracPlaka,arac_textviewAracTelefon,arac_textviewAracModel;
        ImageView arac_imageViewProfile;

        public MyHolderTable(@NonNull View itemView) {
            super(itemView);

            arac_textviewAracPlaka = itemView.findViewById(R.id.arac_textviewAracPlaka);
            arac_textviewAracTelefon = itemView.findViewById(R.id.arac_textviewAracTelefon);
            arac_textviewAracModel = itemView.findViewById(R.id.arac_textviewAracModel);
            // arac_imageViewProfile = itemView.findViewById(R.id.arac_imageViewProfile);

        }
    }//class MyHolderTable
    */
}
