package com.example.krishnakaliappan.test4;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by KPK on 15-06-2017.
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
}
