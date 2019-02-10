package com.example.ee461l_p1;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat;
    private double lon;
    private JSONObject response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude1", 0.00);
        lon = intent.getDoubleExtra("longitude1", 0.00);
        response = new JSONObject();
    }

    public void showWeather(View view) throws JSONException {
        //final
        //JSONObject response = new JSONObject();
        //a queue
        //compile 'com.android.volley:volley:1.1.1'
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlBase = "https://api.darksky.net/forecast/53d0e7d860befe396fdceddfb46f0175/";//37.8267,-122.4233;
        String location = Double.toString(lat) + "," + Double.toString(lon);
        final String url = urlBase + location + "?exclude=[minutely,hourly,daily,alerts,flags]";
        //a request
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                response = res;
                /*try{
                    String temperature = response.getString("temperature");
                    String humidity = response.getString("temperature");
                }*/
                Log.d("Response", res.toString());
                //succeedToast.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //failToast.show();
                Log.d("Error.Response", error.toString() );
            }
        });
        //objectRequest.setTag(Tag);
        queue.add(objectRequest);

        JSONObject myResponse = new JSONObject(response.toString());

        TextView showResponse = (TextView) findViewById(R.id.textView2);
        showResponse.setText(response.toString());


        String temperature = myResponse.getJSONObject("currently").getString("temperature");
        String humidity = myResponse.getJSONObject("currently").getString("humidity");
        String windSpeed = myResponse.getJSONObject("currently").getString("windSpeed");
        String precipitation = myResponse.getJSONObject("currently").getString("precipType");
        //System.out.print(temperature + ',' + humidity + ',' + windSpeed + ',' + precipitation);

    }




/*
    public void call_me() throws Exception {
        String urlBase = "https://api.darksky.net/forecast/53d0e7d860befe396fdceddfb46f0175/";//37.8267,-122.4233;
        String location = Double.toString(lat) + "," + Double.toString(lon);
        String url = urlBase + location + "?exclude=[minutely,hourly,daily,alerts,flags]";
        System.out.print("url formed");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //? W is this?
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        //int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        System.out.print("ready to receive data");
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.print("received");
        JSONObject myResponse = new JSONObject(response.toString());
        String temperature = myResponse.getJSONObject("currently").getString("temperature");
        String humidity = myResponse.getJSONObject("currently").getString("humidity");
        String windSpeed = myResponse.getJSONObject("currently").getString("windSpeed");
        String precipitation = myResponse.getJSONObject("currently").getString("precipType");
        System.out.print(temperature);
    }
    */


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng newLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(newLocation).title("Marker 1"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, 15);
        mMap.moveCamera(update);

    }
}

