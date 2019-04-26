package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UrunEkleFragment extends Fragment {

    View view;
    LinearLayout linearlayout_urunEkle;
    TextInputLayout til_urunAdi,til_urunTipi,til_urunBoyutu,til_urunBilgi,til_urunstok;
    TextInputEditText ed_urunAdi,ed_urunTipi,ed_urunBoyutu,ed_urunBilgi,ed_urunstok;
    Button btn_urunKayit;

    private ChangeFragments changeFragments;
    private InternetManager internetManager;
    private AlertShow alertShow;

    private int urunId = -1;
    private int alturunId = -1;
    Bundle bundle;

    public UrunEkleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_urun_ekle, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ürün Ekle Modülü");

        ViewComponentsInitialize();


        if(getArguments() != null)
        {
            bundle = getArguments();
            urunId = bundle.getInt("urunId",-1);
            alturunId = bundle.getInt("alturunId",-1);
        }
        if(urunId != -1 && alturunId != -1)
        {
            getProduct(urunId,alturunId);
        }
        ButtonInsert();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void ViewComponentsInitialize()
    {
        linearlayout_urunEkle = view.findViewById(R.id.linearlayout_urunEkle);
        changeFragments = new ChangeFragments(getContext());
        internetManager = new InternetManager(getContext());
        alertShow = new AlertShow(getContext());

        til_urunAdi = view.findViewById(R.id.til_urunAdi);
        til_urunTipi = view.findViewById(R.id.til_urunTipi);
        til_urunBoyutu = view.findViewById(R.id.til_urunBoyutu);
        til_urunstok = view.findViewById(R.id.til_urunstok);
        til_urunBilgi = view.findViewById(R.id.til_urunBilgi);


        ed_urunAdi = view.findViewById(R.id.ed_urunAdi);
        ed_urunTipi = view.findViewById(R.id.ed_urunTipi);
        ed_urunBoyutu = view.findViewById(R.id.ed_urunBoyutu);
        ed_urunstok = view.findViewById(R.id.ed_urunstok);
        ed_urunBilgi = view.findViewById(R.id.ed_urunBilgi);

        btn_urunKayit = view.findViewById(R.id.btn_urunKayit);

    }

    private void ButtonInsert() {

        btn_urunKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internetManager.InternetIsConnected())
                {
                    AracViewValidata();
                }else
                {
                    Snackbar.make(linearlayout_urunEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }//FUNC
    private void AracViewValidata()
    {
        ed_urunAdi.setError(null);
        ed_urunTipi.setError(null);
        ed_urunBoyutu.setError(null);
        ed_urunstok.setError(null);


        boolean durumUrunadi = TextUtils.isEmpty(ed_urunAdi.getText());
        boolean durumUruntipi = TextUtils.isEmpty(ed_urunTipi.getText());
        boolean durumUrunboyut = TextUtils.isEmpty(ed_urunBoyutu.getText());
        boolean durumStok = TextUtils.isEmpty(ed_urunstok.getText());

        if(durumUrunadi || durumUruntipi || durumUrunboyut || durumStok)
        {
            if(durumUrunadi)
                ed_urunAdi.setError("Ürün alanı boş.");

            if(durumUruntipi)
                ed_urunTipi.setError("Tip alanı boş.");

            if(durumUrunboyut)
                ed_urunBoyutu.setError("Boyut alanı boş.");

            if(durumStok)
                ed_urunstok.setError("Stok alanı boş");

        }else
        {
            Urun urun = new Urun();
            urun.setUrun_adi(ed_urunAdi.getText().toString().trim().toLowerCase());
            urun.setUrun_tipi(ed_urunTipi.getText().toString().trim().toLowerCase());
            urun.setUrun_boyutu(ed_urunBoyutu.getText().toString().trim().toLowerCase());
            urun.setUrun_bilgi(ed_urunBilgi.getText().toString().trim().toLowerCase());
            urun.setStok(Integer.valueOf(ed_urunstok.getText().toString()));
            urun.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

            if(urunId != -1 && alturunId != -1)
            {
                UrunGuncelleme(urunId,alturunId,urun.getUrun_adi(),urun.getUrun_tipi(),urun.getUrun_boyutu(),urun.getStok(),urun.getUrun_bilgi(),urun.getEkleyenId());
            }else
            {
                UrunKayit(urun.getUrun_adi(),urun.getUrun_tipi(),urun.getUrun_boyutu(),urun.getStok(),urun.getUrun_bilgi(),urun.getEkleyenId());
            }


        }

    }//FUNC
    private void UrunKayit( String urun_adi,String urun_tip,String urun_boyut,int stok,String urun_bilgi,int ekleyenId)
    {
        Call<Result> request = ManagerALL.getInstance().productInsert(urun_adi,urun_tip,urun_boyut,stok,urun_bilgi, ekleyenId);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("URUN KAYIT SUCCES:",response.toString());
                    Log.e("UURUN KAYIT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("URUN KAYIT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("URUN KAYIT HATA :",t.getLocalizedMessage());
                Log.e("URUN KAYIT HATA2 :",t.toString());
            }
        });
    }//funcs

    private void UrunGuncelleme(int urun_id,int alturun_id, String urun_adi,String urun_tip,String urun_boyut,int stok,String urun_bilgi,int ekleyenId)
    {
        Call<Result> request = ManagerALL.getInstance().productUpdate(urun_id,alturun_id,urun_adi,urun_tip,urun_boyut,stok,urun_bilgi, ekleyenId);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("URUN UPDT SUCCES:",response.toString());
                    Log.e("UURUN UPDT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("URUN UPDT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("URUN UPDT HATA :",t.getLocalizedMessage());
                Log.e("URUN UPDT HATA2 :",t.toString());
            }
        });
    }//funcs

    private void getProduct(int urunId,int alturun_id)
    {
        Call<Urun> req = ManagerALL.getInstance().getProduct(urunId,alturun_id);
        req.enqueue(new Callback<Urun>() {
            @Override
            public void onResponse(Call<Urun> call, Response<Urun> response) {
                if(response.isSuccessful())
                {
                    Urun urun = response.body();

                Log.e("URUN ID",""+urun.getUrun_id());


                    ed_urunAdi.setText(urun.getUrun_adi());
                    ed_urunTipi.setText(urun.getUrun_tipi());
                    ed_urunBoyutu.setText(urun.getUrun_boyutu());
                    ed_urunstok.setText(""+urun.getStok());
                    ed_urunBilgi.setText(urun.getUrun_bilgi());
                }else
                {
                    Log.e("URUN GET ID else : ",response.toString());
                }
            }
            @Override
            public void onFailure(Call<Urun> call, Throwable t) {
            Log.e("URUN GET ID FAIL : ",t.getLocalizedMessage());
            }
        });
    }
}
