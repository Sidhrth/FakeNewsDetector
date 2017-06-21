package com.example.krishnakaliappan.test4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class resultpage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);

        Intent intent = getIntent();
        final String str = intent.getStringExtra("EXTRA_MESSAGAE");
//        URL CustomSearchUrl = NetworkUtil.buildUrl(str);
        Log.d("search", "Searching for :" + str);
//        new CustomQueryTask().execute(CustomSearchUrl);
//         ProgressBar mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        final TextView Result = (TextView) findViewById(R.id.results);
//         TextView Errormessage = (TextView) findViewById(R.id.error_message_display);




//
//
 Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try {


                    // looking for
                    String strNoSpaces = str.replace(" ", "+");

                    // Your API key
                    String key="AIzaSyCAajBXGsQKg4FDMBF2Lc1ffl1UtYP5Fzg";

                    // Your Search Engine ID
                    String cx = "013670998315178887075:tstnn39qzcq";

                    String url2 = "https://www.googleapis.com/customsearch/v1?q=" + strNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";

                    String result2 = httpGet(url2);

                    Result.setText(result2);

                }
                catch(Exception e) {
                    System.out.println("Error1 " + e.getMessage());
                }

            }


            private String httpGet(String urlStr) throws IOException {

                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if(conn.getResponseCode() != 200) {
                    throw new IOException(conn.getResponseMessage());
                }

                Log.d("search", "Connection status = " + conn.getResponseMessage());

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = rd.readLine()) != null) {

                    Log.d("search", "Line =" + rd.readLine());
                    sb.append(line+"\n");

                }
                rd.close();

                conn.disconnect();
                return sb.toString();
            }
        });

        thread.start();

    }


//    private void showJsonDataView() {
//        // First, make sure the error is invisible
//        Errormessage.setVisibility(View.INVISIBLE);
//        // Then, make sure the JSON data is visible
//        Result.setVisibility(View.VISIBLE);
//    }
//
//    private void showErrorMessage() {
//        // First, hide the currently visible data
//        Result.setVisibility(View.INVISIBLE);
//        // Then, show the error
//        Errormessage.setVisibility(View.VISIBLE);
//    }
//
//
//    public class CustomQueryTask extends AsyncTask<URL, Void, String> {
//
//        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(URL... params) {
//            URL searchUrl = params[0];
//            String CustomSearchResults = null;
//            try {
//                CustomSearchResults = NetworkUtil.httpGet(searchUrl);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return CustomSearchResults;
//        }
//
//        @Override
//        protected void onPostExecute(String CustomSearchResults) {
//            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
//            if (CustomSearchResults != null && !CustomSearchResults.equals("")) {
//                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
//                showJsonDataView();
//               Result.setText(CustomSearchResults);
//            } else {
//                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
//                showErrorMessage();
//            }
//        }
//    }
}




