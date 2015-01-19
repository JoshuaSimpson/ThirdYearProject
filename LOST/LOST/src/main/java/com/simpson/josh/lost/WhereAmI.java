package com.simpson.josh.lost;

import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class WhereAmI extends ActionBarActivity {

    WifiManager wifi;
    BroadcastReceiver wifiReceiver;
    List<ScanResult> results;
    TextView tv;
    String test = "";
    int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_am_i);


        // Wifi start here
        tv = (TextView) findViewById(R.id.textField);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                results = wifi.getScanResults();
                Log.d("Stuff happened", "Lol");
                Toast.makeText(getApplicationContext(), Integer.toString(results.get(0).level), Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
                for(ScanResult x : results)
                {
                    if(x.frequency > temp)
                    {
                        temp = x.level;
                        test = "Highest signal strength is name: " + x.SSID + " MAC: " + x.BSSID + " with signal strength " + x.level + "MORE STUFF: " + x.capabilities;
                    }
                }

                tv.setText(test);
            }

        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_where_am_i, menu);
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