package com.simpson.wifigather;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends Activity {

    static WifiManager wifi;
    static List<ScanResult> results;
    static EditText et;
    static Spinner sp;
    BroadcastReceiver wifiReceiver;
    TextView tv;
    wifiScanReceiver scanReceiver;
    ArrayAdapter<String> dataAdapter;
    Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = (Button)findViewById(R.id.scanButton);

        dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item);


        sp = (Spinner)findViewById(R.id.spinner);
        sp.setAdapter(dataAdapter);

        updateSpinner();

        et = (EditText)findViewById(R.id.locationText);


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
        if (id == R.id.action_save) {
            uploadDB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onPause();
    }

    public void scanAndStore(View view)
    {
        scanButton.setEnabled(false);
        registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Log.d("Button press", "What the fuck");
    }

    public void uploadDB() {
        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());
        dh.uploadData();
    }

    public void updateSpinner() {
        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());
        Cursor idListCursor = dh.getNodeList();

        dataAdapter.clear();

        if (idListCursor.getCount() == 0) {
            dataAdapter.add("Nothing");
        } else if (idListCursor.getCount() > 0) {
            dataAdapter.add("Nothing");
            idListCursor.moveToFirst();
            do {
                dataAdapter.add(idListCursor.getString(0) + " | " + idListCursor.getString(1));
                sp.setSelection(dataAdapter.getCount() - 1);
            } while (idListCursor.moveToNext());
        }
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

            if (sp.getSelectedItem().toString() == "Nothing") {
                    dh.insertLocation(et.getText().toString(), results.get(0).BSSID.toString(), dh.getNodeCount() + 1);
                    updateSpinner();
                } else {
                    dh.insertLocation(et.getText().toString(), results.get(0).BSSID.toString(), dh.getNodeCount() + 1);
                    dh.insertEdge(dh.getEdgeCount() + 1, sp.getSelectedItemPosition(), dh.getNodeCount(), 1, "Walk");
                    dh.insertEdge(dh.getEdgeCount() + 1, dh.getNodeCount(), sp.getSelectedItemPosition(), 1, "Walk");
                    updateSpinner();
                }
                Toast.makeText(getApplicationContext(), dh.getNodeCount() + " Stuff", Toast.LENGTH_SHORT).show();
                scanButton.setEnabled(true);
                unregisterReceiver(scanReceiver);
            }
        }

    }
