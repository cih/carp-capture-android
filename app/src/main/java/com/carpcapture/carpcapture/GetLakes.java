package com.carpcapture.carpcapture;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chris on 24/11/15.
 */
class GetLakes extends AsyncTask<String, String, String> {
    private LakesListActivity lakeList;
    List<String> lakeNames = new ArrayList<String>();

    public GetLakes(LakesListActivity lakesListActivity) {
       lakeList = lakesListActivity;
    }

    @Override
    protected String doInBackground(String[] url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url[0])
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        LakesFactory lakes = new LakesFactory(result);
        ArrayList lakesArray = lakes.convertToIterator();
        Log.e("LAKESARRAY", lakesArray.toString());

//        int i = 0;
//        for ( ; lakesArray.hasNext() ; ++i ) lakesArray.next();

        ArrayList<String> lakeNames = new ArrayList<String>();

        for(int i = 0; i < lakesArray.size(); i++) {
            Log.e("IN LAKE ARRAY LIST", lakesArray.get(i).toString());

            HashMap lake = (HashMap) lakesArray.get(i);

            lakeNames.add(lake.get("name").toString());
        }

//        while(lakesArray.hasNext()) {
//            Map.Entry entry = (Map.Entry)lakesArray.next();
//            HashMap lake = (HashMap)entry.getValue();
//
//            Log.e("LAKE", lake.toString());
//
//            String lakeName = (String)lake.get("name");
//            lakeNames.add(lakeName);
//        }
//
//        Log.e("LAKE NAMES", lakeNames.toString());

        String names[] = {"Hadleigh Res","Blue Lagoon","Piggeries"};

        String [] newNames = lakeNames.toArray(new String[lakeNames.size()]);

        Log.e("ORIGINAL LAKE NAMES", names.getClass().toString());
        Log.e("ORIGINAL LAKE NAMES", names.toString());
        Log.e("LAKE NAMES CLASS", newNames.getClass().toString());
        Log.e("LAKE NAMES", newNames.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lakeList,
                R.layout.lake_list_item, R.id.textView1, newNames);

        final ListView listView = (ListView) lakeList.findViewById(R.id.lakes_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(lakeList, MapsActivity.class);
                lakeList.startActivity(intent);

            }
        });
    }
}