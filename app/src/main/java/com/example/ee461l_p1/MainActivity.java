package com.example.ee461l_p1;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Yuesen driving now

    public void locationSearch(View view) throws IOException {
        EditText et = findViewById(R.id.search_bar);
        String location = et.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list= geocoder.getFromLocationName(location, 1);
        Address add= list.get(0);
        double lat = add.getLatitude();
        double lon = add.getLongitude();
        Intent intent;

    //end of Yuesen driving, Xiyu driving now

        Toast.makeText(this,"In a Second:)",Toast.LENGTH_SHORT).show();

    //end of Xiyu driving, Yuesen driving now

        intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("latitude1", lat);
        intent.putExtra("longitude1", lon);
        startActivity(intent);
    }

}
