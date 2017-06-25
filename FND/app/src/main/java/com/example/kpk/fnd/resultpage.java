package com.example.kpk.fnd;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class resultpage extends AppCompatActivity {

    String Json_String;

    String opJson_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);

        Intent intent = getIntent();
        String str = intent.getStringExtra("EXTRA_MESSAGAE");


        URL CustomSearchUrl = NetworkUtil.buildUrl(str);
        Log.d("search", "Searching for :" + str);
        Log.d("show URL", "final url :" + CustomSearchUrl.toString());

        new CustomQueryTask().execute();


    }

       class CustomQueryTask extends AsyncTask<Void,Void,String>{

           @Override
           protected String doInBackground(Void... params) {

               try {
                   Intent intent = getIntent();
                   String str = intent.getStringExtra("EXTRA_MESSAGAE");

                   URL CustomSearchUrl = NetworkUtil.buildUrl(str);
                   HttpURLConnection httpURLConnection = (HttpURLConnection) CustomSearchUrl.openConnection();
                   InputStream inputStream = httpURLConnection.getInputStream();
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                   StringBuilder stringBuilder = new StringBuilder();
                   while ((Json_String = bufferedReader.readLine())!= null){

                       stringBuilder.append(Json_String+"\n");


                   }

                   bufferedReader.close();
                   inputStream.close();
                   httpURLConnection.disconnect();

                   return stringBuilder.toString().trim();

               } catch (IOException e) {
                   e.printStackTrace();
               }


                return null;
           }

           @Override
           protected void onPostExecute(String res) {

               opJson_String = res;

           }
       }
}


