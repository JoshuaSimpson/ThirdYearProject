package com.simpson.josh.lost;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class WhereAmI extends Activity {

    WifiManager wifi;
    List<ScanResult> results;
    TextView tv;
    wifiScanReceiver scanReceiver;

    private ProgressBar distraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_am_i);

        distraction = (ProgressBar) findViewById(R.id.progressBar);
        distraction.setVisibility(View.VISIBLE);

        // Wifi start here
        tv = (TextView) findViewById(R.id.textField);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();
        wifi.startScan();
    }

    public void refresh(View view) {
        tv.setVisibility(View.GONE);
        distraction.setVisibility(View.VISIBLE);
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

            String resultString = "";
            if (results.isEmpty() == true) {
                resultString = "Looks like there are no wireless access points around - are you in an elevator?";
            } else {

                //New comparator so that we can get the top three WiFi points
                Comparator<ScanResult> resultComparator = new Comparator<ScanResult>() {
                    @Override
                    public int compare(ScanResult lhs, ScanResult rhs) {
                        return (lhs.level > rhs.level ? -1 : (lhs.level == rhs.level ? 0 : 1));
                    }
                };
                //Sort 'dem WiFis
                Collections.sort(results, resultComparator);

                resultString = "Found you! You're at: \n \n " + MainActivity.myGraph.getLocFromMac(results.get(0).BSSID).toString();

            }
            distraction.setVisibility(View.GONE);

            tv.setVisibility(View.VISIBLE);
            tv.setText(resultString);
        }
    }
}
