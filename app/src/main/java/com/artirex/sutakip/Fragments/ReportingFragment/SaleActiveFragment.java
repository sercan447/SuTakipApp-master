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

import com.artirex.sutakip.Model.ToplamOdemeTuru;
import com.artirex.sutakip.Model.ToplamSatilanUrun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleActiveFragment extends Fragment {

    private View view;
    PieChart pieChart;
    List<ToplamOdemeTuru> list = new ArrayList<>();

    private EditText dpckr_report_basliangictarihi,dpckr_report_bitistarihi;
    private Button btn_report_stokgetir;

    private String secilenTarih = "";

    public SaleActiveFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sale_active, container, false);
        pieChart = (PieChart) view.findViewById(R.id.piechart1);

        dpckr_report_basliangictarihi = view.findViewById(R.id.dpckr_report_basliangictarihi);
        dpckr_report_bitistarihi = view.findViewById(R.id.dpckr_report_bitistarihi);
        btn_report_stokgetir = view.findViewById(R.id.btn_report_stokgetir);


        gettoplamOdemeTuru("","");

        dpckr_report_basliangictarihi.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_report_basliangictarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_report_bitistarihi.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_report_bitistarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_report_stokgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_report_basliangictarihi.getText().toString().trim()) && !TextUtils.isEmpty(dpckr_report_bitistarihi.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                    String bas_tarih =dpckr_report_basliangictarihi.getText().toString();
                    String bit_tarih = dpckr_report_bitistarihi.getText().toString();

                    gettoplamOdemeTuru(bas_tarih,bit_tarih);
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_saleactive),"Tarih Aralığı Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    public void gettoplamOdemeTuru(String baslangic_tarihi,String bitis_tarihi)
    {
        Call<List<ToplamOdemeTuru>> st = ManagerALL.getInstance().gettoplamOdemeTuru(baslangic_tarihi,bitis_tarihi);
        st.enqueue(new Callback<List<ToplamOdemeTuru>>() {
            @Override
            public void onResponse(Call<List<ToplamOdemeTuru>> call, Response<List<ToplamOdemeTuru>> response) {

                if(response.isSuccessful()) {
                    list = response.body();
                    Log.e("RAPORLAMA :", response.body().toString());
                    Log.e("RAPORLAMA SIZE :", "" + list.size());
                    pieChart.clear();
                    getPieChart(list.size(), list);

                    //RaporlamaMetni(list);
                }
            }

            @Override
            public void onFailure(Call<List<ToplamOdemeTuru>> call, Throwable t) {

            }
        });
    }//func


    public void getPieChart(int size,List<ToplamOdemeTuru> listem)
    {
        Log.e("SIZEM : ",""+list.size());

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0; i<size; i++)
        {
            pieEntries.add(new PieEntry(listem.get(i).getOdeme_sayisi(),listem.get(i).getOdeme_adi()));
            Log.e("VERI :",""+i);
        }

        pieChart.setUsePercentValues(false);// yuzde olarak verir
        pieChart.setDrawHoleEnabled(true); //tasarımda küçük bir degişiklik yapıyor pasta dilimi yada yuvarlak gibi
        pieChart.setHoleColor(Color.WHITE);
        pieChart.getDescription().setEnabled(true);
        pieChart.setTransparentCircleRadius(61f); //renklerin yogunlugu
        pieChart.setDragDecelerationFrictionCoef(2.95f);



        pieChart.animateY(1000, Easing.EasingOption.EaseInCubic); //anımasyon kullanılarak acılması

        PieDataSet dataSet = new PieDataSet(pieEntries,"Toplam Satış Türleri");
        dataSet.setSliceSpace(15f); //parcaları ayırıyor.
        dataSet.setSelectionShift(20f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawValues(true);

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+((int)value);
            }
        });
        dataSet.notifyDataSetChanged();


        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);


        Description description = new Description();
        description.setText("Teslim Edilmiş siparişlerin ödeme türlerini istatiksel olarak inceleyebilirsiniz.");
       // description.setTextSize(10);

        pieChart.setDescription(description);
        pieChart.setData(data);
        pieChart.setFocusable(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();

    }//FUNC
}
