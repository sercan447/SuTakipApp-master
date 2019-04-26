package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;

import java.util.List;

public class UrunTipiBaseAdapter extends BaseAdapter {


    List<Urun> urunList;
    Context context;


    public UrunTipiBaseAdapter(List<Urun> urunList, Context context) {
        this.urunList = urunList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urunList.size();
    }

    @Override
    public Object getItem(int position) {
        return urunList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  layoutInflater.from(context).inflate(R.layout.spinner_uruntipi_layout,parent,false);
        }
        TextView tvUrunTipi = convertView.findViewById(R.id.tv_urunTipi);
        Urun urun = (Urun)getItem(position);

        if(urun != null){
            tvUrunTipi.setText(urun.getUrun_adi().toUpperCase());
        }
        return convertView;
    }
}
