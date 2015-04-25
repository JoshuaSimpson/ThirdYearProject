package com.simpson.josh.lost;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class LoginDialog extends settings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);


        Button close_button = (Button) findViewById(R.id.dismiss);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button setUp = (Button) findViewById(R.id.addWiFi);
        setUp.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {

                TextView username = (TextView)findViewById(R.id.usernameBox);
                TextView pwd = (TextView)findViewById(R.id.passwordBox);

                WifiConfiguration wifiConfig = new WifiConfiguration();
                WifiEnterpriseConfig epConfig = new WifiEnterpriseConfig();

                WifiManager wfm = (WifiManager)getSystemService(Context.WIFI_SERVICE);

                //Block to set up AccessKings
                wifiConfig.SSID = "AccessKings";
                wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
                wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
                epConfig.setIdentity(username.getText().toString());
                epConfig.setPassword(pwd.getText().toString());
                epConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);

                wifiConfig.enterpriseConfig = epConfig;

                wfm.addNetwork(wifiConfig);

                //Modify a little bit to set up eduroam
                wifiConfig.SSID = "eduroam";
                epConfig.setIdentity(username.getText().toString() + "@kclad.ds.kcl.ac.uk");
                wifiConfig.enterpriseConfig = epConfig;

                wfm.addNetwork(wifiConfig);

                finish();
                }
        });

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = 800;
        params.width = 800;
        params.y = 0;

        this.getWindow().setAttributes(params);
    }


}
