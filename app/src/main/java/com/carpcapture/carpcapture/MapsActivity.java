package com.carpcapture.carpcapture;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends MenuActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap currentLake = new HashMap();
    private HashMap currentRectangle = new HashMap();

    @Override
    public boolean recordCapture(MenuItem item) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), "Click on the lake to record a capture.", Snackbar.LENGTH_LONG);

        snackbar.show();

        return true;
    }

    @Override
    public boolean predictLocation(MenuItem item){
        String lakeId = getIntent().getStringExtra("ID");

        Log.e("LAEK ID", lakeId);

        GetPrediction prediction = new GetPrediction(lakeId);
        prediction.execute(lakeId);

//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.carp_marker);
//
//        String lat = getIntent().getStringExtra("LAT");
//        String lng = getIntent().getStringExtra("LNG");
//        String zoom = getIntent().getStringExtra("ZOOM");
//
//        Log.e("LAT", lat);
//        Log.e("LNG", lng);
//        Log.e("ZOOM", zoom);
//
//        // Add a marker in Sydney and move the camera
//        LatLng lake = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//
//
//        LatLng other = new LatLng(Double.parseDouble("51.5505"), Double.parseDouble("0.5973"));
//
//
//        MarkerOptions markerOptionsOther = new MarkerOptions().position(other)
//                .title("Current Location")
//                .snippet("Thinking of finding some thing...")
//                .icon(icon);
//
//        mMap.addMarker(markerOptionsOther);
//
//
//        MarkerOptions markerOptions = new MarkerOptions().position(lake)
//                .title("Current Location")
//                .snippet("Thinking of finding some thing...")
//                .icon(icon);
//
//        mMap.addMarker(markerOptions);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_prediction);
        item.setVisible(true);

        MenuItem captureItem = menu.findItem(R.id.action_record_capture);
        captureItem.setVisible(true);

        return true;
    }

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

    public boolean newCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);

        intent.putExtra("ID", currentRectangle.get("id").toString());
        intent.putExtra("MARGIN", currentRectangle.get("margin").toString());
        intent.putExtra("RELLOC", currentRectangle.get("relative_location").toString());

        startActivity(intent);
        return true;
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
        String id = getIntent().getStringExtra("ID");

        Log.e("LAT", lat);
        Log.e("LNG", lng);
        Log.e("ZOOM", zoom);
        Log.e("ID", id);


        Log.e("MAP FACTORY", GetLakes.lakesFactory.convertToIterator().toString());

        ArrayList lakesArray = GetLakes.lakesFactory.convertToIterator();

        for(int i = 0; i < lakesArray.size(); i++) {
            HashMap lakeHash = (HashMap) lakesArray.get(i);

            Log.e("ID", id);
            Log.e("ID CLASS", id.toString().getClass().toString());
            Log.e("ID", lakeHash.get("id").toString());
            Log.e("ID", lakeHash.get("id").toString().getClass().toString());

            if (Integer.parseInt(lakeHash.get("id").toString()) == Integer.parseInt(id)){
                Log.e("INSIDE", "YES");
                currentLake = lakeHash;
            }
        }

        Log.e("LAKE IN VIEW", currentLake.toString());

        final ArrayList rectangles = (ArrayList) currentLake.get("rectangles");
        final ArrayList polygons = new ArrayList();

        for(int i = 0; i < rectangles.size(); i++) {
            HashMap rectangleHash = (HashMap) rectangles.get(i);
            Double x2 = (Double) rectangleHash.get("north_east_lat");
            Double y2 = (Double) rectangleHash.get("north_east_lng");
            Double x1 = (Double) rectangleHash.get("south_west_lat");
            Double y1 = (Double) rectangleHash.get("south_west_lng");

//            x1 = ?  ;  y1 = ? ;    // First diagonal point
//            x2 = ?  ;  y2 = ? ;    // Second diagonal point

            Double xc = (x1 + x2)/2  ;
            Double yc = (y1 + y2)/2  ;    // Center point
            Double xd = (x1 - x2)/2  ;
            Double yd = (y1 - y2)/2  ;    // Half-diagonal

            Double x3 = xc - yd  ;
            Double y3 = yc + xd;    // Third corner
            Double x4 = xc + yd  ;
            Double y4 = yc - xd;    // Fourth corner


            Log.e("HERE", "ADDING POLYGON");

//            LatLng ne = new LatLng(x1, y1);
//            LatLng sw = new LatLng(x2, y2);
//            LatLngBounds latLngBounds = new LatLngBounds(ne, sw);


            // getPoints may be a better way to store here
            Polygon polygon = mMap.addPolygon(new PolygonOptions()
                    .add(new LatLng(x1, y1), new LatLng(x3, y1), new LatLng(x2, y2), new LatLng(x4, y2), new LatLng(x1, y1))
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));

            polygons.add(polygon);
        }


        // Add a marker in Sydney and move the camera
        LatLng lakeMarker = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//        mMap.addMarker(new MarkerOptions().position(lake).title("Lake Marker"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lakeMarker, Float.valueOf(zoom)));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {

                LatLng latLng = new LatLng(arg0.latitude, arg0.longitude);

                Log.e("POLYGONS SIZE", String.valueOf(polygons.size()));

                for(int i = 0; i < polygons.size(); i++) {
                    Polygon polygon = (Polygon) polygons.get(i);

                    List<LatLng> points = polygon.getPoints();

                    Log.e("", String.valueOf(PolyUtil.containsLocation(latLng, points, true)));
                    Log.e("arg0", arg0.latitude + "-" + arg0.longitude);

                    if(PolyUtil.containsLocation(latLng, points, true)){
                        HashMap rectangle = (HashMap) rectangles.get(i);

                        currentRectangle = rectangle;
                        Log.e("RECTANGLE >>>>>>>>>>", String.valueOf(rectangle.get("id")));
                        Log.e("RECTANGLE >>>>>>>>>>", String.valueOf(rectangle.get("relative_location")));
                        Log.e("RECTANGLE >>>>>>>>>>", String.valueOf(rectangle.get("margin")));

                    }
                }

                newCapture();
            }
        });
    }
}
