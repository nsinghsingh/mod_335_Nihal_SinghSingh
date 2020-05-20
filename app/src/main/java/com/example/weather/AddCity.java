package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.weather.Model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.List;

public class AddCity extends AppCompatActivity {

    private List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ScrollView scrollView = findViewById(R.id.list);
        /*readFile();
        for (final City city: cities) {
            Button button = new Button(this);
            button.setText(city.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writeFile(v, city.getName(), city.getCoord().getLat(), city.getCoord().getLon());
                }
            });
            scrollView.addView(button);
        }*/
    }

    public void readFile(){
        if(isExternalStorageReadable()){
            File file = new File("app/src/main/assets/city.list.json");
            System.out.println(file.getAbsolutePath());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder stringBuilder = new StringBuilder();
                int line;
                while((line = reader.read()) != -1){ // -1 equals null
                    stringBuilder.append((char)line);
                }
                Gson gson = new Gson();
                Type model = new TypeToken<List<City>>(){}.getType();
                cities = gson.fromJson(stringBuilder.toString(), model);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile(View v, String city, double latitude, double longitude){
        int canAccessExternalStorage = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermission = PackageManager.PERMISSION_GRANTED;
        if(isExternalStorageWritable() && canAccessExternalStorage == hasPermission){
            File textFile = new File(Environment.getExternalStorageDirectory(), "favorites.txt");
            try {
                FileOutputStream fos = new FileOutputStream(textFile);
                byte[] cityArray = city.getBytes();
                byte[] latArray = Double.toString(latitude).getBytes();
                byte[] longArray = Double.toString(longitude).getBytes();
                byte[] allByteArray = new byte[cityArray.length + latArray.length + longArray.length + 2];
                ByteBuffer buff = ByteBuffer.wrap(allByteArray);
                buff.put(cityArray);
                buff.put(",".getBytes());
                buff.put(latArray);
                buff.put(",".getBytes());
                buff.put(longArray);
                byte[] combined = buff.array();
                fos.write(combined);
                fos.close();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
