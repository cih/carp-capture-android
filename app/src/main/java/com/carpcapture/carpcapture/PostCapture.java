package com.carpcapture.carpcapture;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

/**
 * Created by chris on 04/12/15.
 */
public class PostCapture extends AsyncTask<String, String, String> {
    private CaptureActivity captureActivity;
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    public PostCapture(CaptureActivity activity) {
        captureActivity = activity;
    }

    @Override
    protected String doInBackground(String[] url) {
        String message;
        OkHttpClient client = new OkHttpClient();

        JSONObject params = new JSONObject();
        try {
            String id = captureActivity.getIntent().getStringExtra("ID");
            String relativeLocation = captureActivity.getIntent().getStringExtra("RELLOC");
            String margin = captureActivity.getIntent().getStringExtra("MARGIN");


            params.put("relative_location", relativeLocation);
            params.put("rectangle_id", id);
            params.put("margin", margin);

        } catch (Exception ex) {

        }


        JSONObject object = new JSONObject();
        try {
            object.put("observation", params);
        } catch (Exception ex) {

        }


        message = object.toString();

        Log.e("MESSAGE", message);

        Request request = new Request.Builder()
                .url(url[0])
                .post(RequestBody.create(MEDIA_TYPE_JSON, message))
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
//            HashMap obj = (HashMap) JSONValue.parse(responseBody);
            Log.e("RESPONSE", responseBody);

            return responseBody;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());

            return  null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Snackbar snackbar = Snackbar
                .make(captureActivity.findViewById(android.R.id.content), "Your capture has been recorded, tight lines!.", Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
