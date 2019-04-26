package com.artirex.sutakip.Fragments.SiparisTagsFragments;


        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.artirex.sutakip.Adapters.SiparisAdapter;
        import com.artirex.sutakip.Adapters.SiparisTabsAdapter;
        import com.artirex.sutakip.Fragments.OdemeTuruFragment;
        import com.artirex.sutakip.Fragments.SiparisEkleFragment;
        import com.artirex.sutakip.Helper.ChangeFragments;
        import com.artirex.sutakip.Model.SiparisGet;
        import com.artirex.sutakip.R;
        import com.artirex.sutakip.RestApi.ManagerALL;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class NakitFragment extends Fragment {

    private View view;
    private RecyclerView recyclerview_nakit;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<SiparisGet> siparisList = new ArrayList<>();
    private SiparisAdapter siparisAdapter;
    private TextView tv_veriBulunamadi;
    private SwipeRefreshLayout swipelayout_nakit;
    private ChangeFragments changeFragments;

    public NakitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_nakit, container, false);

        Initialize();
        setHasOptionsMenu(true);

        getOrdersListPayment(1,0,"","");

        return view;
    }
    private void Initialize()
    {
         if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
         {
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);

        recyclerview_nakit = view.findViewById(R.id.recyclerview_nakit);
        linearLayoutManager = new LinearLayoutManager(getContext());
      //  gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerview_nakit.setLayoutManager(linearLayoutManager);
        swipelayout_nakit = view.findViewById(R.id.swipelayout_nakit);
            changeFragments = new ChangeFragments(getContext());

       }else{

        }
        swipelayout_nakit.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getOrdersListPayment(1,0,"","");
                swipelayout_nakit.setRefreshing(false);
            }
        });
    }//func

    private void getOrdersListPayment(int odeme_turu,int tarih_araligi,String baslangictarihi,String bitistarihi)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersListPayment(odeme_turu,tarih_araligi,baslangictarihi,bitistarihi);
        req.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {
                if(response.isSuccessful()) {
                    siparisList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(),siparisList,getActivity());
                    //Listede eleman yok ise verinin olmadıgını söylesin.

                    if(siparisList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_nakit.setAdapter(siparisAdapter);

                    }
                    Log.e("SiparisGet1 List :",response.body().toString());
                    Log.e("SiparisGet1 List 2 :",response.toString());
                }else
                {
                    Log.e("SiparisGet1 List else :",response.toString());
                }
            }
            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis1 hhata :",t.getLocalizedMessage());
            }
        });
    }//Func


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_siparisler,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /*
    * 1: bir hatfalıık siparis |  geçmişe dönük
    * 2: iki haftalık siparis  | geçmişe dönük
    * 3: bir aylık siparis | geçmişe dönük
    * 0 : 2 günlük siparis kaydı | geçmişe dönük
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId)
        {
            case R.id.menu_siparis_birhaftalik:
                getOrdersListPayment(1,1,"","");
                return true;
            case R.id.menu_siparis_ikihaftalik:
                getOrdersListPayment(1,2,"","");
                return true;
            case R.id.menu_siparis_biraylik:
                getOrdersListPayment(1,3,"","");
                return true;
            case R.id.menu_siparis_tarihegore:
                OdemeTuruFragment odemeTuruFragment = new OdemeTuruFragment();
                changeFragments.change(odemeTuruFragment,"odemeTuruFragmentTAG");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}



