package com.example.krishnakaliappan.test4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class resultpage extends AppCompatActivity {

     public ProgressBar mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
     public TextView Result = (TextView) findViewById(R.id.results);
     public TextView Errormessage = (TextView) findViewById(R.id.error_message_display);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);

        Intent intent = getIntent();
        String str = intent.getStringExtra("EXTRA_MESSAGE");
        URL CustomSearchUrl = NetworkUtil.buildUrl(str);

        new CustomQueryTask().execute(CustomSearchUrl);


    }


    private void showJsonDataView() {
        // First, make sure the error is invisible
        Errormessage.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        Result.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // First, hide the currently visible data
        Result.setVisibility(View.INVISIBLE);
        // Then, show the error
        Errormessage.setVisibility(View.VISIBLE);
    }


    public class CustomQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String CustomSearchResults = null;
            try {
                CustomSearchResults = NetworkUtil.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return CustomSearchResults;
        }

        @Override
        protected void onPostExecute(String CustomSearchResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (CustomSearchResults != null && !CustomSearchResults.equals("")) {
                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                showJsonDataView();
                Result.setText(CustomSearchResults);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
            }
        }
    }
}




