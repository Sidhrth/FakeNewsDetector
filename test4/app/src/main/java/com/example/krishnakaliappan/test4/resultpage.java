package com.example.krishnakaliappan.test4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
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
        String str = intent.getStringExtra("EXTRA_MESSAGE");

        

        try {


            // looking for
            String strNoSpaces = str.replace(" ", "+");

            // Your API key
            String key="AIzaSyCAajBXGsQKg4FDMBF2Lc1ffl1UtYP5Fzg";

            // Your Search Engine ID
            String cx = "013670998315178887075:tstnn39qzcq";

            String url2 = "https://www.googleapis.com/customsearch/v1?q=" + strNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";

            URL url = null;

            try {
                url = new URL(url2);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String result2 = getResponseFromHttpUrl(url);

            result.setText(result2);

        }
        catch(Exception e) {
            System.out.println("Error1 " + e.getMessage());
        }

    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
    }

        class CustomSearchTask extends AsyncTask<URL, Void, String> {

            // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(URL... params) {
                URL searchUrl = params[0];
                String githubSearchResults = null;
                try {
                    githubSearchResults = getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return githubSearchResults;
            }

            @Override
            protected void onPostExecute(String githubSearchResults) {
                // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (githubSearchResults != null && !githubSearchResults.equals("")) {
                    // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                    showJsonDataView();
                    mSearchResultsTextView.setText(githubSearchResults);
                } else {
                    // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                    showErrorMessage();
                }
            }
        }


