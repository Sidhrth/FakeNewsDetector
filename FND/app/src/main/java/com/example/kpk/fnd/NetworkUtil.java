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


}
