package com.example.assignment_1;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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
    ArrayList <String> data = new ArrayList<>();
    TextView textView_main_temprature;
    TextView textView_weatherDesc;
    ImageView imageView_weatherimae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        fetchJsonResponse();
        Log.i("LALA", "onCreate: From create");

        textView_main_temprature = findViewById(R.id.temprature);
        imageView_weatherimae = findViewById(R.id.weather_image);
        textView_weatherDesc = findViewById(R.id.weather_desc);
      //  if (weatherProperties != null)
           //  textView_main_temprature.setText(weatherProperties.getMain().getTemp().toString());


        data.add("Sunrise");
        data.add("Sunset");
        data.add("Temprature");
        ////////////////////////////////////////////// Setting Adapter///////////////////////////////////////
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter adapter = new MyAdapter(this,data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }


////////////////////////////////////////////////////////////Volley////////////////////////////////////////////
    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/weather?q=Lahore&appid=5f656a537fd004b2ea371b4ed80796a6\n", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("LALA", "onCreate: From FUNC");
                        Gson gson = new Gson();
                        weatherProperties = gson.fromJson(response.toString(),WeatherProperties.class);
                        Log.i("VENOM",weatherProperties.getMain().getTemp().toString());
                        Toast.makeText(MainActivity.this, "Working", Toast.LENGTH_SHORT).show();
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
                Log.i("Errr", error.getMessage());
                Toast.makeText(MainActivity.this, "NotWorking", Toast.LENGTH_SHORT).show();
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

    }

    public void selectIconAndDes()
    {

    }
}