package com.example.homelibrary;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//TODO: Create Callback Class
public class GoogleBooksApiRequestRunnable implements Runnable{
    private String query;
    private String key;
    private final String apiKey = "AIzaSyDEZ3gzCi8aoPv2OVsYajBXXOs-Id_14I4";
    private String uri;

    @Override
    public void run() {
        onPreExecute();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
        } catch (MalformedURLException e) {
            //TODO: handle exception
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int responseCode = 0;
        try {
            assert connection != null;
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        if(responseCode != 200){
            Log.w(getClass().getName(), "GoogleBooksAPI request failed. Response Code: " + responseCode);
            connection.disconnect();
            return;
        }

        // Read data from response
        StringBuilder builder = new StringBuilder();
        BufferedReader responseReader = null;
        try {
            responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String line = null;
        try {
            line = responseReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (line != null) {
            builder.append(line);
            try {
                line = responseReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String responseString = builder.toString();
        Log.d(getClass().getName(), "Response String: " + responseString);
        try {
            JSONObject responseJson = new JSONObject(responseString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        connection.disconnect();
    }

    private void onPreExecute() {

    }

    public GoogleBooksApiRequestRunnable(String key, String query) {
        this.query = query;
        this.key = key;
        this.uri = "https://www.googleapis.com/books/v1/volumes?q="+ key + ":" + query + "&key=" + apiKey;
    }
}
