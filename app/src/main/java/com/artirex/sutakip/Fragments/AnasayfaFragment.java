package com.artirex.sutakip.Fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.AnasayfaTabsAdapter;
import com.artirex.sutakip.Fragments.AnasayfaTabFragments.TabBirFragment;
import com.artirex.sutakip.Fragments.AnasayfaTabFragments.TabDortCagriFragment;
import com.artirex.sutakip.Fragments.AnasayfaTabFragments.TabIkiFragment;
import com.artirex.sutakip.Fragments.AnasayfaTabFragments.TabUcFragment;
import com.artirex.sutakip.R;

import java.util.ArrayList;
import java.util.List;


public class AnasayfaFragment extends Fragment {


    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AnasayfaTabsAdapter anasayfaTabsAdapter;
    List<Fragment> fragmentTabListe = new ArrayList<>();
    List<String> fragmentBaslikListe = new ArrayList<>();
    Fragment menuFragment;

    public AnasayfaFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_anasayfa, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Anasayfa Modülü");

        Log.e("TABS : ","ONCREATEVİEW");

        //options menu oluşturmak için bile alttaki methodu yazmak gerekiyoir.
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("TABS : ","onActivityCreated");
        Initialize();
    }
    private void Initialize()
    {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            Toast.makeText(getContext(),"if içi",Toast.LENGTH_LONG).show();;
            fragmentTabListe.clear();
            fragmentBaslikListe.clear();

            tabLayout = view.findViewById(R.id.tabs);
            viewPager = view.findViewById(R.id.viewpager);

            //viewPager.setAdapter(null);

            TabBirFragment tabBirFragment = new TabBirFragment();
            TabIkiFragment tabIkiFragment = new TabIkiFragment();
            TabUcFragment tabUcFragment = new TabUcFragment();
            TabDortCagriFragment tabDortCagriFragment = new TabDortCagriFragment();

            TabsFill(tabDortCagriFragment,"Çağrılar");
            TabsFill(tabBirFragment,"Alınan Siparişler");
            TabsFill(tabIkiFragment,"İptal Edilen Siparişler");
            TabsFill(tabUcFragment,"Tamamlanan Siparişler");


            anasayfaTabsAdapter = new AnasayfaTabsAdapter(getChildFragmentManager(), fragmentTabListe,fragmentBaslikListe);
            viewPager.setAdapter(anasayfaTabsAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }else
        {
            Toast.makeText(getContext(),"else içi",Toast.LENGTH_LONG).show();
        }

    }

    public void TabsFill(Fragment tabfragment,String baslik)
    {
        fragmentTabListe.add(tabfragment);
        fragmentBaslikListe.add(baslik);
    }//FUNC



}
