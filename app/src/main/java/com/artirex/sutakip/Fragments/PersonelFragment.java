package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
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

import com.artirex.sutakip.Adapters.MusteriAdapter;
import com.artirex.sutakip.Adapters.PersonelAdapter;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonelFragment extends Fragment {

    private FloatingActionButton fab_personelEkle;
    private View view;
    private ChangeFragments changeFragments;

    private PersonelEkleFragment personelEkleFragment;
    private RecyclerView recyclerview_personeller;
    private List<Personel> personelList = new ArrayList<>();

    private PersonelAdapter adapter;

    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private ProgressDialog progressDialog;

    TextView tv_veriBulunamadi;


    public PersonelFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personel, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Personel Modülü");
        Initialize();
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MusteriClickState();
    }

    private void Initialize() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Personel Listesi");
        progressDialog.setMessage("Yükleniyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        personelEkleFragment = new PersonelEkleFragment();
        changeFragments = new ChangeFragments(getActivity());

        fab_personelEkle = view.findViewById(R.id.fab_personelEkle);
        recyclerview_personeller = view.findViewById(R.id.recyclerview_personeller);

        MenuGoruntulemeTablo();
        gerPersonsList();

    }//func


    private void MusteriClickState() {
        fab_personelEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    changeFragments.change(personelEkleFragment, "PersonelEkleFragmentTAG");
                }else
                {
                    changeFragments.changeNavbar(personelEkleFragment,"PersonelEkleFragmentTAG",null);
                }
            }
        });
    }//func

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_personel, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_card_personel:
                //  MenuGoruntulemeTablo();
                // Toast.makeText(getContext(),"a",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_list_personel:
                //  MenuGoruntulemeListe();
                // Toast.makeText(getContext(),"b",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_rastele_personel:
                //  MenuGoruntulemeRastgele();
                return true;
            case R.id.menu_a_z_personel:
                MenuSirala_AZ();
                return true;
            case R.id.menu_z_a_personel:
                MenuSirala_ZA();
                return true;
            default:
                return false;
        }
    }

    public void MenuGoruntulemeTablo() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerview_personeller.setLayoutManager(gridLayoutManager);

        // adapter.notifyDataSetChanged();
    }

    private void MenuGoruntulemeListe() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_personeller.setLayoutManager(linearLayoutManager);

        //  adapter.notifyDataSetChanged();
    }

    private void MenuGoruntulemeRastgele() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_personeller.setLayoutManager(staggeredGridLayoutManager);
        //  adapter.notifyDataSetChanged();
    }

    private void MenuSirala_AZ() {
        Collections.sort(personelList, new Comparator<Personel>() {
            @Override
            public int compare(Personel o1, Personel o2) {
                String name1 = o1.getAdi();
                String name2 = o2.getAdi();

                return name1.compareTo(name2);
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void MenuSirala_ZA() {
        Collections.sort(personelList, new Comparator<Personel>() {
            @Override
            public int compare(Personel o1, Personel o2) {
                String name1 = o1.getAdi();
                String name2 = o2.getAdi();

                return name2.compareTo(name1);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void gerPersonsList() {
        Call<List<Personel>> musReq = ManagerALL.getInstance().getPersonsList();
        musReq.enqueue(new Callback<List<Personel>>() {
            @Override
            public void onResponse(Call<List<Personel>> call, Response<List<Personel>> response) {
                if (response.isSuccessful()) {
                    Log.e("PERSONEL LIST :", response.toString());
                    Log.e("PERSONEL list", response.body().toString());
                    personelList = response.body();
                    adapter = new PersonelAdapter(getContext(), personelList, getActivity());

                    tv_veriBulunamadi.post(new Runnable() {
                        @Override
                        public void run() {
                            if(personelList.size() == 0)
                            {
                                tv_veriBulunamadi.setVisibility(View.VISIBLE);
                            }else
                            {
                                tv_veriBulunamadi.setVisibility(View.GONE);
                                recyclerview_personeller.setAdapter(adapter);
                            }
                        }
                    });

                    progressDialog.cancel();


                } else {
                    Log.e("PERSONEL LIST else:", response.toString());
                    Log.e("PERSONEL list else", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<List<Personel>> call, Throwable t) {
                Log.e("PERSONEL onFailure :", t.getLocalizedMessage());

            }
        });
    }//FUNC
}
