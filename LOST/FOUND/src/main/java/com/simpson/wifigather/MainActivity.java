package com.simpson.wifigather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    static WifiManager wifi;
    BroadcastReceiver wifiReceiver;
    static List<ScanResult> results;
    TextView tv;
    static EditText et;
    wifiScanReceiver scanReceiver;
    static Spinner sp;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item);


        sp = (Spinner)findViewById(R.id.spinner);
        et = (EditText)findViewById(R.id.locationText);
        tv = (TextView) findViewById(R.id.resultText);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();

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

    @Override
    protected void onPause() {
        unregisterReceiver(scanReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onPause();
    }

    public static void scanAndStore(View view)
    {
        wifi.startScan();
        Log.d("Button press", "What the fuck");
    }

    class wifiScanReceiver extends BroadcastReceiver{

        public void onReceive(Context c, Intent intent)
        {
            Log.d("Scan Complete", "Eat a p33n");
            results = wifi.getScanResults();

            Comparator<ScanResult> resultComparator = new Comparator<ScanResult>() {
                @Override
                public int compare(ScanResult lhs, ScanResult rhs) {
                    return (lhs.level > rhs.level ? -1 : (lhs.level == rhs.level ? 0 : 1));
                }
            };

            Collections.sort(results, resultComparator);

            DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

            dh.insertLocation(et.getText().toString(), results.get(0).BSSID.toString(), results.get(1).BSSID.toString(), results.get(2).BSSID.toString(), dh.returnCount() + 1);

            Toast.makeText(getApplicationContext(), dh.returnCount() + " Stuff", Toast.LENGTH_SHORT).show();
        }
    }
}
