package com.artirex.sutakip.Fragments.AnasayfaTabFragments;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.CagriAdapter;
import com.artirex.sutakip.Adapters.SiparisAdapter;
import com.artirex.sutakip.Model.Cagrilar;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.bea.xml.stream.samples.Parse;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TabDortCagriFragment extends Fragment {

    View view;
    RecyclerView recyclerview_gelenCagrilar;
    LinearLayoutManager linearLayoutManager;
    TextView tv_veriBulunamadi;

    String URL = "https://api.netgsm.com.tr/voice/report/xml";
    HttpURLConnection conn = null;
    java.net.URL url;

    List<Cagrilar> cagrilar = new ArrayList<>();

    public TabDortCagriFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_tab_dort_cagri, container, false);

        ViewComponentInitialize();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void ViewComponentInitialize()
    {
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerview_gelenCagrilar = view.findViewById(R.id.recyclerview_cagrilar);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_gelenCagrilar.setLayoutManager(linearLayoutManager);

        //recyclerview_gelenCagrilar.setRecycledViewPool(new RecyclerView.RecycledViewPool());
       // recyclerview_gelenCagrilar.setHasFixedSize(true);
      //  recyclerview_gelenCagrilar.setItemViewCacheSize(20);
       // recyclerview_gelenCagrilar.setDrawingCacheEnabled(true);

                GetCagrilar();
    }//func

    private void DakikaIleListeie()throws ParseException
    {
        String pattern = "yyyy-dd-MM hh:mm:ss";

        DateFormat df = new SimpleDateFormat(pattern);
        //şuanın tarih zamanını alıyoruz
        String strDate2 = df.format(System.currentTimeMillis());
        Date ft = df.parse(strDate2);

        //System.out.println("format "+df.format(System.currentTimeMillis()));

        Calendar c = Calendar.getInstance();
// set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, ft.getDay());
        c.set(Calendar.MINUTE, ft.getMinutes());
        c.set(Calendar.SECOND, ft.getSeconds());
        c.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        Date today = c.getTime();
// user-specified date which you are testing
// let's say the components come from a form or something
        int year = ft.getYear();
        int month = ft.getMonth();
        int dayOfMonth = ft.getDay();
// reuse the calendar to set user specified date
        c.set(Calendar.HOUR_OF_DAY, ft.getDay());
        c.set(Calendar.MINUTE, ft.getMinutes()-2);
        c.set(Calendar.SECOND, ft.getSeconds());
        c.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        Date dateSpecified = c.getTime();

// test your condition
        if (dateSpecified.before(today)) {
            GetCagrilar();
            Log.e("Date specified [", dateSpecified + "] is before today [" + today + "]");
        } else {
            Log.e("Date specified [", dateSpecified + "] is NOT before today [" + today + "]");
        }
    }//FUNC

    private void GetCagrilar()
    {
        try {

            url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();

            //zorunlu olarak izin gerekiyor
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/xml");
            conn.setRequestProperty("Content-Type", "application/xml");

            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
            String body = "<mainbody>" +
                    "<header>" +
                    "<company>Netgsm</company>" +
                    "<usercode>08503051414</usercode>" +
                    "<password>ARTIREX123</password>" +
                    " <date></date>" +
                    "<direction>4</direction>" +
                    "</header>" +
                    "</mainbody>";
            outStreamWriter.write(body);
            outStreamWriter.flush();
            outStreamWriter.close();
            outStream.close();

            Log.e("DURUM CODE ",""+conn.getResponseCode());
            Log.e("DURUM CODE M ",""+conn.getResponseMessage());

            //OKUMA ISLEMI YAPIIYOR
            String result;
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result2 = bis.read();
            while(result2 != -1) {
                buf.write((byte) result2);
                result2 = bis.read();
            }
            result = buf.toString();
            //  Log.e("SONUC GELIYO",result);

            // Log.e("TAG ","BASARILI");

            CagriParseHtml(result);
        }catch(Exception e)
        {
            Log.e("TAG HATA",""+e.toString());
        } finally {
            conn.disconnect();
        }
    }//func


    private void CagriParseHtml(String mesaj)
    {
        String[] t = mesaj.split("<br/>");
        for(String r: t)
        {
            Log.e("BILGILEN",r);
        }
        DataSplit(t);
    }//func

    //cagrıları iikinci defa split edip arayliste atıyoruz
    private void DataSplit(String[] tr)
    {
        for(int i=0;i<tr.length;i++)
        {
            System.out.println(i+" / "+tr[i]);

            String[] s = tr[i].split(" | ");
            int cagri_id=0;
            String telefonno="";
            String aramaSaati="";
            String aramaTarihi="";
            int aramaTipi = 0;

            for(int w =0; w < s.length;w++)
            {
                if(!s[w].equals("|"))
                {
                    if(w == 0)
                        telefonno = (String)s[w];
                    if(w == 2)
                        aramaTarihi = (String)s[w];
                    if(w == 3)
                        aramaSaati = (String)s[w];
                    if(w == 7)
                        aramaTipi = Integer.valueOf(s[w]);
                }
            }//in for

            cagrilar.add(new Cagrilar(i, telefonno, aramaSaati, aramaTarihi, aramaTipi));
            if(cagrilar.get(0).getTelefon_no().equals("100"))
            {
                return;
            }
        }//for

        CagriAdapter cagriAdapter = new CagriAdapter(getContext(),cagrilar,getActivity());
        recyclerview_gelenCagrilar.setAdapter(cagriAdapter);


        if(cagrilar.size() == 0)
        {
            tv_veriBulunamadi.setVisibility(View.VISIBLE);
        }else
        {
            tv_veriBulunamadi.setVisibility(View.INVISIBLE);
        }

        for(Cagrilar c : cagrilar)
            Log.e("beyhan liste",c.toString());


    }//FUNC

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_anasayfa,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId)
        {
            case R.id.menu_anasayfa_cagri_refresh:
                GetCagrilar();
                return true;
        }

        return true;
    }
}
