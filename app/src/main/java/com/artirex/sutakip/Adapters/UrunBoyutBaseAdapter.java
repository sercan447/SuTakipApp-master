package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.R;

import java.util.List;

public class UrunBoyutBaseAdapter extends BaseAdapter {

    List<AltUrun> altUrunList;
    Context context;

    public UrunBoyutBaseAdapter(List<AltUrun> altUrunList, Context context) {
        this.altUrunList = altUrunList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return altUrunList.size();
    }

    @Override
    public Object getItem(int position) {
        return altUrunList.get(position);
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
            convertView = layoutInflater.from(context).inflate(R.layout.spinner_urunboyut_layout,parent,false);
        }
        TextView tv_urunBoyut = convertView.findViewById(R.id.tv_urunBoyut);
        AltUrun altUrun = (AltUrun)getItem(position);

        if(altUrun != null)
        {
            tv_urunBoyut.setText(altUrun.getAlturun_adi());
        }

        return convertView;
    }
}
