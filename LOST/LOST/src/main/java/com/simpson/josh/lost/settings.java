package com.simpson.josh.lost;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class settings extends FragmentActivity {

    CheckBox locationBox;
    CheckBox wirelessBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button wifiSetup = (Button)findViewById(R.id.addWiFi);

        if(Build.VERSION.SDK_INT < 18)
        {
            wifiSetup.setVisibility(View.GONE);
            wifiSetup.setEnabled(false);
        }

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
                ed.commit();
            }
        });

        wirelessBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ed.putBoolean("Wireless", compoundButton.isChecked());
                ed.commit();
            }
        });
    }

    public void downloadDB(View view) {
        Downloader dl = new Downloader();
        dl.execute(this.getFilesDir().toString());
    }

    public void wifiSetup(View view) {
        Intent newDialog = new Intent(this, LoginDialog.class);
        startActivity(newDialog);
    }

}