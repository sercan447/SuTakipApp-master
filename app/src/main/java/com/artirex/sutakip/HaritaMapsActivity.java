package com.artirex.sutakip;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HaritaMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double lat = 0;
    double lng = 0;
    String adres="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harita_maps);

        Intent intnt = getIntent();
        lat = intnt.getDoubleExtra("lat",0);
        lng = intnt.getDoubleExtra("lng",0);
        adres = intnt.getStringExtra("adres");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng( lat, lng);

        mMap.setMaxZoomPreference(550);
        mMap.addMarker(new MarkerOptions().position(sydney).title(adres));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
    }
}
