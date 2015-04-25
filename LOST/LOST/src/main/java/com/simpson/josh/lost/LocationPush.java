package com.simpson.josh.lost;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Josh on 08/04/2015.
 */
public class LocationPush extends Service {

    WifiManager wifi;
    ConnectivityManager cm;
    List<ScanResult> results;
    wifiScanReceiver scanReceiver;
    Context c;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        final Context c = this;

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();
        registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Toast.makeText(c, "Initial location upload", Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;


        //Consider calling JSONPOST with certain parameters, or whether to put this code into JSONPost itself - probably the former

        //Also need to consider the various types of 'connected' we will be


    }

    public void JSONPost(final String location, String date) {
        final String dateString = date;
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj.put("loc", location);
                    jsonobj.put("time", dateString);
                } catch (JSONException j) {
                    j.printStackTrace();
                }

                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppostreq = new HttpPost("http://178.62.73.203/locations.json");

                StringEntity se = null;
                try {
                    se = new StringEntity(jsonobj.toString());
                } catch (UnsupportedEncodingException u) {
                    u.printStackTrace();
                }

                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                httppostreq.setEntity(se);

                try {
                    HttpResponse httpResponse = httpclient.execute(httppostreq);

                } catch (Exception c) {
                    c.printStackTrace();
                }
            }
        }).start();
    }


    public void JSONPost(String mac, String location, String date, String notes) {
        final String macString = mac;
        final String locString = location;
        final String dateString = date;
        final String noteString = notes;


        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj.put("mac", macString);
                    jsonobj.put("location", locString);
                    jsonobj.put("time", dateString);
                    jsonobj.put("notes", noteString);
                } catch (JSONException j) {
                    j.printStackTrace();
                }


                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppostreq = new HttpPost("http://178.62.73.203/access_points.json");

                StringEntity se = null;
                try {
                    se = new StringEntity(jsonobj.toString());
                } catch (UnsupportedEncodingException u) {
                    u.printStackTrace();
                }

                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                httppostreq.setEntity(se);

                try {
                    HttpResponse httpResponse = httpclient.execute(httppostreq);
                } catch (Exception c) {
                    c.printStackTrace();
                }
            }
        }).start();
    }

    class wifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            unregisterReceiver(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FaultDatabaseHelper fh = new FaultDatabaseHelper(getApplicationContext());

                    results = wifi.getScanResults();

                    // New comparator so that we can get the top three WiFi points
                    Comparator<ScanResult> resultComparator = new Comparator<ScanResult>() {
                        @Override
                        public int compare(ScanResult lhs, ScanResult rhs) {
                            return (lhs.level > rhs.level ? -1 : (lhs.level == rhs.level ? 0 : 1));
                        }
                    };
                    // Sort 'dem WiFis
                    Collections.sort(results, resultComparator);

                    String firstMac = results.get(0).BSSID;

                    // Boolean as to whether we're at Kings - uses the first wifi result
                    boolean atKings = MainActivity.myGraph.containsMAC(firstMac);

                    WifiInfo info = wifi.getConnectionInfo();

                    // Get connection info
                    NetworkInfo networkInf = cm.getActiveNetworkInfo();

                    // Get a date instance for recording
                    Date d = new Date();
                    String dateString = DateFormat.getInstance().format(d.getTime());

                    // If we're at Kings then launch into this set of statements, otherwise we ain't got no business posting stuff
                    if (atKings) {
                        String location = MainActivity.myGraph.getLocFromMac(firstMac);
                        // If we're connected, and connected to WiFi
                        if (networkInf.isConnected() && networkInf.getType() == ConnectivityManager.TYPE_WIFI) {
                            if (internetAvailable()) {
                                // Here we POST the location data
                                JSONPost(location, dateString);

                                // Now to make sure we don't have any leftover faulty access point data
                                if (!fh.isEmpty()) {
                                    Cursor poster = fh.getFaults();
                                    poster.moveToFirst();

                                    do {
                                        JSONPost(poster.getString(0), poster.getString(1), poster.getString(2), poster.getString(3));
                                    } while (poster.moveToNext());

                                    fh.clearFaults();
                                }
                            }
                        }

                        try {
                            // This is our issue set, then just insert into the database
                            if (networkInf.isConnected() && InetAddress.getByName("http://www.google.co.uk").isReachable(10000)) {
                                // FirstMAC may not NECESSARILY be the one we're connected to...
                                fh.insertFault(info.getMacAddress(), location, dateString, "High latency");
                            } else if (networkInf.isConnected() && !InetAddress.getByName("http://www.google.co.uk").isReachable(10000)) {
                                // This case tells us if the latency is abnormally high
                                fh.insertFault(info.getMacAddress(), location, dateString, "No internet");
                            }
                        } catch (UnknownHostException u) {

                        } catch (IOException i) {

                        }

                    }
                    SystemClock.sleep(900000);
                    registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                }
            }).start();
        }

        private boolean internetAvailable() {
            boolean yes = false;
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                yes = (urlc.getResponseCode() == 200);
            } catch (IOException e) {

            }

            return yes;
        }
    }
}
