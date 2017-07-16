package com.example.kpk.fnd;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class resultpage extends AppCompatActivity {

    String Json_String;

    String opJson_String;

    JSONObject jsonObject;

    JSONArray jsonArray;
    Map<String[],Integer> Ltit = new HashMap<>();
    ResultAdapter resultAdapter;
    String Title;
    ListView listView;
    int flag = 0;
    String[] strlets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);

        resultAdapter = new ResultAdapter(this,R.layout.row_layout);
        listView = (ListView) findViewById(R.id.DispResults);
        listView.setAdapter(resultAdapter);
        Intent intent = getIntent();
        String str = intent.getStringExtra("EXTRA_MESSAGAE");

        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(str);
        if(m.find()) {
            Log.d("URL test","It is a URL");

            flag = 4;
            new LinkTest().execute(str);

        }
        else {
            Log.d("URL test","It is NOT a URL");
        }

        if(str.length()> 40){
           flag = 3;
            strlets = str.split("\\.\\s*");

            Log.d("strlets", strlets[0]);
        }






        if (flag ==3) {

            for (int i = 0; i < strlets.length; i++) {

                URL url = NetworkUtil.buildUrl(strlets[i]);
                new CustomQueryTask().execute(url);

            }

        }



            if (flag == 0) {

                URL CustomSearchUrl = NetworkUtil.buildUrl(str);
                Log.d("search", "Searching for :" + str);
                Log.d("show URL", "final url :" + CustomSearchUrl.toString());


                new CustomQueryTask().execute(CustomSearchUrl);


            }



    }

    public void webdisp(View view) {

        TextView lnk = (TextView) findViewById(R.id.link);
        String url = lnk.getText().toString();
        Log.d("url for webview",url);
        Intent webintent = new Intent(this,webresult.class);
        webintent.putExtra("linkurl",url);
        startActivity(webintent);

    }

    class LinkTest extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {

            Document doc = null;
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Title = doc.title();
            Log.d("Title of URL",Title);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            URL CustomSearchUrl = NetworkUtil.buildUrl(Title);
            new CustomQueryTask().execute(CustomSearchUrl);

        }
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

               if (flag == 3){
                   ProcessLargeJson(opJson_String);



               }
               if (flag == 0 || flag == 1 || flag == 4) {
                   flag = ProcessJson(opJson_String);
               }

               if (flag == 1) {

                   Intent intent = getIntent();
                   String str = intent.getStringExtra("EXTRA_MESSAGAE");
                   URL CustomSearchUrl = NetworkUtil.buildUrlopensearch(str);

                   new CustomQueryTask().execute(CustomSearchUrl);



               }

               if(flag == 2) {

                   showToast();


               }




           }
    }

    private void ProcessLargeJson(String opJson_string) {
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



                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void showToast() {

        Toast.makeText(this,"Results not found",Toast.LENGTH_LONG).show();
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
            if (flag ==1){
                flag = 2;
            }
            else {
                flag = 1;
            }
        }

        return flag;
    }







}


