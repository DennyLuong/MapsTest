package com.example.denny.mapstest;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

        requestSmsPermission();

        //fab
        iamthefab = (FloatingActionButton) findViewById(R.id.iamfab);
        iamthefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(context, "i am fab", Toast.LENGTH_LONG).show();
                updateUserLocation(mMap);
//                sendSMSMessage();
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

    public void updateUserLocation(GoogleMap mMap) {
        LatLng mvmtTracker = new LatLng(29.72186, -95.34011);
        mMap.addMarker(new MarkerOptions().position(mvmtTracker).title("TimeStamp: HH:MM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mvmtTracker));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
    }

    //TODO have options for updating the shoe location
    //and have another option to send a text message to check where they are.

    private void requestSmsPermission() {
        String permission = Manifest.permission.READ_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(context,"permission granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(context,"permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void sendSMSMessage() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }
}


//TODO
/*
Accept Bluetooth

Send SMS when out of range.
Update location every 20 seconds - Pintracker.
Send SMS with location info.

Connect to second phone to show on both screens

*/