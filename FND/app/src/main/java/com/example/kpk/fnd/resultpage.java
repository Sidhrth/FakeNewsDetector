package com.example.kpk.fnd;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class resultpage extends AppCompatActivity {


// global declarations
    String jsonapistring;
    String Json_String;
//  String opJson_String;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Map<String[],Integer> Lintitl = new HashMap<>();
    ResultAdapter resultAdapter;
    ListView listView;
//  boolean firsttry = true;
//  boolean secondtry = false;
    boolean isUrl=false;
    boolean isLarge = false;
    String[] strlets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);


        // Display declarations NOT FUNCTIONAL
        /*
        resultAdapter = new ResultAdapter(this,R.layout.row_layout);
        listView = (ListView) findViewById(R.id.DispResults);
        listView.setAdapter(resultAdapter);
        */


        //gets input from previous activity
        Intent intent = getIntent();
        String str = intent.getStringExtra("EXTRA_MESSAGAE");

        //FOR TESTING API, Builds the URL
        JSONObject jsondata = new JSONObject();
        try {
            jsondata.put("input data from app", str);
            jsondata.put("google url","https://www.google.co.in/");
            jsonapistring = jsondata.toString();

            //log to show jsonstring
            Log.d("json string formed",jsonapistring);

            URL url = NetworkUtil.urlbuilding(jsonapistring);

            new PostAPItask().execute(url);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        //////NOT FUNCTIONAL
        // TO check whether input is an URL and if true executes function

        /*
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(str);
        if(m.find()) {
            Log.d("URL test","It is a URL");
            Log.d("Link Conditions","Entered");
            isUrl = true;
            try {

                final boolean[] status = new boolean[2];
                status[1] = true;
                String Title = new LinkTest().execute(str).get();

                Log.d("Title of URL",Title);

                URL CustomSearchUrl = NetworkUtil.buildUrl(Title);

                String opjson = new CustomQueryTask().execute(CustomSearchUrl).get();

                status[0] = ProcessJson(opjson);

                Log.d("Initial Status", String.valueOf(status[0]));

                if (!status[0])
                {
                    Log.d("results not found","open seacrch");
                    URL openCustomSearchUrl = NetworkUtil.buildUrlopensearch(Title);
                    Log.d("Improved Url",openCustomSearchUrl.toString());

                    String outputJson1 = new CustomQueryTask().execute(CustomSearchUrl).get();

                    status[1] = ProcessJson(outputJson1);
                    Log.d("opensearch Status", String.valueOf(status[1]));

                }
                if (!status[1])
                {
                    showToast();
                }




            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        else{
            Log.d("URL test","It is NOT a URL");
        }

       */


        // to check if input is very large and if true Splits them into sentences and stores them into an array

/*
        if(str.length() > 40 && !isUrl){
            final boolean[] status = new boolean[2];
           isLarge = true;
            strlets = str.split("\\.\\s*");
            Log.d("Using long expansion",">40");

            for (int i = 0; i < strlets.length; i++) {

                URL url = NetworkUtil.buildUrl(strlets[i]);
                Log.d("show URL", "final url :" + url.toString());
                try {
                    String opjson = new CustomQueryTask().execute(url).get();
                    status[0] = ProcessLargeJson(opjson);

                    if (!status[0]){
                        Log.d("results not found","open seacrch");
                        URL openCustomSearchUrl = NetworkUtil.buildUrlopensearch(strlets[i]);
                        Log.d("Improved Url",openCustomSearchUrl.toString());

                        String opjson2 = new CustomQueryTask().execute(openCustomSearchUrl).get();
                        ProcessLargeJson(opjson2);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            if (Lintitl.isEmpty()){
                showToast();
            }
            else {
                for (Map.Entry<String[], Integer> entry : Lintitl.entrySet()) {


                    if (entry.getValue() >= 1) {
                        String[] inf = entry.getKey();
                        resultinfo info = new resultinfo(inf[0], inf[1]);
                        resultAdapter.add(info);

                    }

                }

            }



        }
*/


//       OLD CODE not required
//
//        if (flag ==3) {
//
//            for (int i = 0; i < strlets.length; i++) {
//
//                URL url = NetworkUtil.buildUrl(strlets[i]);
//                temp = strlets[i];
//                Log.d("show URL", "final url :" + url.toString());
//                new CustomQueryTask().execute(url);
//
//            }
//
//        }






        // Normal execution of normal input conditions  ex. simple text(one sentence)
        /*

            if (!isLarge && !isUrl) {

                final boolean[] status = new boolean[2];
                status[1] = true;
                Log.d("Regular Conditions","entered");
                URL CustomSearchUrl = NetworkUtil.buildUrl(str);
                Log.d("search", "Searching for :" + str);
                Log.d("show URL", "final url :" + CustomSearchUrl.toString());
//                 new CustomQueryTask(new AsyncResponse() {
//                     @Override
//                     public void ProcessFinish(String op) {
//                          status[0] =ProcessJson(op);
//                         Log.d("status", String.valueOf(status[0]));
//                     }
//                 }).execute(CustomSearchUrl);
//
//                if(!status[0]){
//                    Log.d("results not found","open seacrch");
//                    URL openCustomSearchUrl = NetworkUtil.buildUrlopensearch(str);
//                    Log.d("Improved Url",openCustomSearchUrl.toString());
//                   new CustomQueryTask(new AsyncResponse() {
//                       @Override
//                       public void ProcessFinish(String op) {
//                           status[1]= ProcessJson(op);
//                       }
//                   }).execute(openCustomSearchUrl);
//                }
//
//                if (!status[1]){
//                    showToast();
//                }
                try {


                    String outputJson = new CustomQueryTask().execute(CustomSearchUrl).get();

                    status[0] = ProcessJson(outputJson);

                    Log.d("Initial Status", String.valueOf(status[0]));

                    if (!status[0])
                    {
                        Log.d("results not found","open seacrch");
                        URL openCustomSearchUrl = NetworkUtil.buildUrlopensearch(str);
                        Log.d("Improved Url",openCustomSearchUrl.toString());

                        String outputJson1 = new CustomQueryTask().execute(openCustomSearchUrl).get();

                        status[1] = ProcessJson(outputJson1);
                        Log.d("opensearch Status", String.valueOf(status[1]));

                    }
                    if (!status[1])
                    {
                        showToast();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }


            */
    }

    //Makes URL connection. posts data
    class PostAPItask extends AsyncTask<URL,Void,Void>
    {

        @Override
        protected Void doInBackground(URL... params) {
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) params[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }



    //////NOT FUNCTIONAL
    public void webdisp(View view) {

        TextView lnk = (TextView) findViewById(R.id.link);
        String url = lnk.getText().toString();
        Log.d("url for webview",url);
        Intent webintent = new Intent(this,webresult.class);
        webintent.putExtra("linkurl",url);
        startActivity(webintent);

    }

    //////NOT FUNCTIONAL
    class LinkTest extends AsyncTask<String,Void,String>{

        String Title;

        @Override
        protected String doInBackground(String... params) {

            Document doc = null;
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Title = doc.title();
//            Log.d("Title of URL",Title);
            return Title;

        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.d("Linktest Task","Entered");
//            URL CustomSearchUrl = NetworkUtil.buildUrl(Title);

        }
    }
    //////NOT FUNCTIONAL
    class CustomQueryTask extends AsyncTask<URL,Void,String>{



        //        public AsyncResponse obj = null;
//
//        public  CustomQueryTask(AsyncResponse asyncResponse){
//            obj = asyncResponse;
//        }


        //////NOT FUNCTIONAL
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


        //////NOT FUNCTIONAL
           @Override
           protected void onPostExecute(String res) {


//               boolean foundresult;
////               opJson_String = res;
//
//               obj.ProcessFinish(res);


//               if (!isLarge){
//                   foundresult = ProcessJson(opJson_String);
//
//                   if (!foundresult && !firsttry){
//                       opensearch();
//                   }
//
//                   if (!foundresult && secondtry){
//                       showToast();
//                   }
//
//               }
//               else {
//                   ProcessLargeJson(opJson_String);
//               }




//               if (flag == 3){
//
//                   ProcessLargeJson(opJson_String);
//
//                   if (flag2 == 1){
//
//                       URL urL = NetworkUtil.buildUrlopensearch(temp);
//                       new CustomQueryTask().execute(urL);
//                   }
//
//
//                   for (Map.Entry<String[],Integer> entry : Lintitl.entrySet()){
//
//
//                       if (entry.getValue()>2){
//                           String[] inf = entry.getKey();
//                           resultinfo info = new resultinfo(inf[0],inf[1]);
//                           resultAdapter.add(info);
//
//                       }
//
//                   }
//
//
//               }
//               if (flag == 0 || (flag == 1)) {
//                   flag = ProcessJson(opJson_String);
//               }
//
//               if (flag == 1) {
//
//                   Intent intent = getIntent();
//                   String str = intent.getStringExtra("EXTRA_MESSAGAE");
//                   URL CustomSearchUrl = NetworkUtil.buildUrlopensearch(str);
//
//                   new CustomQueryTask().execute(CustomSearchUrl);
//
//
//
//               }
//
//               if(flag == 2) {
//
//                   showToast();
//
//
//               }
//
//


           }
    }

//    private void opensearch (){
//
//                   Intent intent = getIntent();
//                   String str = intent.getStringExtra("EXTRA_MESSAGAE");
//                   URL CustomSearchUrl = NetworkUtil.buildUrlopensearch(str);
//
//                   new CustomQueryTask().execute(CustomSearchUrl);
//
//
//    }

    //////NOT FUNCTIONAL
    private boolean ProcessLargeJson(String opJson_string) {

        boolean resultfound = true;

        try {
            jsonObject = new JSONObject(opJson_string);
            jsonArray = new JSONObject(opJson_string).getJSONArray("items");
            int count = 0;
            String title,link;
            while (count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                title = JO.getString("title");
                link = JO.getString("link");

                String[] info = {title,link};

                if (Lintitl.containsKey(info)){

                Lintitl.put(info,Lintitl.get(info)+1);
                }
                else {
                    Lintitl.put(info, 1);
                }



                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();

            resultfound = false;

        }

        return resultfound;

    }

    private void showToast() {

        Toast.makeText(this,"Results not found",Toast.LENGTH_LONG).show();
    }

    //////NOT FUNCTIONAL
    public boolean ProcessJson(String opJson_String) {

        boolean resultfound = true;

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

            }

        } catch (JSONException e) {
            e.printStackTrace();
//            if (firsttry){
//                firsttry = false;
//                secondtry = false;
//            }
//            if (!firsttry){
//                secondtry = true;
//            }
//
//            resultfound = false;
            resultfound = false;
        }

        return resultfound;
    }

//    public interface AsyncResponse {
//        void ProcessFinish(String op);
//    }





}


