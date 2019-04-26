package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.AracModel;
import com.artirex.sutakip.R;

import java.util.List;

public class AracBaseAdapter extends BaseAdapter {

    private List<AracGet> aracList;
    private Context context;

    public AracBaseAdapter(List<AracGet> aracList, Context context) {
        this.aracList = aracList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return aracList.size();
    }

    @Override
    public Object getItem(int position) {
        return aracList.get(position);
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
            convertView = layoutInflater.from(context).inflate(R.layout.spinner_arac_layout,parent,false);
        }
        TextView tv_arac = convertView.findViewById(R.id.tv_arac);

        AracGet arac = (AracGet) getItem(position);

        if(arac != null)
        {
            tv_arac.setText(arac.getArac_plaka());
        }
        return convertView;
    }
}
