package com.simpson.josh.lost;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class RouteResults extends ActionBarActivity {

    String[] path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_results);
        path = this.getIntent().getStringArrayExtra("Path");
        TextView tv = (TextView)findViewById(R.id.path);

        String myString = "";

        for (int i = 0 ; i < path.length ; i++)
        {
            if(i == 0)
            {
                myString += "Start at: " + path[i] + ", then \n";
            }
            else
            {
                myString += "Go to: " + path[i] + ", then \n";
            }
        }

        tv.setText(myString);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
