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
        weatherIcon.setImageResource(R.drawable.ic_lightningstorm);
        //TODO
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        String temperatureString = Double.toString(temperature);
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
                String time = date.getHour() + ":" + date.getMinute();
                dateText.setText(time);
            }
            else{
                dateText.setText(date.getDayOfWeek().name());
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
