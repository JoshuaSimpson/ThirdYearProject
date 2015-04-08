package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class settings extends Activity {

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





    public void downloadDB(View view) {
        Downloader dl = new Downloader();
        dl.execute(this.getFilesDir().toString());

    }
}