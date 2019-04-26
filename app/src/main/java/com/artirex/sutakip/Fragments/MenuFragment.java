package com.artirex.sutakip.Fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.MenuAdapter;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Model.Menu;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    CardView cardview_musteriler,cardview_anasayfa,cardview_ayarlar,
            cardview_product,cardview_customer,cardview_persons,
            cardview_call,cardview_drivers,cardview_order;

    View view;
    ChangeFragments changeFragments;

    MusteriFragment musteriFragment;
    AnasayfaFragment anasayfaFragment;
    PersonelFragment personelFragment;
    AracFragment aracFragment;
    UrunFragment urunFragment;
    AyarFragment ayarFragment;
    SiparisFragment siparisFragment;
    GelenAramaFragment gelenAramaFragment;

    RecyclerView recyclerView_menu;
    MenuAdapter menuAdapter;
    List<Menu> menulist = new ArrayList<>();

    private final String TAG = "MenuFragment";
    public MenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        Initilazie();
        OnClickState();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void Initilazie()
    {

        if( (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE ) {

            recyclerView_menu = view.findViewById(R.id.recyclerview_menuler);
            recyclerView_menu.setLayoutManager(new LinearLayoutManager(getContext()));

            changeFragments = new ChangeFragments(getActivity());
            musteriFragment = new MusteriFragment();
            anasayfaFragment = new AnasayfaFragment();
            personelFragment = new PersonelFragment();
            aracFragment = new AracFragment();
            urunFragment = new UrunFragment();
            ayarFragment = new AyarFragment();
            siparisFragment = new SiparisFragment();
            gelenAramaFragment = new GelenAramaFragment();

            getMenus(SharedPreferenceManager.getSharedPrefere(getContext()).getInt("yetki",-1));

           /* cardview_musteriler = view.findViewById(R.id.cardview_customer);
            cardview_anasayfa = view.findViewById(R.id.cardview_anasayfa);
            cardview_product = view.findViewById(R.id.cardview_product);
            cardview_call = view.findViewById(R.id.cardview_call);
            cardview_drivers = view.findViewById(R.id.cardview_drivers);
            cardview_order = view.findViewById(R.id.cardview_order);
            cardview_persons = view.findViewById(R.id.cardview_persons);
            cardview_ayarlar = view.findViewById(R.id.cardview_ayarlar);
            */

        }
    }//func



    public void OnClickState()
    {
       /* if( (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE ) {
            //CARDVIEWLERIN TIKLAMA OLAYLARI
            cardview_musteriler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(musteriFragment, "MusteriFragmentTAG");
                }
            });
            cardview_anasayfa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(anasayfaFragment, "AnasayfaFragmentTAG");
                }
            });
            cardview_persons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(personelFragment, "PersonelFragmentTAG");
                }
            });
            cardview_drivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(aracFragment, "AracFragmentTAG");
                }
            });
            cardview_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(gelenAramaFragment, "GelenAramaFragmentTAG");
                }
            });
            cardview_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(siparisFragment, "SiparisFragmentTAG");
                }
            });
            cardview_ayarlar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(ayarFragment, "AyarFragmentTAG");
                }
            });
            cardview_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragments.change(urunFragment, "UrunFragmentTAG");
                }
            });
        }*/
    }//func

    private void getMenus(int yetkId)
    {
        Call<List<Menu>> req = ManagerALL.getInstance().getMenus(yetkId);
        req.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {

                if(response.isSuccessful()) {
                    menulist = response.body();
                    menuAdapter = new MenuAdapter(getContext(), menulist, getActivity());
                    recyclerView_menu.setAdapter(menuAdapter);
                    Log.e(TAG,"SAYI : "+response.body().size());
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {

            }
        });
    }


}
