package com.carpcapture.carpcapture;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends MenuActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.carpicon);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String lat = getIntent().getStringExtra("LAT");
        String lng = getIntent().getStringExtra("LNG");
        String zoom = getIntent().getStringExtra("ZOOM");

        Log.e("LAT", lat);
        Log.e("LNG", lng);
        Log.e("ZOOM", zoom);

        // Add a marker in Sydney and move the camera
        LatLng lake = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//        mMap.addMarker(new MarkerOptions().position(lake).title("Lake Marker"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lake, Float.valueOf(zoom)));

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.carp_marker);


        MarkerOptions markerOptions = new MarkerOptions().position(lake)
                .title("Current Location")
                .snippet("Thinking of finding some thing...")
                .icon(icon);

        mMap.addMarker(markerOptions);
    }
}
