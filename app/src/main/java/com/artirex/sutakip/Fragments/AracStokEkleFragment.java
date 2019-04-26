package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.StokListeAdapter;
import com.artirex.sutakip.Adapters.UrunBoyutBaseAdapter;
import com.artirex.sutakip.Adapters.UrunTipiBaseAdapter;
import com.artirex.sutakip.Helper.AlertShow;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AracStokEkleFragment extends Fragment {

    View view;
    TextView tv_arac_plaka;
    TextInputLayout til_arac_kilometre,til_arac_yakitDurumu,til_arac_stok_bilgi;
    TextInputEditText ed_arac_kilometre,ed_arac_yakitDurumu,ed_arac_stok_bilgi;
    Button btn_aracStokEkle,btn_stokListeyeEkle;
    LinearLayout linearlayout_aracStokEkle;

    private Spinner spinner_stok_anaurun,spinner_stok_alturun;
    private EditText ed_stokbilgi;
    private ListView listview_Stokbilgi;
    StokListeAdapter stokListeAdapter;
    List<StokBilgi> stokEkle = new ArrayList<>();
    List<StokBilgiShowList> stokBilgiShowLists = new ArrayList<>();

    InternetManager internetManager;
    ChangeFragments changeFragments;
    AlertShow alertShow;

    String plakaAdi="";
    int aracId = -1;

    String listeUrunAdi,listeAlturunAdi;
    int listeUrunId,listeAlturunId,listeStokbilgiAdet;

    UrunTipiBaseAdapter urunTipiBaseAdapter;
    UrunBoyutBaseAdapter urunBoyutBaseAdapter;

    List<Urun> urunList = new ArrayList<>();
    List<AltUrun> altUrunList = new ArrayList<AltUrun>();

    public AracStokEkleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_arac_stok_ekle, container, false);

        InitComponents();
        ClickState();

        if(getArguments() != null)
        {
            Bundle bundle = getArguments();

            aracId = bundle.getInt("plakaId",-1);
            plakaAdi = bundle.getString("plakaAdi","");
        }

        tv_arac_plaka.setText(plakaAdi);
        return view;
    }

    private void InitComponents()
    {
        alertShow = new AlertShow(getContext());
        internetManager = new InternetManager(getContext());
        changeFragments = new ChangeFragments(getContext());

        linearlayout_aracStokEkle = view.findViewById(R.id.linearlayout_aracStokEkle);
        tv_arac_plaka = view.findViewById(R.id.tv_arac_plaka);

        til_arac_kilometre = view.findViewById(R.id.til_arac_kilometre);
        til_arac_yakitDurumu = view.findViewById(R.id.til_arac_yakitDurumu);
        til_arac_stok_bilgi = view.findViewById(R.id.til_arac_stok_bilgi);

        ed_arac_kilometre = view.findViewById(R.id.ed_arac_kilometre);
        ed_arac_yakitDurumu = view.findViewById(R.id.ed_arac_yakitDurumu);
        ed_arac_stok_bilgi = view.findViewById(R.id.ed_arac_stok_bilgi);

        spinner_stok_anaurun = view.findViewById(R.id.spinner_stok_anaurun);
        spinner_stok_alturun = view.findViewById(R.id.spinner_stok_alturun);
        ed_stokbilgi = view.findViewById(R.id.ed_stokbilgi);
        listview_Stokbilgi = view.findViewById(R.id.listview_stokliste);

        btn_aracStokEkle = view.findViewById(R.id.btn_aracStokEkle);
        btn_stokListeyeEkle = view.findViewById(R.id.btn_stokListeyeEkle);

        listview_Stokbilgi.setVisibility(View.GONE);

        getProductList();
    }

    private void ClickState()
    {
        btn_aracStokEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetManager.InternetIsConnected())
                {
                    StokViewValidate();

                }else
                {
                    Snackbar.make(linearlayout_aracStokEkle,Message.NO_INTERNET_MESSAGE,Toast.LENGTH_LONG).show();
                }
            }
        });
        spinner_stok_anaurun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Urun tiklananTip = (Urun)parent.getSelectedItem();

                listeUrunId = tiklananTip.getUrun_id();
                listeUrunAdi = tiklananTip.getUrun_adi();

                getSubProductList(tiklananTip.getUrun_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_stok_alturun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        AltUrun tiklananBoyut = (AltUrun)parent.getSelectedItem();

                        listeAlturunId = tiklananBoyut.getAlturun_id();
                        listeAlturunAdi = tiklananBoyut.getAlturun_adi();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        btn_stokListeyeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LISTEYE ELEMAN EKLENECEGI ZAMAN LİSTEYI GORUNUR YAPIYORUM

                listview_Stokbilgi.setVisibility(View.VISIBLE);

                listeStokbilgiAdet = Integer.valueOf(ed_stokbilgi.getText().toString());

                Log.e("TAG","listeye ekleniyor..");

                //BU SINIF LİSTEDE EKLENEN STOK BILGILERINI GOSTERMEK ICIN
                StokBilgiShowList stokshow = new StokBilgiShowList(plakaAdi,listeUrunAdi,listeAlturunAdi,listeStokbilgiAdet);
                //BU ISE VERITABANINI EKLEME YAPMAK ICIN OLUSTURULAN SINIF
                StokBilgi stokBilgi = new StokBilgi(aracId,listeUrunId,listeAlturunId,listeStokbilgiAdet);

                stokBilgiShowLists.add(stokshow);
                stokEkle.add(stokBilgi);

                stokListeAdapter = new StokListeAdapter(stokBilgiShowLists,getContext());

                Log.e("DATALAR",""+ stokEkle.toString());

                listview_Stokbilgi.setAdapter(stokListeAdapter);

                stokListeAdapter.notifyDataSetChanged();
            }
        });
    }//func

    @Subscribe(sticky = true)
    public void ListViewItemDelete(StokListeAdapter.ListeItemDeleteEvent listeItemDeleteEvent)
    {
       // stokBilgiShowLists.remove(listeItemDeleteEvent.getItemId());
        stokEkle.remove(listeItemDeleteEvent.getItemId());
    }

    private void StokViewValidate()
    {
        til_arac_kilometre.setError(null);

        til_arac_stok_bilgi.setError(null);
        til_arac_yakitDurumu.setError(null);

        boolean durumKm = TextUtils.isEmpty(ed_arac_kilometre.getText());
        boolean durumYakit = TextUtils.isEmpty(ed_arac_yakitDurumu.getText());
        boolean durumBilgi = TextUtils.isEmpty(ed_arac_stok_bilgi.getText());


        if(durumKm || durumYakit)
        {
            ed_arac_kilometre.setError("Kilometre bilgisi giriniz.");
            ed_arac_yakitDurumu.setError("Yakıt bilgisi giriniz.");
        }else
        {
            //stok kayıt

            int kilometre = Integer.valueOf(ed_arac_kilometre.getText().toString());
            int yakit = Integer.valueOf(ed_arac_yakitDurumu.getText().toString());
            String bilgi = ed_arac_stok_bilgi.getText().toString().trim();

            AracStok aracStok = new AracStok(0,aracId,kilometre,yakit,bilgi,"",stokEkle);


            if(stokEkle.size() != 0)
            {
                StokBilgiKayit(aracStok);
            }else
            {
                Snackbar.make(linearlayout_aracStokEkle, "Araç Stoğu için Ürün girmelisiniz.",Snackbar.LENGTH_LONG).show();
            }



            //stok güncelleme
        }
    }
    private void getProductList()
    {
        Call<List<Urun>> request = ManagerALL.getInstance().getProductList();
        request.enqueue(new Callback<List<Urun>>() {
            @Override
            public void onResponse(Call<List<Urun>> call, Response<List<Urun>> response) {

                if(response.isSuccessful())
                {
                    urunList = response.body();
                    urunTipiBaseAdapter = new UrunTipiBaseAdapter(urunList,getContext());
                    spinner_stok_anaurun.setAdapter(urunTipiBaseAdapter);
                }else
                {
                }
            }
            @Override
            public void onFailure(Call<List<Urun>> call, Throwable t) {

            }
        });
    }//FUNC

    private void getSubProductList(int urun_id)
    {
        Call<List<AltUrun>> request = ManagerALL.getInstance().getSubProductList(urun_id);
        request.enqueue(new Callback<List<AltUrun>>() {
            @Override
            public void onResponse(Call<List<AltUrun>> call, Response<List<AltUrun>> response) {

                if(response.isSuccessful())
                {
                    altUrunList = response.body();
                    urunBoyutBaseAdapter = new UrunBoyutBaseAdapter(altUrunList,getContext());

                    spinner_stok_alturun.setAdapter(urunBoyutBaseAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<AltUrun>> call, Throwable t) {
            }
        });
    }//func
    private void StokBilgiKayit(AracStok aracStok)
    {
        if(stokEkle.size() == 0)
        {
            listview_Stokbilgi.setVisibility(View.GONE);
            Snackbar.make(linearlayout_aracStokEkle,"Araca Stok Eklemediniz .!",Snackbar.LENGTH_LONG).show();
             return;
        }

        Call<Result> str = ManagerALL.getInstance().aracStokEkle(aracStok);
        str.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.e("CEVAP dgr",response.body().toString());

                Result result = response.body();

                if(result.isTf())
                {
                    alertShow.AlertDialogKutusu("",result.getMesaj(),R.drawable.ic_add);
                }else
                {
                    alertShow.AlertDialogKutusu("",result.getMesaj(),R.drawable.ic_add);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("CEVAP hata ",t.getLocalizedMessage().toString());
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }


}
