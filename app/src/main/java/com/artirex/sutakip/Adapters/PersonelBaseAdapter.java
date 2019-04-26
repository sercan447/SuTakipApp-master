package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.R;

import java.util.List;

public class PersonelBaseAdapter extends BaseAdapter {

    private List<Personel> personelList;
    private Context context;

    public PersonelBaseAdapter(List<Personel> personelList, Context context) {
        this.personelList = personelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personelList.size();
    }

    @Override
    public Object getItem(int position) {
        return personelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.from(context).inflate(R.layout.spinner_personel_layout, parent, false);
        }
        TextView tv_personelAdi = convertView.findViewById(R.id.tv_personelAdi);

        Personel personel = (Personel) getItem(position);

        if (personel != null) {
            tv_personelAdi.setText(personel.getAdi() + " " + personel.getSoyadi());
        }
        return convertView;
    }
}
