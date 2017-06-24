package com.example.krishnakaliappan.test4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

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
        Log.d("search", "Searching for :" + str);
         ListView Result = (ListView) findViewById(R.id.results);



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
//                    Log.d("json string",result2);
                    JSONObject obj = new JSONObject(result2);
                    JSONObject query = obj.getJSONObject("queries");
                    int nresults = query.getInt("count");
                    Log.d("number of results", String.valueOf(nresults));
                  //  JSONArray items = obj.getJSONArray("items");


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

}




