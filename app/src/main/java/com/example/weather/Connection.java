package com.example.weather;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Connection {

    static String stream = null;
    public static String API_KEY = "2e9ec911708bc2e79bc9cad8910b8056";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

    public static String getHttpData(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            if(connection.getResponseCode() == 200){ // OK = 200
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
    public static String apiRequest(String latitude, String longitude){
        return API_LINK + String.format("?lat=%s&lon=%s&APPid=%s&units=metric", latitude, longitude, API_KEY);
    }

    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat =  new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long) unixTimeStamp * 1000);
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png", icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
