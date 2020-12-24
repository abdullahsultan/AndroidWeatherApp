package com.example.assignment_1;

import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue mRequestQueue;
    ArrayList <String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        fetchJsonResponse();


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
                        Gson gson = new Gson();
                        WeatherProperties weatherProperties = gson.fromJson(response.toString(),WeatherProperties.class);
                        Log.i("Venom", "onResponse: "+ weatherProperties.getMain().getTemp().toString());
                        Toast.makeText(MainActivity.this, "Working", Toast.LENGTH_SHORT).show();
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
}