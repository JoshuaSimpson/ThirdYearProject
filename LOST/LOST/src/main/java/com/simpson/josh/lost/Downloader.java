package com.simpson.josh.lost;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Josh on 29/03/2015.
 */
public class Downloader extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... application) {

        try {
            URL url = new URL("http://178.62.73.203/dbs/get");

            //Create a connection file based on the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);

            //Start that connection
            connection.connect();

            //Set download location
            File location = new File(application[0]);
            Log.d("Where is this downloading to?", "No seriously," + location.getPath().toString());

            //Set download name
            File downloadedFile = new File(location, "LOSTANDFOUND");

            FileOutputStream fo = new FileOutputStream(downloadedFile);

            InputStream is = connection.getInputStream();

            //Total size of file we're downloading
            int fileSize = connection.getContentLength();

            //File buffer yo
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = is.read(buffer)) > 0) {
                fo.write(buffer, 0, bufferLength);
            }

            fo.close();

            if (downloadedFile.exists()) {
                Log.d("FUCKING DEBUGGING,", downloadedFile.getAbsolutePath());
            }
        } catch (final MalformedURLException m) {
            Log.d("Fuck", m.toString());
        } catch (final IOException i) {
            Log.d("Fuckit", i.toString());
        } catch (Exception e) {
            Log.d("Something went wrong", e.toString());
        }

        return "done";
    }

    protected void onPostExecute(String message) {
        Log.d("Just posting it here", "Awesome");
    }
}
