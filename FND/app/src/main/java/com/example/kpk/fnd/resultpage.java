package com.example.kpk.fnd;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;



public class resultpage extends AppCompatActivity {

    TextView Result = (TextView) findViewById(R.id.results);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);

        Intent intent = getIntent();
        final String str = intent.getStringExtra("EXTRA_MESSAGAE");


        URL CustomSearchUrl = NetworkUtil.buildUrl(str);
        Log.d("search", "Searching for :" + str);
        Log.d("show URL", "final url :" + CustomSearchUrl.toString());

        new CustomQueryTask().execute(CustomSearchUrl);


    }

    public class CustomQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String CustomSearchResults = null;
            try {
                CustomSearchResults = NetworkUtil.httpGet(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return CustomSearchResults;
        }

//        @Override
//        protected void onPostExecute(String CustomSearchResults) {
//            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
//            if (CustomSearchResults != null && !CustomSearchResults.equals("")) {
//                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
//                showJsonDataView();
//                Result.setText(CustomSearchResults);
//            } else {
//                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
//                showErrorMessage();
//            }
//        }
    }

}
