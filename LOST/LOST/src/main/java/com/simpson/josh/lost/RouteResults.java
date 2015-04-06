package com.simpson.josh.lost;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

// http://www.icons4android.com/iconset/29 - Thank them for the Icon Set.
public class RouteResults extends Activity {

    String[] path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_results);
        path = this.getIntent().getStringArrayExtra("Path");

        String myString = "";

        // Attach to tablelayout, and inflate the table rows
        TableLayout table = (TableLayout) findViewById(R.id.resultsTable);
        for (int i = 0; i < path.length; i++) {
            TableRow row = (TableRow) LayoutInflater.from(RouteResults.this).inflate(R.layout.inflatable_table_row, null);
            ImageView iv = (ImageView) row.findViewById(R.id.statusIcon);
            Log.d("Trying here", "Seriously");

            // Set icon depending on the status
            if (i == 0) {
                iv.setImageResource(R.drawable.ic_en_route);
            } else if (i == path.length - 1) {
                iv.setImageResource(R.drawable.ic_fin);
            } else {
                iv.setImageResource(R.drawable.ic_action);
            }


            TextView instructionText = (TextView) row.findViewById(R.id.instruction);
            instructionText.setText(path[i]);
            table.addView(row);
        }
    }
}
