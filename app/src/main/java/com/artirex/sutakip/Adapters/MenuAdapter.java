package com.artirex.sutakip.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artirex.sutakip.Fragments.UrunEkleFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Menu;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyHolder> {


    private Context context;
    private List<Menu> menuList;
    Activity activity;
    ChangeFragments changeFragments;

    public MenuAdapter(Context context, List<Menu> menuList, Activity activity) {
        this.context = context;
        this.menuList = menuList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_menuler,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        changeFragments = new ChangeFragments(context);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {


        //ICON PROBLEM COZULECEK

       // Resources res = activity.getResources();
       // int icon = res.getIdentifier(menuList.get(i).getResimAdi(),"drawable",context.getPackageName());

       // myHolder.img_icon.setImageResource(icon);
        myHolder.tv_menuBaslik.setText(menuList.get(i).getMenu_adi());

        if(!menuList.get(i).getResimAdi().equals(""))
        {
            //veritabanındaki resim isimlerini çekip drawable içerisindeki resimlere eşleyip int tipine dönüştürüyoruz.
            int imageid =context.getResources().getIdentifier(menuList.get(i).getResimAdi().trim(), "drawable", context.getPackageName());
            myHolder.img_icon.setImageResource(imageid);
        }

        myHolder.cardview_menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MenuSecimDataEvent(menuList.get(i).getMenu_id(),menuList.get(i).getMenu_adi()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return  menuList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tv_menuBaslik;
        CardView cardview_menuItem;
        ImageView img_icon;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv_menuBaslik = itemView.findViewById(R.id.tv_menuBaslik);
            cardview_menuItem = itemView.findViewById(R.id.cardview_menuItem);
            img_icon = itemView.findViewById(R.id.img_icon);
        }

    }//class

    public static class MenuSecimDataEvent
    {
        public int id;
        public String menuAdi;

        public MenuSecimDataEvent()
        {

        }

        public MenuSecimDataEvent(int id, String menuAdi) {
            this.id = id;
            this.menuAdi = menuAdi;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMenuAdi() {
            return menuAdi;
        }

        public void setMenuAdi(String menuAdi) {
            this.menuAdi = menuAdi;
        }
    }//class
}
