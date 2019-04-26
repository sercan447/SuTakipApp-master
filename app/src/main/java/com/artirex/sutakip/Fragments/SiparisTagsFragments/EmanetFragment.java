package com.artirex.sutakip.Fragments.SiparisTagsFragments;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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

import com.artirex.sutakip.Adapters.SiparisAdapter;
import com.artirex.sutakip.Fragments.OdemeTuruFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmanetFragment extends Fragment {

    private View view;
    private RecyclerView recyclerview_emanetler;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<SiparisGet> siparisList = new ArrayList<>();
    private SiparisAdapter siparisAdapter;
    private TextView tv_veriBulunamadi;
    private SwipeRefreshLayout swipelayout_emanet;
    private ProgressDialog progressDialog;

    ChangeFragments changeFragments;

    public EmanetFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_emanet, container, false);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Initialize();
//          progressDialog.show();
        getOrdersListPayment(5,0);
    }

    private void Initialize()
    { if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
           /* progressDialog = ProgressDialog.show(getContext(),"Emanet","Bekleyiniz..");
          //  progressDialog.setTitle("Emanet Kategorisi");
            //progressDialog.setMessage("Veriler Listeleniyor..");
            progressDialog.setCancelable(false);
*/

            changeFragments = new ChangeFragments(getContext());

            tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
            recyclerview_emanetler = view.findViewById(R.id.recyclerview_emanetler);
             linearLayoutManager = new LinearLayoutManager(getContext());
             gridLayoutManager = new GridLayoutManager(getContext(),3);
            recyclerview_emanetler.setLayoutManager(linearLayoutManager);
           swipelayout_emanet = view.findViewById(R.id.swipelayout_emanet);

        }else{

        }

        swipelayout_emanet.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrdersListPayment(5,0);
                swipelayout_emanet.setRefreshing(false);
            }
        });

    }//func

    //odeme_turu  = 5 ÇEŞİT ODEME türü (emanet,veresiye,nakit vs.) olan ödeme tipleri
    //tarih aralıgı ise DATALARI 1,2 HAFTALIK  YADA BIR AYLIK olarak gosterilmek üzere verilmiş parametre tipi
    private void getOrdersListPayment(int odeme_turu,int tarih_araligi)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersListPayment(odeme_turu,tarih_araligi,"","");
        req.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {
                if(response.isSuccessful()) {
                     siparisList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(),siparisList,getActivity());
                    //Listede eleman yok ise verinin olmadıgını söylesin.

                    if(siparisList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {

                       tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_emanetler.setAdapter(siparisAdapter);
                    }
                    Log.e("SiparisGet1 List :",response.body().toString());
                    Log.e("SiparisGet1 List 2 :",response.toString());
                }else
                {
                    Log.e("SiparisGet1 List else :",response.toString());
                }

               // progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis1 hhata :",t.getLocalizedMessage());

              //  progressDialog.dismiss();
            }
        });
    }//Func



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_siparisler,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /*
     * 1: bir hatfalıık siparis |  geçmişe dönük
     * 2: iki haftalık siparis  | geçmişe dönük
     * 3: bir aylık siparis | geçmişe dönük
     * 0 : 2 günlük siparis kaydı | geçmişe dönük
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId)
        {
            case R.id.menu_siparis_birhaftalik:
                getOrdersListPayment(5,1);
                return true;
            case R.id.menu_siparis_ikihaftalik:
                getOrdersListPayment(5,2);
                return true;
            case R.id.menu_siparis_biraylik:
                getOrdersListPayment(5,3);
                return true;
            case R.id.menu_siparis_tarihegore:
                OdemeTuruFragment odemeTuruFragment = new OdemeTuruFragment();
                changeFragments.change(odemeTuruFragment,"odemeTuruFragmentTAG");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onStop() {
        super.onStop();

       // progressDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //progressDialog.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
       // progressDialog.dismiss();
    }

}
