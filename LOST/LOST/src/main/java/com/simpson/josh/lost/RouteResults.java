package com.simpson.josh.lost;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

// http://www.icons4android.com/iconset/29 - Thank them for the Icon Set.
public class RouteResults extends ActionBarActivity {

    String[] path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_results);
        path = this.getIntent().getStringArrayExtra("Path");

        String myString = "";

        // Attach to tablelayout, and inflate the table rows
        TableLayout table = (TableLayout)findViewById(R.id.resultsTable);
        for( int i = 0 ; i < path.length ; i++)
        {
            TableRow row = (TableRow) LayoutInflater.from(RouteResults.this).inflate(R.layout.inflatable_table_row, null);
            ImageView iv = (ImageView)row.findViewById(R.id.statusIcon);
            Log.d("Trying here", "Seriously");

            // Set icon depending on the status
            if(i == 0)
            {
                iv.setImageResource(R.drawable.ic_en_route);
            }
            else if(i == path.length -1) {
                iv.setImageResource(R.drawable.ic_fin);
            }


            TextView instructionText = (TextView)row.findViewById(R.id.instruction);
            instructionText.setText(path[i]);
            table.addView(row);
        }
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
