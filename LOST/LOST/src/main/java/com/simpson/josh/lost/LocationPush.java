package com.simpson.josh.lost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import java.io.IOException;
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
    List<ScanResult> results;
    wifiScanReceiver scanReceiver;


    public void onReceive(Context context, Intent intent) {
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        scanReceiver = new wifiScanReceiver();
        wifi.startScan();

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();

        //Consider calling JSONPOST with certain parameters, or whether to put this code into JSONPost itself - probably the former

        //Also need to consider the various types of 'connected' we will be


        Log.d("Something happened", "Which is good");
        Toast.makeText(context, "Stuff happened", Toast.LENGTH_SHORT).show();

        try {
            JSONPost();
        } catch (JSONException j) {
            Toast.makeText(context, "It tried and failed", Toast.LENGTH_LONG).show();
        } catch (IOException i) {
            Toast.makeText(context, "IO U an apology for fuckin' up", Toast.LENGTH_LONG).show();
        }

    }

    public void JSONPost() throws JSONException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Date d = new Date();

                String dateString = DateFormat.getInstance().format(d.getTime());

                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj.put("mac", "Receiver");
                    jsonobj.put("location", "Testing");
                    jsonobj.put("time", dateString);
                    jsonobj.put("notes", "lol");
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

            //New comparator so that we can get the top three WiFi points
            Comparator<ScanResult> resultComparator = new Comparator<ScanResult>() {
                @Override
                public int compare(ScanResult lhs, ScanResult rhs) {
                    return (lhs.level > rhs.level ? -1 : (lhs.level == rhs.level ? 0 : 1));
                }
            };
            //Sort 'dem WiFis
            Collections.sort(results, resultComparator);

            MainActivity.myGraph.containsMAC(results.get(0).BSSID.toString());
        }
    }
}
