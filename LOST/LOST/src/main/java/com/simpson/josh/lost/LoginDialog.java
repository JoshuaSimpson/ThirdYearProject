package com.simpson.josh.lost;

import android.app.Activity;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class LoginDialog extends Activity {

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
            @Override
            public void onClick(View view) {
                Log.d("6 and a half thousand"," fucking. Miles.");

                
                
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
