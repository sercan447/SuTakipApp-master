package com.artirex.sutakip.Adapters;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class AnasayfaTabsAdapter extends FragmentPagerAdapter
{

    private List<Fragment> fragmentTabListe = new ArrayList<>();
    private List<String> fragmentBaslikListe = new ArrayList<>();

    public AnasayfaTabsAdapter(FragmentManager fm, List<Fragment> fragmentTabListe, List<String> fragmentBaslikListe) {
        super(fm);
        this.fragmentBaslikListe = fragmentBaslikListe;
        this.fragmentTabListe = fragmentTabListe;

    }
    @Override
    public Fragment getItem(int i) {
        return fragmentTabListe.get(i);
    }

    @Override
    public int getCount() {
        return fragmentTabListe.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentBaslikListe.get(position);
    }


}//AnasayfaTabsAdapter