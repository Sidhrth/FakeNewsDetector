package com.example.kpk.fnd;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
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

    JSONObject jsonObject;

    JSONArray jsonArray;

    ResultAdapter resultAdapter;

    ListView listView;
    int flag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_resultpage);

        resultAdapter = new ResultAdapter(this,R.layout.row_layout);
        listView = (ListView) findViewById(R.id.DispResults);
        listView.setAdapter(resultAdapter);
        Intent intent = getIntent();
        String str = intent.getStringExtra("EXTRA_MESSAGAE");


        URL CustomSearchUrl = NetworkUtil.buildUrl(str);
        Log.d("search", "Searching for :" + str);
        Log.d("show URL", "final url :" + CustomSearchUrl.toString());

        new CustomQueryTask().execute(CustomSearchUrl);



    }

    public void webdisp(View view) {

        TextView lnk = (TextView) findViewById(R.id.link);
        String url = lnk.getText().toString();
        Log.d("url for webview",url);
        Intent webintent = new Intent(this,webresult.class);
        webintent.putExtra("linkurl",url);
        startActivity(webintent);

    }

    class CustomQueryTask extends AsyncTask<URL,Void,String>{

           @Override
           protected String doInBackground(URL... urls) {

               try {


                   HttpURLConnection httpURLConnection = (HttpURLConnection) urls[0].openConnection();
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
               flag = ProcessJson(opJson_String);

               if (flag == 1) {

                   Intent intent = getIntent();
                   String str = intent.getStringExtra("EXTRA_MESSAGAE");
                   URL CustomSearchUrl = NetworkUtil.buildUrlopensearch(str);

                   new CustomQueryTask().execute(CustomSearchUrl);



               }










           }
    }

    public int ProcessJson(String jsonstring) {

        try {
            jsonObject = new JSONObject(opJson_String);
            jsonArray = new JSONObject(opJson_String).getJSONArray("items");
            int count = 0;
            String title,link;
            while (count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                title = JO.getString("title");
                link = JO.getString("link");

                resultinfo info = new resultinfo(title,link);
                resultAdapter.add(info);

                count++;

                flag = 0;

            }

        } catch (JSONException e) {
            e.printStackTrace();
            flag = 1;
        }

        return flag;
    }







}


