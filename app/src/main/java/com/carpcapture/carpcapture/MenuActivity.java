package com.carpcapture.carpcapture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by chris on 25/11/15.
 */
public class MenuActivity extends AppCompatActivity {

    public boolean predictLocation(MenuItem item){
        return  false;
    }

    public boolean recordCapture(MenuItem item){
        return  false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_lakes, menu);

        MenuItem item = menu.findItem(R.id.action_prediction);
        item.setVisible(false);

        MenuItem CaptureItem = menu.findItem(R.id.action_record_capture);
        CaptureItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_captures) {
            Intent intent = new Intent(this, CapturesActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_lakes) {
            Intent intent = new Intent(this, LakesListActivity.class);
            startActivity(intent);
//            intent.putExtra("lakes_response", response);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
