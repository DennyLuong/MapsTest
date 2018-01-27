package com.example.denny.mapstest;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FloatingActionButton iamthefab;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo = "8326606067";
    String message = "Test Message";

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, "", duration);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        //fab
        iamthefab = (FloatingActionButton) findViewById(R.id.iamfab);
        iamthefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(context, "i am fab", Toast.LENGTH_LONG).show();
//                updateUserLocation(mMap);
                sendSMSMessage();
            };
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng home = new LatLng(29.69145, -95.6768);
        mMap.addMarker(new MarkerOptions().position(home).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
    }

    //TODO have options for updating the shoe location
    //and have another option to send a text message to check where they are.

    protected void sendSMSMessage() {
        SmsManager smsManager = SmsManager.getDefault();
//        StringBuilder coordinatesMsg = new StringBuilder(30).append(longitude).append(" Long | Lat ").append(latitude);
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }

    //SMS should automatically send when outside of the radius or not in the correct route.


    private void updateUserLocation(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng school = new LatLng(29.719949, -95.342233);
//        mMap.addMarker(new MarkerOptions().position(school).title("University of Houston"));
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}

        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
}
