package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nothing much doing here...


    }

    public void whereAmI(View view)
    {
        Log.d("STUFF HERE", getFilesDir().getPath().toString());
        Intent openView = new Intent(this, WhereAmI.class);
        startActivity(openView);
    }

    public void routeFinder(View view) {
        Intent openView = new Intent(this, RouteFinder.class);
        startActivity(openView);
    }
    }
