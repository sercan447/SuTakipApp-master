package com.artirex.sutakip.Fragments.ReportingFragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.artirex.sutakip.Model.ToplamAylikSiparis;
import com.artirex.sutakip.Model.ToplamSatilanUrun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleProductMonthFragment extends Fragment {


    //aylık olarak alınan toplam sipariş sayısı grafigi bilgileri gosteriliyor

    private View view;
    private HorizontalBarChart horizontalBarChart;

    List<ToplamAylikSiparis> list = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    private Spinner spinner_yilsiparis;
    private Button btn_yilsiparis;

    String s="";
    public SaleProductMonthFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_sale_product_month, container, false);

        horizontalBarChart =(HorizontalBarChart) view.findViewById(R.id.horizontal_barchart);
        spinner_yilsiparis = view.findViewById(R.id.spinner_yilsiparis);
        btn_yilsiparis = view.findViewById(R.id.btn_yilsiparis);

        getToplamAylikSiparis("");

        spinner_yilsiparis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s =  (String)parent.getItemAtPosition(position);
                Log.e("SECILEN : ","--"+s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_yilsiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        getToplamAylikSiparis(s);
            }
        });

        return view;
    }


    public void getToplamAylikSiparis(String yil)
    {
        Call<List<ToplamAylikSiparis>> st = ManagerALL.getInstance().getToplamAylikSiparis(yil);
        st.enqueue(new Callback<List<ToplamAylikSiparis>>() {
            @Override
            public void onResponse(Call<List<ToplamAylikSiparis>> call, Response<List<ToplamAylikSiparis>> response) {

                if(response.isSuccessful()) {

                    if(response.body().size() > 0) {
                      horizontalBarChart.clear();


                        list = response.body();
                        Log.e("RAPORLAMA HOR:", response.body().toString());
                        Log.e("RAPORLAMA HOR :", "" + list.size());
                        geHorizontaltChart(list.size(), list);
                    }else{
                        Snackbar.make(view.findViewById(R.id.layout_saleproduct_month),"Bu Tarihe Ait İstatistik bulunamadı.",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ToplamAylikSiparis>> call, Throwable t) {

            }
        });
    }//func


    public void geHorizontaltChart(int listsize,List<ToplamAylikSiparis> listem)
    {
        Log.e("SIZEM : ",""+list.size());

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=0; i<listsize; i++)
        {
            barEntries.add(new BarEntry(i,listem.get(i).getSiparis_sayisi()));
            Log.e("VERI :",""+i+"-"+listem.get(i).getSiparis_sayisi());
        }


        for (int i=0; i<listsize; i++)
        {
            if(listem.get(i).getAy() == 1)
                labels.add("Ocak");
            else if(listem.get(i).getAy() == 2)
                labels.add("Şubar");
            else if(listem.get(i).getAy() == 3)
                labels.add("Mart");
            else if(listem.get(i).getAy() == 4)
                labels.add("Nisan");
            else if(listem.get(i).getAy() == 5)
                labels.add("Mayıs");
            else if(listem.get(i).getAy() == 6)
                labels.add("Haziran");
            else if(listem.get(i).getAy() == 7)
                labels.add("Temmuz");
            else if(listem.get(i).getAy() == 8)
                labels.add("Ağustos");
            else if(listem.get(i).getAy() == 9)
                labels.add("Eylül");
            else if(listem.get(i).getAy() == 10)
                labels.add("Ekim");
            else if(listem.get(i).getAy() == 11)
                labels.add("Kasım");
            else if(listem.get(i).getAy() == 12)
                labels.add("Aralık");

            Log.e("ISIM :","veri"+i);
        }

        BarDataSet dataSet = new BarDataSet(barEntries,"Aylık Alınan Siparişlerin Sayısı");
        dataSet.setValueTextSize(20f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSet.setValueTextColor(Color.BLACK);


        dataSet.setDrawValues(true);
        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        dataSet.notifyDataSetChanged();

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+((int)value);
            }
        });
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        horizontalBarChart.setData(data);
        String[] t = (String[])labels.toArray();

        horizontalBarChart.setDrawBorders(true);
        horizontalBarChart.setDrawGridBackground(true); //gri yapıyor uzayı
        horizontalBarChart.animateY(500);
        horizontalBarChart.setDrawBorders(true);
        horizontalBarChart.setFocusable(true);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.setFitBars(true);
        horizontalBarChart.invalidate();

    }//FUNC



}
