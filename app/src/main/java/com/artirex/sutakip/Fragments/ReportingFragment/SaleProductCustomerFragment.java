package com.artirex.sutakip.Fragments.ReportingFragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Model.ToplamMusteriSiparis;
import com.artirex.sutakip.Model.ToplamSatilanUrun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleProductCustomerFragment extends Fragment {
    View view;


    List<ToplamMusteriSiparis> list = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();
    BarChart barchart;

    private EditText dpckr_salecus_basliangictarihi,dpckr_salecus_bitistarihi,ed_sprs_adet;
    private Button btn_salecus_stokgetir;

    TextView tv_raporlama_text;
    StringBuilder stringBuilder = new StringBuilder();


    String secilenTarih = "";

    public SaleProductCustomerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sale_product_customer, container, false);

        barchart = (BarChart)view.findViewById(R.id.barchart2);
      //  tv_raporlama_text = view.findViewById(R.id.tv_raporlama_text);

        dpckr_salecus_basliangictarihi = view.findViewById(R.id.dpckr_salecus_basliangictarihi);
        dpckr_salecus_bitistarihi = view.findViewById(R.id.dpckr_salecus_bitistarihi);
        ed_sprs_adet = view.findViewById(R.id.ed_sprs_adet);
        btn_salecus_stokgetir = view.findViewById(R.id.btn_salecus_stokgetir);

        getToplamMusteriSiparisi(0,"","");


        ClickState();
        return view;
    }
    private void ClickState()
    {


        dpckr_salecus_basliangictarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int yil = c.get(Calendar.YEAR);
                int ay = c.get(Calendar.MONTH);
                int gun = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog;
                pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Log.e("SECILEN ILK TARIH",""+year+"-"+month+"-"+dayOfMonth);
                        secilenTarih = year+"-"+(month+1)+"-"+dayOfMonth+" 00:00:00";
                        dpckr_salecus_basliangictarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_salecus_bitistarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int yil = c.get(Calendar.YEAR);
                int ay = c.get(Calendar.MONTH);
                int gun = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog;
                pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Log.e("SECILEN SON TARIH",""+year+"-"+month+"-"+dayOfMonth);
                        secilenTarih = year+"-"+(month+1)+"-"+dayOfMonth+" 23:59:59";
                        dpckr_salecus_bitistarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_salecus_stokgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_salecus_basliangictarihi.getText().toString().trim()) &&
                        !TextUtils.isEmpty(dpckr_salecus_bitistarihi.getText().toString().trim()) && !TextUtils.isEmpty(ed_sprs_adet.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                    String bas_tarih =dpckr_salecus_basliangictarihi.getText().toString();
                    String bit_tarih = dpckr_salecus_bitistarihi.getText().toString();
                    int siparisAdeti = Integer.valueOf(ed_sprs_adet.getText().toString());
                    getToplamMusteriSiparisi(siparisAdeti,bas_tarih,bit_tarih);
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_saleactive),"Tarih Aralığı ve Sipariş adeti girmelisiniz    .",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }//FUNC

    public void getToplamMusteriSiparisi(int siparis_adeti,String baslangicTarihi,String bitisTarihi)
    {
        Call<List<ToplamMusteriSiparis>> st = ManagerALL.getInstance().getToplamMusteriSiparisi(siparis_adeti,baslangicTarihi,bitisTarihi);
        st.enqueue(new Callback<List<ToplamMusteriSiparis>>() {
            @Override
            public void onResponse(Call<List<ToplamMusteriSiparis>> call, Response<List<ToplamMusteriSiparis>> response) {

                if(response.isSuccessful()) {
                    list = response.body();
                    Log.e("RAPORLAMA :", response.body().toString());
                    Log.e("RAPORLAMA SIZE :", "" + list.size());
                    getChart(list.size(), list);
                }
            }

            @Override
            public void onFailure(Call<List<ToplamMusteriSiparis>> call, Throwable t) {

            }
        });
    }//func

    public void getChart(int size,List<ToplamMusteriSiparis> listem)
    {
        Log.e("SIZEM : ",""+list.size());

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=0; i<size; i++)
        {

            barEntries.add(new BarEntry(i,listem.get(i).getSiparis_adeti()));
            Log.e("VERI :",""+i);
        }

        ArrayList<String> labels = new ArrayList<>();
        for (int i=0; i<size; i++)
        {
            String adSoyad = listem.get(i).getMusteri_adi()+" "+listem.get(i).getMusteri_soyadi();
            labels.add(adSoyad);
            Log.e("ISIM :","veri"+i);
        }

        BarDataSet dataSet = new BarDataSet(barEntries,"Raporlama Bilgileri 2");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //  colors = dataSet.getValueColors();
        dataSet.setValueTextColor(Color.BLACK);
        // XAxis axis = barchart.getXAxis();
        //  axis.setLabelCount(listem.size(),true);

        dataSet.setDrawValues(true);
        barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        dataSet.notifyDataSetChanged();

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+((int)value);
            }
        });

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        barchart.setData(data);
        String[] t = (String[])labels.toArray();

        barchart.setDoubleTapToZoomEnabled(false);
        barchart.setDrawBorders(true);
        barchart.setDrawGridBackground(true); //gri yapıyor uzayı
        barchart.animateY(500);
        barchart.setDrawBorders(true);
        barchart.setFocusable(true);
        barchart.getDescription().setEnabled(false);
        barchart.setFitBars(true);
        barchart.invalidate();

    }//FUNC

}
