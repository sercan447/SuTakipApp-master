package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.KullaniciYetki;
import com.artirex.sutakip.Model.OdemeTuru;
import com.artirex.sutakip.R;

import java.util.List;

public class OdemeTuruBaseAdapter extends BaseAdapter {

    private List<OdemeTuru> odemeTuruList;
    private Context context;

    public OdemeTuruBaseAdapter(List<OdemeTuru> odemeTuruList, Context context) {
        this.odemeTuruList = odemeTuruList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return odemeTuruList.size();
    }

    @Override
    public Object getItem(int position) {
        return odemeTuruList.get(position);
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
            convertView = layoutInflater.from(context).inflate(R.layout.spinner_odeme_layout,parent,false);
        }
        TextView tv_odeme = convertView.findViewById(R.id.tv_odeme);

        OdemeTuru odemeTuru = (OdemeTuru) getItem(position);

        if(odemeTuru != null)
        {
            tv_odeme.setText(odemeTuru.getOdeme_adi());
        }
        return convertView;
    }
}
