package com.carpcapture.carpcapture;

import android.os.AsyncTask;

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
class GetLakes extends AsyncTask<String, Void, String> {

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
}