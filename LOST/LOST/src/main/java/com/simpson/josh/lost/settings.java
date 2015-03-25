package com.simpson.josh.lost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


public class settings extends ActionBarActivity {

    CheckBox locationBox;
    CheckBox wirelessBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        locationBox = (CheckBox) findViewById(R.id.locationBox);
        wirelessBox = (CheckBox) findViewById(R.id.wirelessBox);

        final SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor ed = sharedPrefs.edit();

        locationBox.setChecked(sharedPrefs.getBoolean("Location", true));
        wirelessBox.setChecked(sharedPrefs.getBoolean("Wireless", true));

        locationBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ed.putBoolean("Location", compoundButton.isChecked());
                Log.d("Location change: ", "" + sharedPrefs.getBoolean("Location", false));
                ed.commit();
            }
        });

        wirelessBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ed.putBoolean("Wireless", compoundButton.isChecked());
                Log.d("Wireless change: ", "" + sharedPrefs.getBoolean("Wireless", false));
                ed.commit();
            }
        });
    }

    public void JSONPost(View view) throws JSONException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Date d = new Date();

                String dateString = DateFormat.getInstance().format(d.getTime());


                HttpClient client = new DefaultHttpClient();

                HttpPost poster = new HttpPost("http://178.62.73.203/locations");

                Log.d("IT WORKED", "Stuff");
            }
        }).start();
    }

}