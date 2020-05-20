package com.example.weather;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    static String stream = null;
    public static String API_KEY = "2e9ec911708bc2e79bc9cad8910b8056";
    public static String API_LINK = "https://api.openweathermap.org/data/2.5/weather";

    public String getHttpData(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            int response = connection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                int line;
                while((line = reader.read()) != -1){ // -1 equals null
                    stringBuilder.append(line);
                }
                stream = stringBuilder.toString();
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }

    @NotNull
    public String apiRequest(String latitude, String longitude){
        return API_LINK + String.format("?lat=%s&lon=%s&appid=%s", latitude, longitude, API_KEY);
    }
}
