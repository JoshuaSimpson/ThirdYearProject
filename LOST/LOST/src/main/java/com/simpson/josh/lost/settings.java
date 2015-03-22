package com.simpson.josh.lost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;


public class settings extends ActionBarActivity {

    CheckBox locationBox;
    CheckBox wirelessBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        locationBox = (CheckBox) findViewById(R.id.locationBox);
        wirelessBox = (CheckBox) findViewById(R.id.wirelessBox);

        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPrefs.edit();

        if (sharedPrefs.getBoolean("Location", true) == true) {
            locationBox.setChecked(true);
        }

        if (sharedPrefs.getBoolean("Wireless", true) == true) {
            wirelessBox.setChecked(true);
        }
    }
}
