package com.example.assignment_1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue mRequestQueue;
    static WeatherProperties weatherProperties = null;
    ArrayList <WeatherDetails> data = new ArrayList<>();
    TextView textView_main_temprature;
    TextView textView_weatherDesc;
    ImageView imageView_weatherimae;
    WeatherDetails wd;
    RecyclerView recyclerView;
    MyAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRequestQueue = Volley.newRequestQueue(this);
        fetchJsonResponse();

        textView_main_temprature = findViewById(R.id.temprature);
        imageView_weatherimae = findViewById(R.id.weather_image);
        textView_weatherDesc = findViewById(R.id.weather_desc);


    }


////////////////////////////////////////////////////////////Volley////////////////////////////////////////////
    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/weather?q=Lahore&appid=5f656a537fd004b2ea371b4ed80796a6\n", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        weatherProperties = gson.fromJson(response.toString(),WeatherProperties.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UISetter(weatherProperties);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.i("ErrorLog", error.getMessage());
                Toast.makeText(MainActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    ////////////////////////////////////////////////////////////////Setting all data to UI/////////////////////////////////////////////
    public void UISetter(WeatherProperties weatherProperties){
        weatherProperties.getMain().setTemp(weatherProperties.getMain().getTemp() - 273.15);
        textView_main_temprature.setText(weatherProperties.getMain().getTemp().toString() + "℃");
        textView_weatherDesc.setText( weatherProperties.getWeather().get(0).getDescription().toUpperCase());

        String ImageLink = "https://openweathermap.org/img/wn/";
        ImageLink = ImageLink + weatherProperties.getWeather().get(0).getIcon() + "@2x.png";
        Glide.with(this).load(ImageLink).into(imageView_weatherimae);

        wd = new WeatherDetails(R.drawable.sunrise,"Sunrise",weatherProperties.getSys().getSunrise().toString());
        data.add(wd);
        wd = new WeatherDetails(R.drawable.sunset,"Sunset",weatherProperties.getSys().getSunset().toString());
        data.add(wd);
        wd = new WeatherDetails(R.drawable.humidity,"Humidity",weatherProperties.getMain().getHumidity().toString());
        data.add(wd);
        wd = new WeatherDetails(R.drawable.pressure,"Pressure",weatherProperties.getMain().getPressure().toString());
        data.add(wd);
        wd = new WeatherDetails(R.drawable.windspeed,"WindSpeed",weatherProperties.getWind().getSpeed().toString());
        data.add(wd);

        ////////////////////////////////////////////// Setting Adapter///////////////////////////////////////
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this,data);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}