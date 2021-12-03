package com.digitalcataway.accesssuperheroapi.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String API_BASE_URL_ALL = "https://akabab.github.io/superhero-api/api";
    //final static String API_BASE_URL_ID = "https://akabab.github.io/superhero-api/api/id/";

    public static URL buildUrl(String apiSearchQuery) throws MalformedURLException {

        Uri builUri = Uri.parse(API_BASE_URL_ALL).buildUpon()
                .appendPath(apiSearchQuery + ".json")
                .build();
        URL url = null;
        url = new URL(builUri.toString());
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();

        InputStream in = urlConnection.getInputStream();

        Scanner sc = new Scanner(in);
        sc.useDelimiter("\\A");

        try {
            boolean hasInput = sc.hasNext();

            if (hasInput) {
                return sc.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
