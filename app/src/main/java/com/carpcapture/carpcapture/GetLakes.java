package com.carpcapture.carpcapture;

import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by chris on 24/11/15.
 */
class GetLakes extends AsyncTask<String, String, String> {
    private LakesListActivity lakeList;

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
            System.out.println(response);
            String responseBody = response.body().string();
            HashMap obj = (HashMap) JSONValue.parse(responseBody);
            System.out.println(responseBody);
            System.out.println(obj.get("lakes"));
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

        String names[] = {"Hadleigh Res","Blue Lagoon","Piggeries"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lakeList,
                R.layout.lake_list_item, R.id.textView1, names);

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