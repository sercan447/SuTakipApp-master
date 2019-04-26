package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.KullaniciYetki;
import com.artirex.sutakip.R;

import java.util.List;

public class YetkiBaseAdapter extends BaseAdapter {

    private List<KullaniciYetki> yetkiList;
    private Context context;

    public YetkiBaseAdapter(List<KullaniciYetki> yetkiList, Context context) {
        this.yetkiList = yetkiList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return yetkiList.size();
    }

    @Override
    public Object getItem(int position) {
        return yetkiList.get(position);
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
            convertView = layoutInflater.from(context).inflate(R.layout.spinner_yetki_layout,parent,false);
        }
        TextView tv_arac = convertView.findViewById(R.id.tv_yetki);

        KullaniciYetki yetki = (KullaniciYetki) getItem(position);

        if(yetki != null)
        {
            tv_arac.setText(yetki.getYetki());
        }
        return convertView;
    }
}
