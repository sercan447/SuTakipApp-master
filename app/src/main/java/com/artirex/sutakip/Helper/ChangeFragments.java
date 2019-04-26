package com.artirex.sutakip.Helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import com.artirex.sutakip.R;

public class ChangeFragments {

    Context context;
    public ChangeFragments(Context context)
    {
      this.context = context;
    }


    public void change(Fragment fragment,String fragmentTAG)
    {
        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_fragment,fragment,fragmentTAG);
        transaction.addToBackStack(fragmentTAG);
        transaction.commit();
    }

    public void changeNavbar(Fragment fragment, String fragmentTAG, DrawerLayout drawerLayout)
    {
        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.fragment_tutucuu,fragment,fragmentTAG);
        transaction.addToBackStack(fragmentTAG);
        transaction.commit();

        if(drawerLayout != null)
        {
            drawerLayout.closeDrawer(Gravity.START);
        }

    }

    public void addBacktostack(String tag)
    {

    }
    public void popBacktostack(String tag)
    {

    }
}
