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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleProductFragment extends Fragment {

    //satılan ürünler toplamda ne kadar olduğu
    //ve en çok hangi ürünün satıldığı.

    View view;
    List<ToplamSatilanUrun> list = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();
    BarChart barchart;

    private EditText dpckr_saleprd_basliangictarihi,dpckr_saleprd_bitistarihi;
    private Spinner spinner_siparisDurum;
    private Button btn_saleprd_stokgetir;

    TextView tv_raporlama_text;
    StringBuilder stringBuilder = new StringBuilder();


    String secilenTarih = "";
    int siparisDurumu = -1;
    public SaleProductFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_sale_product, container, false);

        barchart = (BarChart)view.findViewById(R.id.barchart1);
        tv_raporlama_text = view.findViewById(R.id.tv_raporlama_text);

        dpckr_saleprd_basliangictarihi = view.findViewById(R.id.dpckr_saleprd_basliangictarihi);
        dpckr_saleprd_bitistarihi = view.findViewById(R.id.dpckr_saleprd_bitistarihi);
        spinner_siparisDurum = view.findViewById(R.id.spinner_siparisDurum);
        btn_saleprd_stokgetir = view.findViewById(R.id.btn_saleprd_stokgetir);

        toplamSatilanUrun(2,"","");

            ClickState();

        return view;

    }

    public void toplamSatilanUrun(int siparisDurum,String baslangicTarihi,String bitisTarihi)
    {
        Call<List<ToplamSatilanUrun>> st = ManagerALL.getInstance().toplamSatilanUrun(siparisDurum,baslangicTarihi,bitisTarihi);
        st.enqueue(new Callback<List<ToplamSatilanUrun>>() {
            @Override
            public void onResponse(Call<List<ToplamSatilanUrun>> call, Response<List<ToplamSatilanUrun>> response) {

                if(response.isSuccessful()) {
                    list = response.body();
                    Log.e("RAPORLAMA :", response.body().toString());
                    Log.e("RAPORLAMA SIZE :", "" + list.size());
                    getChart(list.size(), list);

                    RaporlamaMetni(list);
                }
            }

            @Override
            public void onFailure(Call<List<ToplamSatilanUrun>> call, Throwable t) {

            }
        });
    }//func

    private void ClickState()
    {
        dpckr_saleprd_basliangictarihi.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_saleprd_basliangictarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Başlangıc Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        dpckr_saleprd_bitistarihi.setOnClickListener(new View.OnClickListener() {
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
                        dpckr_saleprd_bitistarihi.setText(secilenTarih);
                    }
                },yil,ay,gun);

                pickerDialog.setTitle("Stok Bitiş Tarihi Seçiniz");
                pickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Seç",pickerDialog);
                pickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",pickerDialog);
                pickerDialog.show();
            }
        });

        btn_saleprd_stokgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dpckr_saleprd_basliangictarihi.getText().toString().trim()) && !TextUtils.isEmpty(dpckr_saleprd_bitistarihi.getText().toString().trim()))
                {
                    Log.e("TARIHE GORE ","getırılıyor");
                    String bas_tarih =dpckr_saleprd_basliangictarihi.getText().toString();
                    String bit_tarih = dpckr_saleprd_bitistarihi.getText().toString();

                    toplamSatilanUrun(siparisDurumu,bas_tarih,bit_tarih);
                }else
                {
                    Snackbar.make(view.findViewById(R.id.layout_saleactive),"Tarih Aralığı Seçmelisiniz.",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        spinner_siparisDurum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String durum = (String)parent.getSelectedItem();
                if(durum.equals("Sipariş"))
                    siparisDurumu = 2;
                else if(durum.equals("Teslimat"))
                    siparisDurumu = 1;
                else if(durum.equals("İptal"))
                    siparisDurumu = 4;
                Toast.makeText(getContext(),""+durum+"-"+siparisDurumu,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//FUNC
    private void RaporlamaMetni(List<ToplamSatilanUrun> liste)
    {
        stringBuilder.setLength(0);
        for(int i=0;i<liste.size(); i++)
        {
            stringBuilder.append(liste.get(i).getUrun_adi().toUpperCase()+" / "+liste.get(i).getAlturun_adi().toLowerCase() +" = "+liste.get(i).getToplamSatis()+" Adet \n\n");

        }
        tv_raporlama_text.setText(stringBuilder.toString());
    }

    public void getChart(int size,List<ToplamSatilanUrun> listem)
    {
        Log.e("SIZEM : ",""+list.size());

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=0; i<size; i++)
        {
            barEntries.add(new BarEntry(i,listem.get(i).getToplamSatis()));
            Log.e("VERI :",""+i);
        }

        ArrayList<String> labels = new ArrayList<>();
        for (int i=0; i<size; i++)
        {
            labels.add(listem.get(i).getUrun_adi()+" / "+ listem.get(i).getAlturun_adi());
            Log.e("ISIM :","veri"+i);
        }

        BarDataSet dataSet = new BarDataSet(barEntries,"Raporlama Bilgileri 2");

        dataSet.setValueTextSize(15f);

        dataSet.setHighlightEnabled(true);

        //FLOAT TİPTE GOSTERILEN DATALARI INT TIPINE CAST ETTIM
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+((int)value);
            }
        });
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //  colors = dataSet.getValueColors();
        dataSet.setValueTextColor(Color.BLACK);
        // XAxis axis = barchart.getXAxis();
        //  axis.setLabelCount(listem.size(),true);

        dataSet.setDrawValues(true);
        barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        dataSet.notifyDataSetChanged();

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        barchart.setData(data);
        String[] t = (String[])labels.toArray();


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
