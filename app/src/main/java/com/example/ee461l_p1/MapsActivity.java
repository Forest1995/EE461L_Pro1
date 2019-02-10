package com.example.ee461l_p1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


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

    //end of Yuesen driving, Xiyu driving now

        response = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlBase = "https://api.darksky.net/forecast/53d0e7d860befe396fdceddfb46f0175/";//37.8267,-122.4233;
        final String location = Double.toString(lat) + "," + Double.toString(lon);
        final String url = urlBase + location + "?exclude=[minutely,hourly,daily,alerts,flags]";
        //final String url = "https://api.darksky.net/forecast/53d0e7d860befe396fdceddfb46f0175/37.8267,-122.4233?exclude=[minutely,hourly,daily,alerts,flags]";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                response = res;
                try{
                    JSONObject jsonObject = response.getJSONObject("currently");
                    String temperature = jsonObject.getString("temperature");
                    String humidity = jsonObject.getString("humidity");
                    String windSpeed = jsonObject.getString("windSpeed");
                    String precipitation = jsonObject.getString("precipIntensity");
                    TextView showResponse = (TextView) findViewById(R.id.textView);
                    String latLonShort = Double.toString(lat).substring(0, 7) + ", " + Double.toString(lon).substring(0, 7);
                    showResponse.setText(" Weather at the Loaction of " + latLonShort  +
                            ": \n Temperature: " +temperature +
                            " ℃                   Humidity: " + humidity +
                            "\n Windspeed: " + windSpeed +
                            " m/s                      Precipitation: " + precipitation + " mm/h");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        });

        queue.add(objectRequest);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //end of Xiyu driving, Yuesen driving now

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng newLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(newLocation).title("Marker 1"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, 15);
        mMap.moveCamera(update);

    }
}
