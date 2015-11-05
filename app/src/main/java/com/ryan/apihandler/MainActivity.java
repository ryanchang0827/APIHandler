package com.ryan.apihandler;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ryan.apihandler.core.APIHandler;
import com.ryan.apihandler.core.Method;
import com.ryan.apihandler.core.OnApiCallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MainActivity extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        callapi();
    }

    public void callapi() {
        Map<String,Object> params = new HashMap<>();
        params.put("Apple", "1");
        params.put("Orange", "3");
        params.put("Banana", "5");

        //String API_PATH = "https://www.google.com";
        String API_PATH = "http://192.168.188.123/test.php";

        APIHandler a = new APIHandler(activity, new OnApiCallbackListener(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("API Result", result);

            }

            @Override
            public void onFailure() {
                super.onFailure();
            }
        });

        //POST
        //a.APIPath(API_PATH).httpMethod(Method.POST).setParams(params).execute();

        //GET
        a.APIPath(API_PATH).setParams(params).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
