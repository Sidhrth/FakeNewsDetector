package com.example.kpk.fnd;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by KPK on 21-06-2017.
 */

class NetworkUtil {

    public static URL buildUrl(String CustomSearchQuery) {
        // looking for
        String strNoSpaces = CustomSearchQuery.replace(" ", "+");

        // Your API key
        String key = "AIzaSyCAajBXGsQKg4FDMBF2Lc1ffl1UtYP5Fzg";

        // Your Search Engine ID
        String cx = "013670998315178887075:tstnn39qzcq";

        String url2 = "https://www.googleapis.com/customsearch/v1?q=" + strNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";

        URL url = null;

        try {
            url = new URL(url2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
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

    public static String httpGet(URL url) throws IOException {



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


}
