package com.artirex.sutakip;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.artirex.sutakip.Adapters.MenuAdapter;
import com.artirex.sutakip.BroadcastReceivers.BataryaBroadCastReceiver;
import com.artirex.sutakip.Fragments.AnasayfaFragment;
import com.artirex.sutakip.Fragments.AracFragment;
import com.artirex.sutakip.Fragments.AyarFragment;
import com.artirex.sutakip.Fragments.GelenAramaFragment;
import com.artirex.sutakip.Fragments.MusteriFragment;
import com.artirex.sutakip.Fragments.NavbarIcerikFragment;
import com.artirex.sutakip.Fragments.PersonelFragment;
import com.artirex.sutakip.Fragments.ReportFragment;
import com.artirex.sutakip.Fragments.SiparisFragment;
import com.artirex.sutakip.Fragments.StokFragment;
import com.artirex.sutakip.Fragments.UrunFragment;
import com.artirex.sutakip.Fragments.navbarAyarlarFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.RestApi.ManagerALL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    MusteriFragment musteriFragment;
    AnasayfaFragment anasayfaFragment;
    PersonelFragment personelFragment;
    AracFragment aracFragment;
    UrunFragment urunFragment;
    AyarFragment ayarFragment;
    SiparisFragment siparisFragment;
    GelenAramaFragment gelenAramaFragment;

    Toolbar mytoolbar;
    ChangeFragments changeFragments;
    BataryaBroadCastReceiver bataryaBroadCastReceiver;
    IntentFilter intentFilterBatarya;


    private NavigationView nav_view;
    private Fragment fragment;
    private DrawerLayout drawerLayout;
    Fragment fragment_navbarIcerik;

    ActionBarDrawerToggle toggle;
    List<com.artirex.sutakip.Model.Menu> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int izinControl = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        if(izinControl != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},100);
        }

        //ekran tablet ise YAN YATIRACAK DEGİL İSE PORTRAIT YAPICAK
        boolean DeviceScreenMode = getResources().getBoolean(R.bool.isTablet);
        if(DeviceScreenMode)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        InitComponents();


        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)  == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            changeFragments.change(anasayfaFragment,"AnasayfaFragmentTAG");
        }else
        {
            //UYGULAMA ILK ACILDIGINDA  AKTF OLAN SIPAISLERI GETIR
            SiparisiDurumaGoreListele(1);
        }


        this.registerReceiver(bataryaBroadCastReceiver,intentFilterBatarya);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mainactivity,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            switch (id)
            {
                case R.id.menu_cikis:
           SharedPreferences.Editor ed = SharedPreferenceManager.setSharedEdit(getApplicationContext());
           ed.clear();
           ed.commit();


            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);

                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private void SiparisiDurumaGoreListele(int durum_id)
    {
        NavbarIcerikFragment icerik = new NavbarIcerikFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("durum",durum_id);
        icerik.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucuu,icerik).commit();
       drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }else
            {

            }
        }
    }

    public void InitComponents()
    {
        mytoolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(mytoolbar);

        changeFragments = new ChangeFragments(MainActivity.this);
        anasayfaFragment = new AnasayfaFragment();
        musteriFragment = new MusteriFragment();
        personelFragment = new PersonelFragment();
        aracFragment = new AracFragment();
        urunFragment = new UrunFragment();
        ayarFragment = new AyarFragment();
        siparisFragment = new SiparisFragment();
        gelenAramaFragment = new GelenAramaFragment();

         bataryaBroadCastReceiver = new BataryaBroadCastReceiver();
        intentFilterBatarya = new IntentFilter();
        intentFilterBatarya.addAction(Intent.ACTION_BATTERY_CHANGED);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)  == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
             fragment_navbarIcerik = new NavbarIcerikFragment();
             getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucuu,fragment_navbarIcerik).commit();

            nav_view = findViewById(R.id.nav_view);
            drawerLayout = findViewById(R.id.drawer);
            toggle = new ActionBarDrawerToggle(this,drawerLayout,mytoolbar,0,0);
            drawerLayout.addDrawerListener(toggle);

            int YetkiId = SharedPreferenceManager.getSharedPrefere(getApplicationContext()).getInt("yetki",-1);
            getMenus(YetkiId);

           toggle.syncState();

            View baslik = nav_view.inflateHeaderView(R.layout.nav_baslik);
            nav_view.setNavigationItemSelectedListener(this);

        }else
        {
        }
    }

    private void getMenus(int yetkId)
    {
        Call<List<com.artirex.sutakip.Model.Menu>> req = ManagerALL.getInstance().getMenus(yetkId);
        req.enqueue(new Callback<List<com.artirex.sutakip.Model.Menu>>() {
            @Override
            public void onResponse(Call<List<com.artirex.sutakip.Model.Menu>> call, Response<List<com.artirex.sutakip.Model.Menu>> response) {

                if(response.isSuccessful()) {
                    menuList = response.body();
            //nav menu içerisine isim tanımlamalarını çekiyoruz.
                    Menu menu = nav_view.getMenu();

                    for (int i=0;i<menuList.size();i++) {
                        menu.add(Menu.CATEGORY_CONTAINER, menuList.get(i).getMenu_id(), 0,menuList.get(i).getMenu_adi());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<com.artirex.sutakip.Model.Menu>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.unregisterReceiver(bataryaBroadCastReceiver);
    }

    private void NormalTelefonGorunumuTasarimi(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucuu,fragment).commit();
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int secilenMenuId = menuItem.getItemId();
        String selectTitle = menuItem.getTitle().toString();

        switch (selectTitle)
        {
            case "Anasayfa":
           AnasayfaFragment anasayfaFragment = new AnasayfaFragment();
           NormalTelefonGorunumuTasarimi(anasayfaFragment);
                return true;
            case "Müşteriler":
                MusteriFragment icerik = new MusteriFragment();
                NormalTelefonGorunumuTasarimi(icerik);
                return true;
            case "Çağrılar":
                GelenAramaFragment gelenAramaFragment = new GelenAramaFragment();
                NormalTelefonGorunumuTasarimi(gelenAramaFragment);
                return true;
            case "Ürünler":
               UrunFragment urunFragment = new UrunFragment();
               NormalTelefonGorunumuTasarimi(urunFragment);
                return true;
            case "Araçlar":
            AracFragment aracFragment = new AracFragment();
            NormalTelefonGorunumuTasarimi(aracFragment);
                return true;
            case "Satışlar":
                SiparisFragment siparisFragment = new SiparisFragment();
                NormalTelefonGorunumuTasarimi(siparisFragment);
                return true;
            case "Personeller":
                PersonelFragment personelFragment = new PersonelFragment();
                NormalTelefonGorunumuTasarimi(personelFragment);
                return true;


            case "Aktif Siparişler" :
                SiparisiDurumaGoreListele(1);
                return true;
            case "İptal Edilen Siparişler":
                SiparisiDurumaGoreListele(4);
                return true;
            case "Teslim Edilen Müşteriler":
                SiparisiDurumaGoreListele(2);
                return true;
            case "Ayarlar":
                navbarAyarlarFragment navbarAyarlarFragment = new navbarAyarlarFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucuu,navbarAyarlarFragment).commit();
                drawerLayout.closeDrawer(Gravity.START);
                return true;
            case "Çıkış":
                Toast.makeText(getApplicationContext(),"cikis",Toast.LENGTH_SHORT).show();
                SharedPreferenceManager.getSharedPrefere(getApplicationContext()).edit().clear();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                return true;


        }
       return true;

    }


    @Subscribe(sticky = true)
    public void CardViewEventBus(MenuAdapter.MenuSecimDataEvent event)
    {

        Toast.makeText(getApplicationContext(),"gelendata"+event.getMenuAdi(),Toast.LENGTH_SHORT).show();
        if(event.getMenuAdi().equals("Anasayfa"))
        {
            changeFragments.change(new AnasayfaFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Müşteriler"))
        {
            changeFragments.change(new MusteriFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Çağrılar"))
        {
            changeFragments.change(new GelenAramaFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Ürünler"))
        {
            changeFragments.change(new UrunFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Araçlar"))
        {
            changeFragments.change(new AracFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Satışlar"))
        {
            changeFragments.change(new SiparisFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Personeller"))
        {
            changeFragments.change(new PersonelFragment(),"anasayfaFragmentTAG");
        }
        else  if(event.getMenuAdi().equals("Ayarlar"))
        {
            changeFragments.change(new AyarFragment(),"anasayfaFragmentTAG");
        }else if(event.getMenuAdi().equals("Stok"))
        {
            changeFragments.change(new StokFragment(),"stokFragmentTAG");
        }else if(event.getMenuAdi().equals("Raporlama"))
        {
            changeFragments.change(new ReportFragment(),"raporalamaFragmentTAG");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    public static class DrawerMenuEventBus
    {
        public int id;
        public String menuAdi;
        public DrawerMenuEventBus()
        {

        }
        public DrawerMenuEventBus(int id, String menuAdi) {
            this.id = id;
            this.menuAdi = menuAdi;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMenuAdi() {
            return menuAdi;
        }

        public void setMenuAdi(String menuAdi) {
            this.menuAdi = menuAdi;
        }
    }//class
}
