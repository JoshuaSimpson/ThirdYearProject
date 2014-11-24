package com.simpson.lost;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WhereAmI extends Activity{

	WifiManager wifi;       
    ListView lv;
    TextView textStatus;
    Button buttonScan;
    int size = 0;
    List<ScanResult> results;
    BroadcastReceiver wifiReceiver;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_where_am_i);
		
		DataBase db = new DataBase(this);
		db.getReadableDatabase();
		
		TextView tv = (TextView) findViewById(R.id.textField);
		Location location = db.getLocation("6247157");
		tv.setText(location.locName());
		
		
		// Wifi start here
		
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if ( wifi.isWifiEnabled() == true )
		{
			wifi.setWifiEnabled(true);
		}
		
		registerReceiver(new BroadcastReceiver()
		{

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				results = wifi.getScanResults();
				size = results.size();
				Toast.makeText(getApplicationContext(), Integer.toString(results.get(0).level), Toast.LENGTH_LONG).show();
				context.unregisterReceiver(this);
			}
			
		}, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
