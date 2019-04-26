package com.artirex.sutakip.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.artirex.sutakip.Adapters.AracUrunStokAdapter;
import com.artirex.sutakip.Adapters.CagriAdapter;
import com.artirex.sutakip.Model.Cagrilar;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.R;
import com.artirex.sutakip.RestApi.ManagerALL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GelenAramaFragment extends Fragment {

    View view;
    RecyclerView recyclerView_anlikcagri;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipelayout_anlikcagrilar;
    TextView tv_veriBulunamadi;
    List<Cagrilar> cagriList = new ArrayList<>();
    CagriAdapter cagriAdapter;
    ProgressDialog progressDialog;

    RequestQueue requestQueue;

    public GelenAramaFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gelen_arama, container, false);


        ViewComponentInitialize();

        return view;
    }


    private void ViewComponentInitialize()
    {
        requestQueue = Volley.newRequestQueue(getContext());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Gelen Çağrı Listesi");
        progressDialog.setMessage("Getiriliyor..");
        progressDialog.setCancelable(false);
      //  progressDialog.show();

        swipelayout_anlikcagrilar = view.findViewById(R.id.swipelayout_anlikcagrilar);
        tv_veriBulunamadi = view.findViewById(R.id.tv_veriBulunamadi);
        recyclerView_anlikcagri = view.findViewById(R.id.recyclerView_anlikcagri);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_anlikcagri.setLayoutManager(linearLayoutManager);

        veriler();

    swipelayout_anlikcagrilar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            veriler();
            swipelayout_anlikcagrilar.setRefreshing(false);
        }
    });

    }//func


    public void veriler()
    {
               // cagriList.clear();
                String URL = "http://crmsntrl.netgsm.com.tr:9111/8503051414/queuestats?username=8503051414&password=ARTIREX123&crm_id=5";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ARAMALAR GELIYO ","PAT "+response);

                        getArayanNumaraParse(response);

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ARAMALAR GELIYO ","HATA "+error.getMessage());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return super.getParams();
                    }
                };

                requestQueue.add(stringRequest);
            }
            public void getArayanNumaraParse(final String response)
            {
                try {

                    JSONObject aramalar = new JSONObject(response);
                    Log.e("ARAMA quese","--"+aramalar.getString("queues"));

                    JSONArray queues = aramalar.getJSONArray("queues");
                    JSONObject obj = queues.getJSONObject(0);

                    JSONObject callers = obj.getJSONObject("callers");
                    List<Cagrilar> liveCallList = new ArrayList<>();

                    final Iterator<String> det = callers.keys();
                    try{

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (det.hasNext()) {
                                    String veri = det.next();

                                    Log.e("WHİLE : ", "!!" + veri);
                                    cagriList.add(new Cagrilar(0, veri, "", "", 0));
                                }

                            }
                        });

                        t.start();


                    }catch (NoSuchElementException et)
                    {
                        Log.e("NO SUCH ELEMANT ","QQQ : "+et.getMessage().toString());
                    }

                        if (cagriList.size() == 0)
                        {
                            tv_veriBulunamadi.setVisibility(View.VISIBLE);
                        }else
                        {
                            tv_veriBulunamadi.setVisibility(View.GONE);
                            cagriAdapter = new CagriAdapter(getContext(),cagriList,getActivity());
                            recyclerView_anlikcagri.setAdapter(cagriAdapter);
                        }


                    Log.e("ARAMA callers","**"+ obj.getString("callers"));

                } catch (JSONException e) {
                    Log.e("HATA CATCH",e.getLocalizedMessage().toLowerCase());
                    e.printStackTrace();
                }

            }//Denetle
    private void getGelenCagriAnlik()
    {
        Call<List<Cagrilar>> request = ManagerALL.getInstance().getGelenCagriAnlik();
        request.enqueue(new Callback<List<Cagrilar>>() {
            @Override
            public void onResponse(Call<List<Cagrilar>> call, Response<List<Cagrilar>> response) {

                if(response.isSuccessful())
                {
                    Log.e("VERILER GELIYOR",response.body().toString());
                  //  cagriList = response.body();
                   // cagriAdapter = new CagriAdapter(getContext(),cagriList,getActivity());

                    if(cagriList.size() == 0)
                    {
                        Log.e("LISTE BOS",""+cagriList.size());
                        tv_veriBulunamadi.setVisibility(View.VISIBLE);
                    }else
                    {
                        Log.e("LISTE DOLU",""+cagriList.size());
                        tv_veriBulunamadi.setVisibility(View.INVISIBLE);
                        recyclerView_anlikcagri.setAdapter(cagriAdapter);
                    }

                    progressDialog.cancel();
                }else
                {

                }
            }
            @Override
            public void onFailure(Call<List<Cagrilar>> call, Throwable t) {
                Log.e("STOK HATA ",""+t.getMessage().toString());
            }
        });
    }//FUNC
}
