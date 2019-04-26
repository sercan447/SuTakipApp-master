package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Fragments.MusteriEkleFragment;
import com.artirex.sutakip.Fragments.MusteriyeAitSiparisFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.ILoadMore;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.R;

import java.util.List;

public class MusteriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0, VIEW_TYPE_LOADING=1;
    ILoadMore loadMore;
    boolean isLoading;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;



    private Context context;
    private List<Musteri> musteriList;
    Activity activity;
    ChangeFragments changeFragments;

    public MusteriAdapter(RecyclerView recyclerView,Context context, List<Musteri> musteriList, Activity activity) {
        this.context = context;
        this.musteriList = musteriList;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();

       /*
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount =  linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                {
                    if(loadMore != null)
                    {
                        loadMore.onLoadMore();
                    }
                    isLoading = true;
                }

            }
        });
        */

    }
    public MusteriAdapter(Context context, List<Musteri> musteriList, Activity activity) {
        this.context = context;
        this.musteriList = musteriList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        changeFragments = new ChangeFragments(context);

        if(i == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_musteriler,viewGroup,false);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }else if(i == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading,viewGroup,false);
            LoadingViewHolder loadingViewHolder = new LoadingViewHolder(view);

            return loadingViewHolder;
        }
    return null;
    }


    @Override
    public int getItemViewType(int position) {
        return musteriList.get(position) == null ? VIEW_TYPE_LOADING  : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {

        if(holder instanceof MyHolder)
        {

            final MyHolder myHolder = (MyHolder)holder;

            myHolder.cardview_item_musteriler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MusteriEkleFragment musteriEkleFragment = new MusteriEkleFragment();

                    //Musteri Id bilgisini musteriekle sayfasına gonderiyorum.
                    //guncelleme için
                    Bundle bundle = new Bundle();
                    bundle.putInt("musteriId", musteriList.get(i).getMusteri_id());
                    musteriEkleFragment.setArguments(bundle);

                    ChangeFragments changeFragments = new ChangeFragments(context);
                    if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                        changeFragments.change(musteriEkleFragment, "musteriEkleFragmentTAG");
                    } else {
                        changeFragments.changeNavbar(musteriEkleFragment, "musteriEkleFragmentTAG", null);
                    }

                }
            });

            myHolder.musteri_textviewAdiSoyadi.setText(musteriList.get(i).getMusteri_adi().toUpperCase() + " " +
                    musteriList.get(i).getMusteri_soyadi().toUpperCase());
            myHolder.musteri_textViewTarih.setText(musteriList.get(i).getMusteri_kayitTarihi());
            myHolder.musteri_textViewAdres.setText(musteriList.get(i).getMusteri_adres1());

            if (musteriList.get(i).getMusteri_cinsiyet() == 1) {
                myHolder.musteri_imageViewProfile.setImageResource(R.drawable.women);
            } else {
                myHolder.musteri_imageViewProfile.setImageResource(R.drawable.man);
            }

            myHolder.musteri_imageViewArama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallPhone(musteriList.get(i).getMusteri_tel1());
                    Toast.makeText(context, musteriList.get(i).getMusteri_tel1(), Toast.LENGTH_SHORT).show();
                }
            });

            myHolder.musteri_aitsiparisler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popupMenu = new PopupMenu(context, myHolder.musteri_aitsiparisler);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_musteri_siparis_popup, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int itemId = item.getItemId();

                            switch (itemId) {
                                case R.id.menu_musteri_aitsiparis:

                                    MusteriyeAitSiparisFragment musteriyeAitSiparisFragment = new MusteriyeAitSiparisFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("musteriId", musteriList.get(i).getMusteri_id());

                                    musteriyeAitSiparisFragment.setArguments(bundle);

                                    changeFragments.change(musteriyeAitSiparisFragment, "musteriyeAitSiparisFragmentTAG");
                                    Log.e("MUSTERI adapter ", "" + musteriList.get(i).getMusteri_id());

                                    return true;
                                default:
                                    return false;
                            }//switch
                        }
                    });
                    popupMenu.show();
                }
            });
        }else if(holder instanceof LoadingViewHolder)
        {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    public void setLoaded() {
        isLoading = false;
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
        return  musteriList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView musteri_textviewAdiSoyadi,musteri_textViewTarih,musteri_textViewAdres;
        ImageView musteri_imageViewProfile,musteri_imageViewArama,musteri_aitsiparisler;
        CardView cardview_item_musteriler;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            cardview_item_musteriler = itemView.findViewById(R.id.cardview_item_musteriler);
            musteri_textviewAdiSoyadi = itemView.findViewById(R.id.musteri_textviewAdiSoyadi);
            musteri_textViewTarih = itemView.findViewById(R.id.musteri_textViewTarih);
            musteri_imageViewProfile = itemView.findViewById(R.id.musteri_imageViewProfile);
            musteri_textViewAdres = itemView.findViewById(R.id.musteri_textViewAdres);
            musteri_imageViewArama = itemView.findViewById(R.id.musteri_imageViewArama);
            musteri_aitsiparisler = itemView.findViewById(R.id.musteri_aitsiparisler);
        }
    }//class

    public class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView)
        {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
        }

    }//CLASS
}
