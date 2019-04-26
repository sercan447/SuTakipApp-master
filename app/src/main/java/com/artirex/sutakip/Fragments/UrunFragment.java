package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artirex.sutakip.Adapters.MusteriAdapter;
import com.artirex.sutakip.Adapters.UrunAdapter;
import com.artirex.sutakip.Adapters.UrunTipiBaseAdapter;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.Urun;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UrunFragment extends Fragment {

    FloatingActionButton fab_urunEkle;
    View view;
    ChangeFragments changeFragments;
    RecyclerView recyclerview_urunler;
    UrunEkleFragment urunEkleFragment;
    List<Urun> urunList = new ArrayList<>();

    UrunAdapter adapter;
    TextView tv_veriBulunamadi;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ProgressDialog progressDialog;


    public UrunFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_urun, container, false);

        Initialize();
        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UrunClickState();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_urun,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.menu_card_urun:
             //   MenuGoruntulemeTablo();
                // Toast.makeText(getContext(),"a",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_list_urun:
              //  MenuGoruntulemeListe();
                // Toast.makeText(getContext(),"b",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_rastele_urun:
               // MenuGoruntulemeRastgele();
                return true;
            case R.id.menu_a_z_urun:
                MenuSirala_AZ();
                return true;
            case R.id.menu_z_a_urun:
                MenuSirala_ZA();
                return true;
            default:
                return false;
        }
    }
    private void Initialize()
    {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Ürün Listesi");
        progressDialog.setMessage("Yükleniyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        urunEkleFragment = new UrunEkleFragment();
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        changeFragments = new ChangeFragments(getActivity());
        fab_urunEkle = view.findViewById(R.id.fab_urunEkle);
        recyclerview_urunler = view.findViewById(R.id.recyclerview_urunler);

        MenuGoruntulemeListe();
            getProductList();


    }//func
    public void MenuGoruntulemeTablo()
    {
        gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerview_urunler.setLayoutManager(gridLayoutManager);

        // adapter.notifyDataSetChanged();
    }
    private void MenuGoruntulemeListe()
    {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_urunler.setLayoutManager(linearLayoutManager);

        //  adapter.notifyDataSetChanged();
    }
    private void MenuGoruntulemeRastgele()
    {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerview_urunler.setLayoutManager(staggeredGridLayoutManager);
        //  adapter.notifyDataSetChanged();
    }
    private void MenuSirala_AZ()
    {
        Collections.sort(urunList, new Comparator<Urun>() {
            @Override
            public int compare(Urun o1, Urun o2) {
                String name1 = o1.getUrun_adi();
                String name2 = o2.getUrun_adi();

                return name1.compareTo(name2);
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void MenuSirala_ZA()
    {
        Collections.sort(urunList, new Comparator<Urun>() {
            @Override
            public int compare(Urun o1, Urun o2) {
                String name1 = o1.getUrun_adi();
                String name2 = o2.getUrun_adi();

                return name2.compareTo(name1);
            }
        });
        adapter.notifyDataSetChanged();
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
                    adapter = new UrunAdapter(getContext(),urunList,getActivity());

                    if(urunList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        tv_veriBulunamadi.setVisibility(View.GONE);
                        recyclerview_urunler.setAdapter(adapter);
                    }

                    progressDialog.cancel();
                }else
                {
                }
            }
            @Override
            public void onFailure(Call<List<Urun>> call, Throwable t) {
            }
        });
    }//FUNC
    private void UrunClickState()
    {
        fab_urunEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(urunEkleFragment,"UrunEkleFragmentTAG");
                }else
                {
                    changeFragments.changeNavbar(urunEkleFragment,"UrunEkleFragmentTAG",null);
                }

            }
        });
    }//func

}
