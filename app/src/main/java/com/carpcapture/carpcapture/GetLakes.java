package com.carpcapture.carpcapture;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
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
            return response.body().string();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            return null;
        }
    }
}