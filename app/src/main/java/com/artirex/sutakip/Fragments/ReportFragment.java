package com.artirex.sutakip.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artirex.sutakip.Fragments.ReportingFragment.NotSaleProductFragment;
import com.artirex.sutakip.Fragments.ReportingFragment.SaleActiveFragment;
import com.artirex.sutakip.Fragments.ReportingFragment.SaleProductCustomerFragment;
import com.artirex.sutakip.Fragments.ReportingFragment.SaleProductFragment;
import com.artirex.sutakip.Fragments.ReportingFragment.SaleProductMonthFragment;
import com.artirex.sutakip.Helper.ChangeFragments;
import com.artirex.sutakip.R;


public class ReportFragment extends Fragment {


    private View view;
    private ImageView img_toplamUrun,img_activeUrun,img_donemselSiparis,img_toplamMusteriSiparis,img_siparisVermeyenler;
    ChangeFragments changeFragments;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_report, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("İstatistiksel Raporlama Modülü   ");
        InitializeComponents();
        ClickState();

        return view;

    }
    private void InitializeComponents()
    {
        changeFragments = new ChangeFragments(getContext());

        img_toplamUrun = view.findViewById(R.id.img_toplamUrun);
        img_activeUrun = view.findViewById(R.id.img_activeUrun);
        img_donemselSiparis = view.findViewById(R.id.img_donemselSiparis);
        img_toplamMusteriSiparis = view.findViewById(R.id.img_toplamMusteriSiparis);
        img_siparisVermeyenler= view.findViewById(R.id.img_siparisVermeyenler);

    }

    private void ClickState()
    {
        img_toplamUrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaleProductFragment saleProductFragment = new SaleProductFragment();
                changeFragments.change(saleProductFragment,"saleProductFragmentTAG");
            }
        });

        img_activeUrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaleActiveFragment saleActiveFragment = new SaleActiveFragment();
                changeFragments.change(saleActiveFragment,"SaleActiveFragmentTAG");
            }
        });

        img_donemselSiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaleProductMonthFragment saleProductMonthFragment = new SaleProductMonthFragment();
                changeFragments.change(saleProductMonthFragment,"saleProductMonthFragmentTAG");
            }
        });
        img_toplamMusteriSiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaleProductCustomerFragment saleProductCustomerFragment = new SaleProductCustomerFragment();
                changeFragments.change(saleProductCustomerFragment,"saleProductCustomerFragmentTAG");
            }
        });
        img_siparisVermeyenler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotSaleProductFragment notSaleProductFragment = new NotSaleProductFragment();
                changeFragments.change(notSaleProductFragment,"NotSaleProductFragmentTAG");
            }
        });
    }



}
