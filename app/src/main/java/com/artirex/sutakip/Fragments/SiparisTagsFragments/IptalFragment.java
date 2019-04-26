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
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.artirex.sutakip.Adapters.SiparisAdapter;
        import com.artirex.sutakip.Adapters.SiparisTabsAdapter;
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

public class IptalFragment extends Fragment {

    private View view;
    private RecyclerView recyclerview_iptal;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<SiparisGet> siparisList = new ArrayList<>();
    private SiparisAdapter siparisAdapter;
    private TextView tv_veriBulunamadi;
    private SwipeRefreshLayout swipelayout_iptal;
    private ChangeFragments changeFragments;

    public IptalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_iptal, container, false);

        Initialize();
        setHasOptionsMenu(true);

        getOrdersListPayment("4");

        return view;
    }
    private void Initialize()
    {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
         {
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);

        recyclerview_iptal = view.findViewById(R.id.recyclerview_iptal);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerview_iptal.setLayoutManager(linearLayoutManager);
        swipelayout_iptal = view.findViewById(R.id.swipelayout_iptal);
        changeFragments = new ChangeFragments(getContext());

        }else{

        }
        swipelayout_iptal.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipelayout_iptal.setRefreshing(false);
                getOrdersListPayment("4");
            }
        });
    }//func

    private void getOrdersListPayment(String durumcode)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersList(durumcode);
        req.enqueue(new Callback<List<SiparisGet>>() {
            @Override
            public void onResponse(Call<List<SiparisGet>> call, Response<List<SiparisGet>> response) {

                if(response.isSuccessful()) {

                    siparisList = response.body();
                    siparisAdapter = new SiparisAdapter(getContext(),siparisList,getActivity());

                    if(siparisList.size() == 0)
                    {
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerview_iptal.setAdapter(siparisAdapter);
                    }


                    Log.e("SiparisGet 0 List :",response.body().toString());
                    Log.e("SiparisGet 0  List 2 :",response.toString());
                }else
                {
                    Log.e("SiparisGet0 List else :",response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<SiparisGet>> call, Throwable t) {
                Log.e("Siparis 0  hhata :",t.getLocalizedMessage());
            }
        });
    }//Func
    /*
    private void getOrdersListPayment(int odeme_turu)
    {
        Call<List<SiparisGet>> req = ManagerALL.getInstance().getOrdersListPayment(odeme_turu);
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
                        recyclerview_iptal.setAdapter(siparisAdapter);
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

    */
}


