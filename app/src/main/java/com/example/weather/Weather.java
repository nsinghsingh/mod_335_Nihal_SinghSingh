package com.example.weather;

import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;

public class Weather {

    private String description;
    private String iconString;
    private double temperature;
    private LocalDateTime date;
    private TextView temperatureText;
    private TextView dateText;
    private ImageView weatherIcon;

    public Weather(TextView temperatureText, TextView dateText, ImageView weatherIcon){
        this.temperatureText = temperatureText;
        this.dateText = dateText;
        this.weatherIcon = weatherIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        weatherIcon.setContentDescription(description);
    }

    public String getIconString() {
        return iconString;
    }

    public void setIconString(String iconString) {
        this.iconString = iconString;
        switch(iconString){
            case "01d":
                weatherIcon.setImageResource(R.drawable.ic_day_clear);
                break;
            case "01n":
                weatherIcon.setImageResource(R.drawable.ic_night_clear);
                break;
            case "02d":
                weatherIcon.setImageResource(R.drawable.ic_sun_cloudy);
                break;
            case "02n":
                weatherIcon.setImageResource(R.drawable.ic_night_cloudy);
                break;
            case "03d":
            case "03n":
                weatherIcon.setImageResource(R.drawable.ic_cloudy);
                break;
            case "09d":
            case "09n":
            case "10n":
                weatherIcon.setImageResource(R.drawable.ic_raining);
                break;
            case "10d":
                weatherIcon.setImageResource(R.drawable.ic_sun_raining);
                break;
            case "11d":
            case "11n":
                weatherIcon.setImageResource(R.drawable.ic_lightning);
                break;
            case "13d":
            case "13n":
                weatherIcon.setImageResource(R.drawable.ic_snowing);
                break;
            case "50d":
            case "50n":
                weatherIcon.setImageResource(R.drawable.ic_mist);
                break;
            default:
                weatherIcon.setImageResource(R.drawable.ic_refresh);
                break;
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        temperature = temperature - 273.15;
        temperature = (int) (temperature * 10);
        temperature = temperature / 10;
        String temperatureString = temperature + " C°";
        temperatureText.setText(temperatureString);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
        if(dateText != null){
            int currentDay = LocalDateTime.now().getDayOfYear();
            int currentYear = LocalDateTime.now().getYear();
            if(date.getDayOfYear() > currentDay || date.getYear() > currentYear){
                String weekDay = date.getDayOfWeek().name();
                weekDay = weekDay.substring(0,1).toUpperCase() + weekDay.substring(1).toLowerCase();
                dateText.setText(weekDay);
            }
            else{
                String time;
                if(date.getMinute() < 10){
                    time = date.getHour() + ": 0" + date.getMinute();
                }
                else{
                    time = date.getHour() + ":" + date.getMinute();
                }
                dateText.setText(time);
            }
        }
    }

    public TextView getTemperatureText() {
        return temperatureText;
    }

    public void setTemperatureText(TextView temperatureText) {
        this.temperatureText = temperatureText;
    }

    public TextView getDateText() {
        return dateText;
    }

    public void setDateText(TextView dateText) {
        this.dateText = dateText;
    }

    public ImageView getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(ImageView weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
