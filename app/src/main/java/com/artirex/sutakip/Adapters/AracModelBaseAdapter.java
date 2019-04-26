package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artirex.sutakip.Model.AracModel;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;

import java.util.List;

public class AracModelBaseAdapter extends BaseAdapter {


    List<AracModel> aracModelList;
    Context context;


    public AracModelBaseAdapter(List<AracModel> aracModelList, Context context) {
        this.aracModelList = aracModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return aracModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return aracModelList.get(position);
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
            convertView =  layoutInflater.from(context).inflate(R.layout.spinner_aracmodel_layout,parent,false);
        }
        TextView tv_aracModel = convertView.findViewById(R.id.tv_aracModel);
        AracModel aracModel = (AracModel)getItem(position);

        if(aracModel != null){
            tv_aracModel.setText(aracModel.getModel_adi().toUpperCase());
        }
        return convertView;
    }
}
