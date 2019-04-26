package com.artirex.sutakip.Fragments.SiparisTagsFragments;


        import android.app.ProgressDialog;
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
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

public class ETicaretFragment extends Fragment {

    private View view;
    private RecyclerView recyclerview_eticaret;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<SiparisGet> siparisList = new ArrayList<>();
    private SiparisAdapter siparisAdapter;
    private TextView tv_veriBulunamadi;
    private SwipeRefreshLayout swipelayout_eticaret;
    private ProgressDialog progressDialog;
    ChangeFragments changeFragments;

    public ETicaretFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_eticaret, container, false);

        Initialize();
        setHasOptionsMenu(true);

        getOrdersListPayment(3,0,"","");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        progressDialog.show();
    }

    private void Initialize()
    {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
         {
             /*
             //progressDialog = new ProgressDialog(getActivity());
             progressDialog = ProgressDialog.show(getContext(),"E-Ticaret","Bekleyiniz..");
           //  progressDialog.setTitle("E-Ticaret Kategorisi");
             //progressDialog.setMessage("Veriler Listeleniyor..");
             progressDialog.setCancelable(false);
*/
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        swipelayout_eticaret = view.findViewById(R.id.swipelayout_eticaret);
        recyclerview_eticaret = view.findViewById(R.id.recyclerview_eticaret);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerview_eticaret.setLayoutManager(linearLayoutManager);
        changeFragments = new ChangeFragments(getContext());

        }else{

        }

        swipelayout_eticaret.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrdersListPayment(3,0,"","");
                swipelayout_eticaret.setRefreshing(false);
            }
        });
    }//func

    private void getOrdersListPayment(int odeme_turu,int tarih_araligi,String bas_tarih,String bitis_tarihi)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersListPayment(odeme_turu,tarih_araligi,bas_tarih,bitis_tarihi);
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

                      //  progressDialog.cancel();
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_eticaret.setAdapter(siparisAdapter);
                    }
                    Log.e("SiparisGet1 List :",response.body().toString());
                    Log.e("SiparisGet1 List 2 :",response.toString());
                }else
                {
                    Log.e("SiparisGet1 List else :",response.toString());
                }
               // progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis1 hhata :",t.getLocalizedMessage());
                //progressDialog.dismiss();
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
                getOrdersListPayment(3,1,"","");
                return true;
            case R.id.menu_siparis_ikihaftalik:
                getOrdersListPayment(3,2,"","");
                return true;
            case R.id.menu_siparis_biraylik:
                getOrdersListPayment(3,3,"","");
                return true;
            case R.id.menu_siparis_tarihegore:
                OdemeTuruFragment odemeTuruFragment = new OdemeTuruFragment();
                changeFragments.change(odemeTuruFragment,"odemeTuruFragmentTAG");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause() {
        super.onPause();
       // progressDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // progressDialog.dismiss();
    }
}

