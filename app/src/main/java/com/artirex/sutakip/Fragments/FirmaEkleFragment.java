package com.artirex.sutakip.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import com.artirex.sutakip.Model.AracModel;
import com.artirex.sutakip.Model.Firma;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirmaEkleFragment extends Fragment {

  private View view;
  LinearLayout linearlayout_firmaEkle;
  private TextInputLayout til_firmaAdi,til_firmaTelefon,til_firmaBilgi;
  private TextInputEditText ed_firmaAdi,ed_firmaTelefon,ed_firmaBilgi;
  private Button btn_firmaEkle;

  InternetManager internetManager;
  AlertShow alertShow;
  ChangeFragments changeFragments;

    public FirmaEkleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_firma_ekle, container, false);

        ViewComponentsInitialize();
        EventState();

        return view;
    }

    private void ViewComponentsInitialize()
    {
        linearlayout_firmaEkle = view.findViewById(R.id.linearlayout_firmaEkle);
        til_firmaAdi = view.findViewById(R.id.til_firmaAdi);
        til_firmaTelefon = view.findViewById(R.id.til_firmaTelefon);
        til_firmaBilgi = view.findViewById(R.id.til_firmaBilgi);
        ed_firmaAdi = view.findViewById(R.id.ed_firmaAdi);
        ed_firmaTelefon = view.findViewById(R.id.ed_firmaTelefon);
        ed_firmaBilgi = view.findViewById(R.id.ed_firmaBilgi);

        btn_firmaEkle = view.findViewById(R.id.btn_firmaEkle);

        internetManager = new InternetManager(getContext());
        changeFragments = new ChangeFragments(getContext());
        alertShow = new AlertShow(getContext());
    }//func


    private void FirmaKayit(String firma_adi, String firma_bilgi,String firma_tel1,int ekleyenId){
        Call<Result> aracInsert = ManagerALL.getInstance().firmaInsert(firma_adi, firma_bilgi,firma_tel1,ekleyenId);
        aracInsert.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isTf())
                    {
                        Log.e("FIRMA KAYIT : ",response.body().toString());
                        Log.e("FIRMA KAYIT : ",response.toString());
                        alertShow.AlertDialogKutusu("Ekleme Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                    }else
                    {
                        Log.e("FIRMA KAYIT else: ",response.body().toString());
                        Log.e("FIRMA KAYIT else : ",response.toString());
                        alertShow.AlertDialogKutusu("Ekleme Bilgisi",response.body().getMesaj(),R.drawable.ic_call);
                    }
                }else
                {
                    alertShow.AlertDialogKutusu("Ekleme Sorunu",response.body().getMesaj(),R.drawable.ic_call);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.e("FIRMA KAYIT exp: ",t.getLocalizedMessage());

            }
        });
    }//FUNC

    private void EventState()
    {
        btn_firmaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetManager.InternetIsConnected())
                {
                    FirmaViewValidate();
                }else
                {
                    Snackbar.make(linearlayout_firmaEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }//func

    private void FirmaViewValidate()
    {

        ed_firmaAdi.setError(null);
        boolean durumUrunadi = TextUtils.isEmpty(ed_firmaAdi.getText());

        if(durumUrunadi)
        {
            if(durumUrunadi)
                ed_firmaAdi.setError("Firma alanı boş.");

        }else
        {
            Firma firma = new Firma();
            firma.setFirma_adi(ed_firmaAdi.getText().toString().trim());
            firma.setFirma_bilgi(ed_firmaBilgi.getText().toString().trim());
            firma.setFirma_tel1(ed_firmaTelefon.getText().toString().trim());
            firma.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

            FirmaKayit(firma.getFirma_adi(),firma.getFirma_bilgi(),firma.getFirma_tel1(),firma.getEkleyenId());
        }

    }//FUNC
}
