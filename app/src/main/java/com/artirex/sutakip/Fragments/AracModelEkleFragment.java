package com.artirex.sutakip.Fragments;


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
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AracModelEkleFragment extends Fragment {

    private View view;
    private LinearLayout linearlayout_aracModelEkle;
    private TextInputLayout til_aracModelAdi,til_aracModelBilgi;
    private TextInputEditText ed_aracModelAdi,ed_aracModelBilgi;
    private Button btn_aracModeliEkle;

    InternetManager internetManager;
    ChangeFragments changeFragments;
    AlertShow alertShow;

    public AracModelEkleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_arac_model_ekle, container, false);

        ViewComponentsInitialize();
        EventState();
        return view;
    }

    private void ViewComponentsInitialize()
    {
        linearlayout_aracModelEkle = view.findViewById(R.id.linearlayout_aracModelEkle);
        til_aracModelAdi = view.findViewById(R.id.til_aracModelAdi);
        til_aracModelBilgi = view.findViewById(R.id.til_aracModelBilgi);
        ed_aracModelAdi = view.findViewById(R.id.ed_aracModelAdi);
        ed_aracModelBilgi = view.findViewById(R.id.ed_aracModelBilgi);
        btn_aracModeliEkle = view.findViewById(R.id.btn_aracModeliEkle);

        internetManager = new InternetManager(getContext());
        changeFragments = new ChangeFragments(getContext());
        alertShow = new AlertShow(getContext());
    }//func

    private void AracModelViewValidate() {
        ed_aracModelAdi.setError(null);

        boolean durumaracModelAdi= TextUtils.isEmpty(ed_aracModelAdi.getText());
        if(durumaracModelAdi)
        {
            if(durumaracModelAdi)
                ed_aracModelAdi.setError("Model alanı boş.");

        }else
        {
            AracModel aracModel = new AracModel();
            aracModel.setModel_adi(ed_aracModelAdi.getText().toString().trim());
            aracModel.setModel_bilgi(ed_aracModelBilgi.getText().toString());
            aracModel.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

            AracModelKayit(aracModel.getModel_adi(),aracModel.getModel_bilgi(),aracModel.getEkleyenId());
        }
    }//func

    private void AracModelKayit(String model_adi,String model_bilgi,int ekleyenId){
        Call<Result> aracInsert = ManagerALL.getInstance().carModelInsert(model_adi,model_bilgi,ekleyenId);
        aracInsert.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isTf())
                    {
                        Log.e("MODEL KAYIT : ",response.body().toString());
                        Log.e("MODEL KAYIT : ",response.toString());
                        alertShow.AlertDialogKutusu("Ekleme Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                    }else
                    {
                        Log.e("MODEL KAYIT else: ",response.body().toString());
                        Log.e("MODEL KAYIT else : ",response.toString());
                        alertShow.AlertDialogKutusu("Ekleme Bilgisi",response.body().getMesaj(),R.drawable.ic_call);
                    }
                }else
                {
                    alertShow.AlertDialogKutusu("Ekleme Sorunu",response.body().getMesaj(),R.drawable.ic_call);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.e("MODEL KAYIT exp: ",t.getLocalizedMessage());

            }
        });
    }//FUNC

    private void EventState()
    {
        btn_aracModeliEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetManager.InternetIsConnected())
                {
                    ModelViewValidate();
                }else
                {
                    Snackbar.make(linearlayout_aracModelEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }//func

    private void ModelViewValidate()
    {

        ed_aracModelAdi.setError(null);
        boolean durumUrunadi = TextUtils.isEmpty(ed_aracModelAdi.getText());

        if(durumUrunadi)
        {
            if(durumUrunadi)
                ed_aracModelAdi.setError("Ürün alanı boş.");

        }else
        {
            AracModel aracmodel = new AracModel();
            aracmodel.setModel_adi(ed_aracModelAdi.getText().toString().trim());
            aracmodel.setModel_bilgi(ed_aracModelBilgi.getText().toString().trim());
            aracmodel.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

            AracModelKayit(aracmodel.getModel_adi(),aracmodel.getModel_bilgi(),aracmodel.getEkleyenId());
        }

    }//FUNC
}
