package com.simpson.josh.lost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Josh on 08/04/2015.
 */
public class LocationPush extends BroadcastReceiver {

    WifiManager wifi;
    ConnectivityManager cm;
    List<ScanResult> results;
    wifiScanReceiver scanReceiver;


    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();
        wifi.startScan();


        //Consider calling JSONPOST with certain parameters, or whether to put this code into JSONPost itself - probably the former

        //Also need to consider the various types of 'connected' we will be


        Log.d("Something happened", "Which is good");
        Toast.makeText(context, "Stuff happened", Toast.LENGTH_SHORT).show();

    }

    public void JSONPost(final String location, String date) {
        final String dateString = date;
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj.put("location", location);
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
                Log.d("IT WORKED", "Stuff");
            }
        }).start();
    }

    class wifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            results = wifi.getScanResults();

            SharedPreferences sharedPrefs = c.getSharedPreferences("FaultStore", Context.MODE_PRIVATE);

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
                // If we're connected, and connected to WiFi
                if (networkInf.isConnected() == true && networkInf.getType() == ConnectivityManager.TYPE_WIFI) {
                    // Here we POST the location data
                    String location = MainActivity.myGraph.getLocFromMac(firstMac);
                    JSONPost(location, dateString);

                    // Now to make sure we don't have any leftover faulty access point data
                    if (sharedPrefs.contains("faultlist")) {
                        //TODO - Implement posting for Faults using FaultDataBaseHelper

                    }
                }
            }


        }
    }
}
