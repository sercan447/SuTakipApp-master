package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.artirex.sutakip.Adapters.AracBaseAdapter;
import com.artirex.sutakip.Adapters.AracModelBaseAdapter;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.AracModel;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AracEkleFragment extends Fragment {

    View view;
    private LinearLayout linearlayout_aracEkle;
    private ChangeFragments changeFragments;
    private InternetManager internetManager;
    private AlertShow alertShow;
    TextInputLayout til_plaka_aracEkle,til_telefon_aracEkle,til_bilgi_aracEkle;
    TextInputEditText ed_plaka_aracEkle,ed_telefon_aracEkle,ed_bilgi_aracEkle;
    private Spinner spinner_model_aracModelEkle;
    private Button btn_aracEkle;

    AracModelBaseAdapter aracModelBaseAdapter;
    List<AracModel> aracModelList = new ArrayList<>();

    private int aracModelId;
    private int aracId=-1;
    Bundle bundle;

    public AracEkleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_arac_ekle, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Araç Ekle Modülü");
        ViewComponentsInitialize();
        ButtonInsert();

        if(getArguments() != null)
        {
            bundle = getArguments();
            aracId = bundle.getInt("aracId",-1);
        }

        if(aracId != -1)
        {
            getCar(aracId);
            Log.e("BILGI ICERIGI","var suan ");
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    private void ViewComponentsInitialize()
    {
        linearlayout_aracEkle = view.findViewById(R.id.linearlayout_aracEkle);
        changeFragments = new ChangeFragments(getContext());
        internetManager = new InternetManager(getContext());
        alertShow = new AlertShow(getContext());

        til_plaka_aracEkle = view.findViewById(R.id.til_plaka_aracEkle);
        til_telefon_aracEkle = view.findViewById(R.id.til_telefon_aracEkle);
        til_bilgi_aracEkle = view.findViewById(R.id.til_bilgi_aracEkle);

        ed_plaka_aracEkle = view.findViewById(R.id.ed_plaka_aracEkle);
        ed_telefon_aracEkle = view.findViewById(R.id.ed_telefon_aracEkle);
        ed_bilgi_aracEkle = view.findViewById(R.id.ed_bilgi_aracEkle);

        spinner_model_aracModelEkle = view.findViewById(R.id.spinner_model_aracModelEkle);

        btn_aracEkle = view.findViewById(R.id.btn_aracEkle);

        ed_telefon_aracEkle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int i = 10 - start;
                if(start > 10)
                {
                    btn_aracEkle.setVisibility(View.GONE);
                }else
                {
                    btn_aracEkle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getCarModelList();

    }

    private void AracKayit( String plaka,String telefon,int model,String bilgi,int ekleyenId)
    {
        Call<Result> request = ManagerALL.getInstance().carInsert(plaka,telefon,model,bilgi,ekleyenId);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("ARAC KAYIT SUCCES:",response.toString());
                    Log.e("ARAC KAYIT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("ARAC KAYIT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("arac KAYIT HATA :",t.getLocalizedMessage());
                Log.e("arac KAYIT HATA2 :",t.toString());
            }
        });
    }//funcs
    private void AracGuncelleme(int arac_id, String plaka,String telefon,int model,String bilgi,int ekleyenId)
    {
        Call<Result> request = ManagerALL.getInstance().carUpdate(arac_id,plaka,telefon,model,bilgi,ekleyenId);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                {
                    Log.e("ARAC UPDT SUCCES:",response.toString());
                    Log.e("ARAC UPDT SUCES2:",response.body().toString());

                    alertShow.AlertDialogKutusu("Başarılı",response.body().getMesaj(),R.drawable.ic_call);
                }else
                {
                    Log.e("ARAC UPDT NOSUCC:",response.toString());
                    alertShow.AlertDialogKutusu("Başarısız.!",response.body().getMesaj(),R.drawable.drw_line_cerceve);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("arac UPDT HATA :",t.getLocalizedMessage());
                Log.e("arac UPDT HATA2 :",t.toString());
            }
        });
    }//funcs
    private void ButtonInsert() {

        btn_aracEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internetManager.InternetIsConnected())
                {
                    AracViewValidate();
                }else
                {
                    Snackbar.make(linearlayout_aracEkle, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });

        spinner_model_aracModelEkle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                AracModel aracModel = (AracModel) parent.getSelectedItem();
                aracModelId = aracModel.getModel_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//FUNC
    private void AracViewValidate()
    {
        ed_plaka_aracEkle.setError(null);
        ed_telefon_aracEkle.setError(null);
        ed_bilgi_aracEkle.setError(null);


        boolean durumPlaka = TextUtils.isEmpty(ed_plaka_aracEkle.getText());
        boolean durumTelefon = TextUtils.isEmpty(ed_telefon_aracEkle.getText());
        boolean durumBilgi = TextUtils.isEmpty(ed_bilgi_aracEkle.getText());

        if(durumPlaka || durumTelefon || durumBilgi)
        {
            if(durumPlaka)
                ed_plaka_aracEkle.setError("Adı alanı boş.");

            if(durumTelefon)
                ed_telefon_aracEkle.setError("Soyadı alanı boş.");

            if(durumBilgi)
                ed_bilgi_aracEkle.setError("Parola alanı boş.");

        }else
        {
            Arac arac = new Arac();
            arac.setArac_plaka(ed_plaka_aracEkle.getText().toString());
            arac.setArac_telefon(ed_telefon_aracEkle.getText().toString());
            arac.setArac_model(aracModelId);
            arac.setArac_bilgi(ed_bilgi_aracEkle.getText().toString());
            arac.setEkleyenId(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("kullaniciId",-1));

            if(aracId != -1)
            {
                AracGuncelleme(aracId,arac.getArac_plaka(),arac.getArac_telefon(),arac.getArac_model(),arac.getArac_bilgi(),arac.getEkleyenId());
            }else
            {
                AracKayit(arac.getArac_plaka(),arac.getArac_telefon(),arac.getArac_model(),arac.getArac_bilgi(),arac.getEkleyenId());
            }
        }

    }//FUNC

    private void getCarModelList()
    {

        Call<List<AracModel>> req = ManagerALL.getInstance().getCarModelList();
        req.enqueue(new Callback<List<AracModel>>() {
            @Override
            public void onResponse(Call<List<AracModel>> call, Response<List<AracModel>> response) {
                if(response.isSuccessful())
                {
                    aracModelList = response.body();
                    aracModelBaseAdapter = new AracModelBaseAdapter(aracModelList,getContext());
                    spinner_model_aracModelEkle.setAdapter(aracModelBaseAdapter);
                }else
                {

                }
            }

            @Override
            public void onFailure(Call<List<AracModel>> call, Throwable t) {

            }
        });
    }//FUNC

    private void getCar(int arac_id)
    {

        Call<AracGet> req = ManagerALL.getInstance().getCar(arac_id);

        req.enqueue(new Callback<AracGet>() {
            @Override
            public void onResponse(Call<AracGet> call, Response<AracGet> response) {
               // if(response.isSuccessful())
                {
                    AracGet aracGet = response.body();
                    ed_plaka_aracEkle.setText(""+aracGet.getArac_plaka());
                    ed_telefon_aracEkle.setText(aracGet.getArac_telefon());
                     ed_bilgi_aracEkle.setText(aracGet.getArac_bilgi());


                     int selectIndis = -1;
                    for (int i=0;i<aracModelList.size();i++)
                    {
                        Log.e("TAG",aracModelList.get(i).getModel_adi().toUpperCase() +"--"+ aracGet.getArac_model().toUpperCase());
                        if(aracModelList.get(i).getModel_adi().toUpperCase().trim().equalsIgnoreCase(aracGet.getArac_model().toUpperCase().trim()))
                        {
                            selectIndis = i;
                            Log.e("TAG","İF");
                        }else{
                            Log.e("TAG","else");
                        }
                    }
                    spinner_model_aracModelEkle.setSelection(selectIndis);
                }
            }

            @Override
            public void onFailure(Call<AracGet> call, Throwable t) {
                Log.e("hata carget : ","");
            }
        });
    }
}//func
