package com.carpcapture.carpcapture;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chris on 04/12/15.
 */
public class GetPrediction extends AsyncTask<String, String, String> {
    private String lakeIdParam;
    private GoogleMap mMap;

    public GetPrediction(String lakeId, GoogleMap myMap) { lakeIdParam = lakeId; mMap = myMap; }

    @Override
    protected String doInBackground(String[] url) {
        String requestUrl = "https://carp-capture-api.herokuapp.com/lakes/" + lakeIdParam + "/current_prediction.json";

        Log.e("REQUEST URL", requestUrl);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
//            HashMap obj = (HashMap) JSONValue.parse(responseBody);

            return responseBody;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        Log.e("RESPONSE", result);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.carp_marker);
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
        PredictionFactory rectangles = new PredictionFactory(result);
        ArrayList rectanglesArray = rectangles.convertToIterator();
        Log.e("RECTANGLESARRAY", rectanglesArray.toString());

        final ArrayList<String> rectLat = new ArrayList<String>();
        final ArrayList<String> rectLng = new ArrayList<String>();

        for(int i = 0; i < rectanglesArray.size(); i++) {
            Log.e("IN LAKE ARRAY LIST", rectanglesArray.get(i).toString());

            HashMap rectangle = (HashMap) rectanglesArray.get(i);

            LatLng other = new LatLng(Double.parseDouble(rectangle.get("lat").toString()), Double.parseDouble(rectangle.get("lng").toString()));

            MarkerOptions markerOptionsOther = new MarkerOptions().position(other)
                    .title("Potential carp locaiton")
                    .snippet("Could be worth a bait")
                    .icon(icon);
//
            mMap.addMarker(markerOptionsOther);
        }

//        LatLng other = new LatLng(Double.parseDouble("51.5505"), Double.parseDouble("0.5973"));
////

//        MarkerOptions markerOptionsOther = new MarkerOptions().position(other)
//                .title("Current Location")
//                .snippet("Thinking of finding some thing...")
//                .icon(icon);
////
//        mMap.addMarker(markerOptionsOther);
//        mMap.addMarker(markerOptionsOther);
//
//
//        MarkerOptions markerOptions = new MarkerOptions().position(lake)
//                .title("Current Location")
//                .snippet("Thinking of finding some thing...")
//                .icon(icon);
//
//        mMap.addMarker(markerOptions);
    }
}
