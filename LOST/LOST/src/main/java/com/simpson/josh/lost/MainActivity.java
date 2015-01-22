package com.simpson.josh.lost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.sqlite.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nothing much doing here...
    }

    public void whereAmI(View view)
    {
        Intent openView = new Intent(this, WhereAmI.class);
        startActivity(openView);
    }
    }
