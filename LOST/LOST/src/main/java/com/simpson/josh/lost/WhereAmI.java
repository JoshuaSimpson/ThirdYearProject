package com.simpson.josh.lost;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class WhereAmI extends Activity {

    WifiManager wifi;
    BroadcastReceiver wifiReceiver;
    List<ScanResult> results;
    TextView tv;
    wifiScanReceiver scanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_am_i);


        // Wifi start here
        tv = (TextView) findViewById(R.id.textField);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();
        wifi.startScan();
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

    class wifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            results = wifi.getScanResults();

            Log.d("Stuff happened", "Lol");
            Toast.makeText(getApplicationContext(), Integer.toString(results.get(0).level), Toast.LENGTH_LONG).show();

            //New comparator so that we can get the top three WiFi points
            Comparator<ScanResult> resultComparator = new Comparator<ScanResult>() {
                @Override
                public int compare(ScanResult lhs, ScanResult rhs) {
                    return (lhs.level > rhs.level ? -1 : (lhs.level == rhs.level ? 0 : 1));
                }
            };

            //Sort 'dem WiFis
            Collections.sort(results, resultComparator);


            String testToastString = "";

            testToastString += "" + results.get(0).BSSID + "" + results.get(1).BSSID +  "" + results.get(2).BSSID;

            testToastString = MainActivity.myGraph.getLocFromMac(testToastString).toString();

            tv.setText(testToastString);

            Log.d("Stuffing it here", "" + testToastString);
            //Cool, we have WiFis sorted by level
            Toast.makeText(getApplicationContext(), testToastString, Toast.LENGTH_SHORT).show();
        }
    }
}
