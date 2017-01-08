package com.example.chelliahdilkumar.htv;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.*;
import java.io.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String address;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        URL u;
        Intent intent = getIntent();
        address = intent.getStringExtra(MainActivity.EXTRA_MESSAGE).replace(' ', '+');
        try {
            u = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=AIzaSyDRxdp-_-cnS0z4KTu7JzeeGiz08zO4Drg");
            URLConnection conn = u.openConnection();
            InputStream is = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            String string = "";

            while (br.ready()){
                string += br.readLine();
            }
            int position = string.indexOf("\"lat\"");
            latitude = Double.parseDouble(string.substring(position+7, position+18));
            position = string.indexOf("\"lng\"");
            longitude = Double.parseDouble(string.substring(position+7, position+18));

        }
        catch (Exception e)
        {
            System.out.println("yikes that isn't supposed to happen");
            latitude = 43.7837405;
            longitude = -79.187683;
        }

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        


            }
        }

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng marker = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in location"));
        Circle circle = mMap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude)).radius(300).strokeColor(Color.argb(200,0,0,200)).fillColor(Color.argb(50,0,0,200)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude)));


    }
}
