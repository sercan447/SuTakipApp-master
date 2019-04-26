package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.Model.Firma;
import com.artirex.sutakip.R;

import java.util.List;

public class FirmaBaseAdapter extends BaseAdapter {

    List<Firma> firmaList;
    Context context;

    public FirmaBaseAdapter(List<Firma> firmaList, Context context) {
        this.firmaList = firmaList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return firmaList.size();
    }

    @Override
    public Object getItem(int position) {
        return firmaList.get(position);
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
            convertView = layoutInflater.from(context).inflate(R.layout.spinner_firma_layout,parent,false);
        }
        TextView tv_firma = convertView.findViewById(R.id.tv_firmaAdi);
        Firma firma = (Firma) getItem(position);

        if(firma != null)
        {
            tv_firma.setText(firma.getFirma_adi());
        }

        return convertView;
    }
}
