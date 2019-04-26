package com.artirex.sutakip.Fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.SiparisAdapter;
import com.artirex.sutakip.Adapters.SiparisTabsAdapter;
import com.artirex.sutakip.Fragments.SiparisTagsFragments.ETicaretFragment;
import com.artirex.sutakip.Fragments.SiparisTagsFragments.EmanetFragment;
import com.artirex.sutakip.Fragments.SiparisTagsFragments.IptalFragment;
import com.artirex.sutakip.Fragments.SiparisTagsFragments.KrediKartiFragment;
import com.artirex.sutakip.Fragments.SiparisTagsFragments.NakitFragment;
import com.artirex.sutakip.Fragments.SiparisTagsFragments.VeresiyeFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SiparisFragment extends Fragment {


    FloatingActionButton fab_siparisEkle;
    View view;
    ChangeFragments changeFragments;

    SiparisEkleFragment siparisEkleFragment;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<Fragment> fragmentTabListe = new ArrayList<>();
    List<String> fragmentBaslikListe = new ArrayList<>();
    private SiparisTabsAdapter siparisTabsAdapter;

    public SiparisFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_siparis, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Satışlar Modülü");

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Initialize();
        SiparisClickState();
    }
    private void Initialize()
    {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            siparisEkleFragment = new SiparisEkleFragment();
            changeFragments = new ChangeFragments(getActivity());
            fab_siparisEkle = view.findViewById(R.id.fab_siparisEkle);

            tabLayout = view.findViewById(R.id.tabs2);
            viewPager = view.findViewById(R.id.viewpager2);

            EmanetFragment emanetFragment = new EmanetFragment();
            ETicaretFragment eTicaretFragment = new ETicaretFragment();
            IptalFragment iptalfragment = new IptalFragment();
            KrediKartiFragment krediKartiFragment = new KrediKartiFragment();
            NakitFragment nakitFragment = new NakitFragment();
            VeresiyeFragment veresiyeFragment = new VeresiyeFragment();

            FillTabs(emanetFragment,"Emanet");
            FillTabs(eTicaretFragment,"E-Ticaret");
            FillTabs(iptalfragment,"İptal");
            FillTabs(krediKartiFragment,"Kredi Kartı");
            FillTabs(nakitFragment,"Nakit");
            FillTabs(veresiyeFragment,"Veresiye");

            siparisTabsAdapter = new SiparisTabsAdapter(getChildFragmentManager(),fragmentTabListe,fragmentBaslikListe);
            viewPager.setAdapter(siparisTabsAdapter);
            tabLayout.setupWithViewPager(viewPager);

           /*
            tabLayout.getTabAt(1).setIcon(R.drawable.eticaret);
            tabLayout.getTabAt(2).setIcon(R.drawable.iptal);
            tabLayout.getTabAt(3).setIcon(R.drawable.creditcard);
            tabLayout.getTabAt(4).setIcon(R.drawable.change);
            tabLayout.getTabAt(5).setIcon(R.drawable.veresiye);
*/

        }else{
            Toast.makeText(getContext(),"else içinde siparis ",Toast.LENGTH_SHORT).show();
        }
    }//func

    private void FillTabs(Fragment fragment,String baslik)
    {
        fragmentTabListe.add(fragment);
        fragmentBaslikListe.add(baslik);
    }

    private void SiparisClickState()
    {
        fab_siparisEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                {
                    changeFragments.change(siparisEkleFragment,"SiparisEkleFragmentTAG");
                }else
                {
                    changeFragments.changeNavbar(siparisEkleFragment,"SiparisEkleFragmentTAG",null);
                }
            }
        });



    }//func



}
