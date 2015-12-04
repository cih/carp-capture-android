package com.carpcapture.carpcapture;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;

/**
 * Created by chris on 04/12/15.
 */
public class GetPrediction extends AsyncTask<String, String, String> {
    private String lakeIdParam;

    public GetPrediction(String lakeId) { lakeIdParam = lakeId; }

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
    }
}
