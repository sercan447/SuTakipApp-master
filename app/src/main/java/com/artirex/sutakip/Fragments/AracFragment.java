package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.artirex.sutakip.Adapters.AracAdapter;
import com.artirex.sutakip.Adapters.AracBaseAdapter;
import com.artirex.sutakip.Adapters.MusteriAdapter;
import com.artirex.sutakip.Adapters.UrunAdapter;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Helper.TasarimGorunum;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
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

public class AracFragment extends Fragment {

    FloatingActionButton fab_aracEkle;
    View view;
    ChangeFragments changeFragments;

    RecyclerView recyclerview_araclar;
    AracEkleFragment aracEkleFragment;
    List<AracGet> aracList = new ArrayList<>();

    AracAdapter adapter;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    int tasarimgorunum = 0;
    TextView tv_veriBulunamadi;
    ProgressDialog progressDialog;

    public AracFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_arac, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Araç Modülü");
        Initialize();
        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AracClickState();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_arac,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.menu_card_arac:
               // TasarimGorunumKaydi(TasarimGorunum.TASARIM_NORMAL);
               // adapter.notifyDataSetChanged();
               // MenuGoruntulemeTablo();


                return true;
            case R.id.menu_list_arac:
               // TasarimGorunumKaydi(TasarimGorunum.TASARIM_TABLE);
                //adapter.notifyDataSetChanged();

               // MenuGoruntulemeListe();

                return true;
            case R.id.menu_rastele_arac:
               // TasarimGorunumKaydi(TasarimGorunum.TASARIM_RASTGELE);
               // adapter.notifyDataSetChanged();
               // MenuGoruntulemeRastgele();

                return true;
            case R.id.menu_a_z_arac:
                MenuSirala_AZ();
                return true;
            case R.id.menu_z_a_arac:
                MenuSirala_ZA();
                return true;
            case R.id.menu_aracModelEkle:
                AracModelFragmenteGit();
                return true;
            case R.id.menu_aracPeronslEkle:
                PersonelAracAtaGit();
                return true;
            default:
                return false;
        }
    }
private void AracModelFragmenteGit()
{
    changeFragments.change(new AracModelEkleFragment(),"aracModelEkleFragmentTAG");
}
private void PersonelAracAtaGit()
{
    changeFragments.change(new PersonelAracAtaFragment(),"personelAracAtaFragmentTAG");
}
    public void TasarimGorunumKaydi(int GORUNUM)
    {
        SharedPreferences.Editor editor = SharedPreferenceManager.setSharedEdit(getContext());
        editor.putInt("tasarimGorunum", GORUNUM);
        editor.commit();
    }
    private void Initialize()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Araçlar Listesi");
        progressDialog.setMessage("Listeleniyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        aracEkleFragment = new AracEkleFragment();
        changeFragments = new ChangeFragments(getActivity());

        fab_aracEkle = view.findViewById(R.id.fab_aracEkle);
        recyclerview_araclar = view.findViewById(R.id.recyclerview_araclar);

       tasarimgorunum = SharedPreferenceManager.getSharedPrefere(getContext()).getInt("tasarimGorunum",-1);


       MenuGoruntulemeTablo();

        getCarList();

    }//func
    public void MenuGoruntulemeTablo()
    {

        gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerview_araclar.setLayoutManager(gridLayoutManager);

        // adapter.notifyDataSetChanged();
    }
    private void MenuGoruntulemeListe()
    {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_araclar.setLayoutManager(linearLayoutManager);

        //  adapter.notifyDataSetChanged();
    }
    private void MenuGoruntulemeRastgele()
    {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerview_araclar.setLayoutManager(staggeredGridLayoutManager);
        //  adapter.notifyDataSetChanged();
    }
    private void MenuSirala_AZ()
    {
        Collections.sort(aracList, new Comparator<AracGet>() {
            @Override
            public int compare(AracGet o1, AracGet o2) {
                String name1 = o1.getArac_plaka();
                String name2 = o2.getArac_plaka();

                return name1.compareTo(name2);
            }
        });

        adapter.notifyDataSetChanged();
    }
    private void MenuSirala_ZA()
    {
        Collections.sort(aracList, new Comparator<AracGet>() {
            @Override
            public int compare(AracGet o1, AracGet o2) {
                String name1 = o1.getArac_plaka();
                String name2 = o2.getArac_plaka();

                return name2.compareTo(name1);
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void AracClickState()
    {
        fab_aracEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(aracEkleFragment,"AracEkleFragmentTAG");
                }else
                {
                    changeFragments.changeNavbar(aracEkleFragment,"AracEkleFragmentTAG",null);
                }
            }
        });
    }//func

    private void getCarList()
    {
        Call<List<AracGet>> request = ManagerALL.getInstance().getCarList();
        request.enqueue(new Callback<List<AracGet>>() {
            @Override
            public void onResponse(Call<List<AracGet>> call, Response<List<AracGet>> response) {

                if(response.isSuccessful())
                {
                    aracList = response.body();
                    adapter = new AracAdapter(getContext(),aracList,getActivity());
                    
                    if(aracList.size() == 0)
                    {
                        Log.e("ARAC gelmıyor",response.body().toString());

                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        Log.e("ARAC ",response.body().toString());
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_araclar.setAdapter(adapter);
                    }

                    progressDialog.cancel();
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<AracGet>> call, Throwable t) {

            }
        });
    }//FUNC

}
