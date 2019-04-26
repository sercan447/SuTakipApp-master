package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.MusteriAdapter;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.ILoadMore;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusteriFragment extends Fragment {

    RecyclerView recyclerview_musteriler;
    FloatingActionButton fab_musteriEkle;
    View view;
    ChangeFragments changeFragments;

    MusteriEkleFragment musteriEkleFragment;
    List<Musteri> musteriList = new ArrayList<>();

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    SwipeRefreshLayout swipelayout_musteriler;

    MusteriAdapter adapter;
    TextView tv_veriBulunamadi;
    ProgressDialog progressDialog;

    public MusteriFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_musteri, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Müşteriler Modülü");

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Initialize();
        MusteriClickState();

        Toast.makeText(getContext(),"musterıler fra",Toast.LENGTH_SHORT).show();
    }
    private void Initialize()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Müşteriler Listesi");
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        swipelayout_musteriler = view.findViewById(R.id.swipelayout_musteriler);
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        musteriEkleFragment = new MusteriEkleFragment();

        changeFragments = new ChangeFragments(getActivity());
        fab_musteriEkle = view.findViewById(R.id.fab_musteriEkle);
        recyclerview_musteriler = view.findViewById(R.id.recyclerview_musteriler);

        adapter = new MusteriAdapter(recyclerview_musteriler,getContext(),musteriList,getActivity());

        MenuGoruntulemeListe();

       /* adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(musteriList.size() <= 10)
                {
                    musteriList.add(null);
                    adapter.notifyItemInserted(musteriList.size() -1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            musteriList.remove(musteriList.size() -1);
                            adapter.notifyItemRemoved(musteriList.size());

                            int index = musteriList.size();
                            int end = index + 10;

                            for(int i = index; i < end; i++)
                            {
                                //String name = UUID.randomUUID().toString();
                            }

                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },4000);
                }else{
                    Toast.makeText(getActivity(),"Load data completd.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        */


        getCustomerList();

        swipelayout_musteriler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getCustomerList();
                swipelayout_musteriler.setRefreshing(false);
            }
        });



    }//func
    public void MenuGoruntulemeTablo()
    {
        gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerview_musteriler.setLayoutManager(gridLayoutManager);

       // adapter.notifyDataSetChanged();
    }
    private void MenuGoruntulemeListe()
    {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_musteriler.setLayoutManager(linearLayoutManager);

      //  adapter.notifyDataSetChanged();
    }
    private void MenuGoruntulemeRastgele()
    {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerview_musteriler.setLayoutManager(staggeredGridLayoutManager);
        //  adapter.notifyDataSetChanged();
    }
    private void MenuSirala_AZ()
    {
       Collections.sort(musteriList, new Comparator<Musteri>() {
           @Override
           public int compare(Musteri o1, Musteri o2) {
               String name1 = o1.getMusteri_adi();
               String name2 = o2.getMusteri_adi();

               return name1.compareTo(name2);
           }
       });

       adapter.notifyDataSetChanged();
    }
    private void MenuSirala_ZA()
    {
        Collections.sort(musteriList, new Comparator<Musteri>() {
            @Override
            public int compare(Musteri o1, Musteri o2) {
                String name1 = o1.getMusteri_adi();
                String name2 = o2.getMusteri_adi();

                return name2.compareTo(name1);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void FirmaEkleFragment()
    {
        changeFragments.change(new FirmaEkleFragment(),"firmaEkleFragmentTAG");
    }

    private void MusteriClickState()
    {
        fab_musteriEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(musteriEkleFragment,"MusteriEkleFragmentTAG");
                }else
                {
                    changeFragments.changeNavbar(musteriEkleFragment,"MusteriEkleFragmentTAG",null);
                }


            }
        });
    }//func

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_musteriler,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.menu_card_musteri:
              //  MenuGoruntulemeTablo();
               // Toast.makeText(getContext(),"a",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_list_musteri:
              //  MenuGoruntulemeListe();
               // Toast.makeText(getContext(),"b",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_rastele_musteri:
              //  MenuGoruntulemeRastgele();
                return true;
            case R.id.menu_a_z_musteri:
                MenuSirala_AZ();
                return true;
            case R.id.menu_z_a_musteri:
                MenuSirala_ZA();
                return true;
            case R.id.menu_firmaEkle:
                FirmaEkleFragment();
                        return true;
            default:
                return false;
        }
    }//func


    private void getCustomerList()
    {
        Call<List<Musteri>> musReq = ManagerALL.getInstance().getCustomersList();
        musReq.enqueue(new Callback<List<Musteri>>() {
            @Override
            public void onResponse(Call<List<Musteri>> call, Response<List<Musteri>> response) {
                if (response.isSuccessful())
                {
                    Log.e("MUSTERI LIST :",response.toString());
                    Log.e("Musteri list",response.body().toString());
                    musteriList = response.body();
                    adapter = new MusteriAdapter(recyclerview_musteriler,getContext(),musteriList,getActivity());

                    if(musteriList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_musteriler.setAdapter(adapter);
                    }
                    progressDialog.cancel();


                }else{
                    Log.e("MUSTERI LIST else:",response.toString());
                    Log.e("Musteri list else",response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<List<Musteri>> call, Throwable t) {
                Log.e("MUSTERI onFailure :",t.getLocalizedMessage());

            }
        });
    }//FUNC
}
