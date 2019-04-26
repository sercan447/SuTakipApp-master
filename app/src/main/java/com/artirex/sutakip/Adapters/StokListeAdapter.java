package com.artirex.sutakip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class StokListeAdapter extends BaseAdapter {

    private List<StokBilgiShowList> stokBilgiList;
    private Context context;

    public StokListeAdapter(List<StokBilgiShowList> stokBilgiList, Context context) {
        this.stokBilgiList = stokBilgiList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stokBilgiList.size();
    }

    @Override
    public Object getItem(int position) {
        return stokBilgiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.from(context).inflate(R.layout.item_stokliste,parent,false);
        }
        TextView stok_urunAdi = convertView.findViewById(R.id.stok_urunAdi);
        TextView stok_urunTip = convertView.findViewById(R.id.stok_urunTip);
        TextView stok_adet = convertView.findViewById(R.id.stok_adet);
        Button btn_stokSil = convertView.findViewById(R.id.btn_stokSil);


        StokBilgiShowList stokBilgishow = (StokBilgiShowList) getItem(position);

        if(stokBilgishow != null)
        {
            stok_urunAdi.setText(""+stokBilgishow.getUrun_adi());
            stok_urunTip.setText(""+stokBilgishow.getAlturun_adi());
            stok_adet.setText(""+stokBilgishow.getStok());
            btn_stokSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stokBilgiList.remove(position);
                    notifyDataSetChanged();

                    EventBus.getDefault().postSticky(new ListeItemDeleteEvent(position));

                }
            });
        }
        return convertView;
    }


    public class ListeItemDeleteEvent
    {
        private int itemId;
        public ListeItemDeleteEvent(int itemId)
        {

        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }
    }//class
}
