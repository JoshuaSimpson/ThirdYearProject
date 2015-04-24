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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class WhereAmI extends Activity {

    WifiManager wifi;
    List<ScanResult> results;
    TextView tv;
    ImageButton ib;
    wifiScanReceiver scanReceiver;
    private String locationString;

    private ProgressBar distraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_am_i);

        distraction = (ProgressBar) findViewById(R.id.progressBar);
        distraction.setVisibility(View.VISIBLE);

        // Wifi start here
        tv = (TextView) findViewById(R.id.textField);
        ib = (ImageButton) findViewById(R.id.getDirections);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();
        wifi.startScan();
    }

    public void refresh(View view) {
        tv.setVisibility(View.GONE);
        ib.setVisibility(View.GONE);
        ib.setEnabled(false);
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
                for(int i = 0 ; i < results.size() ; i++ )
                {
                    if(!MainActivity.myGraph.getLocFromMac(results.get(i).BSSID).equals("Location not found"))
                    {
                        locationString = MainActivity.myGraph.getLocFromMac(results.get(i).BSSID);
                        break;
                    }
                }
                resultString = "You're at: \n \n " + locationString;
            }
            distraction.setVisibility(View.GONE);
            if(locationString != "Location not found")
            {
                ib.setVisibility(View.VISIBLE);
                ib.setEnabled(true);
            }
            tv.setVisibility(View.VISIBLE);
            tv.setText(resultString);
        }
    }

    public void getDirections(View view)
    {
        Intent i = new Intent(this, RouteFinder.class);
        i.putExtra("Location", MainActivity.myGraph.getNodeFromLocation(locationString).id - 1);
        startActivity(i);
    }
}
