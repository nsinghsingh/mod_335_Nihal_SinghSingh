package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private double latitude;
    private double longitude;
    private Weather currentWeather;
    private Weather[] hourlyWeather = new Weather[6];
    private Weather[] dailyWeather = new Weather[5];
    private String city;
    private LocalDateTime lastUpdate;
    private LocationManager locationManager;

    TextView cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = findViewById(R.id.city);
        TextView temperatureText = findViewById(R.id.temperature);
        ImageView weatherIcon = findViewById(R.id.weatherIcon);
        currentWeather = new Weather(temperatureText,null, weatherIcon);
        for (int i = 0; i < 6; i++){
            int id = getResources().getIdentifier("hour" + (i+1), "id", getPackageName());
            TextView hour = findViewById(id);
            id = getResources().getIdentifier("hourlyWeatherIcon" + (i+1), "id", getPackageName());
            ImageView hourlyWeatherIcon = findViewById(id);
            id = getResources().getIdentifier("hourlyTemperature" + (i+1), "id", getPackageName());
            TextView hourlyTemperature = findViewById(id);
            hourlyWeather[i] = new Weather(hourlyTemperature, hour, hourlyWeatherIcon);
        }
        for (int i = 0; i < 5; i++){
            int id = getResources().getIdentifier("day" + (i+1), "id", getPackageName());
            TextView day = findViewById(id);
            id = getResources().getIdentifier("dayWeatherIcon" + (i+1), "id", getPackageName());
            ImageView dailyWeatherIcon = findViewById(id);
            id = getResources().getIdentifier("dailyTemperature" + (i+1), "id", getPackageName());
            TextView dailyTemperature = findViewById(id);
            dailyWeather[i] = new Weather(dailyTemperature, day, dailyWeatherIcon);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        int canAccessFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int canAccessCoarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int isGranted = PackageManager.PERMISSION_GRANTED;
        if (canAccessFineLocation != isGranted && canAccessCoarseLocation != isGranted) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        new GetInfo().execute(Double.toString(latitude), Double.toString(longitude));
    }

    private void showWeather(String jsonString){

        city = rootObj.get("name").getAsString();
        cityText.setText(city);
        double temperature = main.get("temperature").getAsDouble();
        currentWeather.setTemperature(temperature);
        String weatherIcon = weather.get("icon").getAsString();
        currentWeather.setIconString(weatherIcon);
        lastUpdate = LocalDateTime.now();
        for (int i = 0; i < 6; i++){
            LocalDateTime newHour = LocalDateTime.now();
            newHour.plusMinutes(i);
            hourlyWeather[i].setDate(newHour);
            hourlyWeather[i].setIconString(currentWeather.getIconString());
            hourlyWeather[i].setTemperature(currentWeather.getTemperature());
        }
        for (int i = 0; i < 5; i++){
            LocalDateTime newDay = LocalDateTime.now();
            newDay.plusDays(i);
            dailyWeather[i].setDate(newDay);
            dailyWeather[i].setIconString(currentWeather.getIconString());
            dailyWeather[i].setTemperature(currentWeather.getTemperature());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private class GetInfo extends AsyncTask<String,Void,String> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Getting weather...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Connection connection = new Connection();
            String url = connection.apiRequest(params[0], params[1]);
            return connection.getHttpData(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showWeather(s);
        }
    }
}
