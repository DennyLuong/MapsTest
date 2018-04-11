package com.example.denny.mapstest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FloatingActionButton iamthefab;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    String phoneNo = "8326606067";
    String message = "Test Message";
    int securityCircle = 1610; //Roughly 1 mile in meters
    double latVal;
    double longVal;

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

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(29.69145, -95.6768))
                .radius(securityCircle)
                .strokeColor(Color.RED)
                .fillColor(0x22FF0000));

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng home = new LatLng(29.69145, -95.6768);
        mMap.addMarker(new MarkerOptions().position(home).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
    }

    public void updateUserLocation(GoogleMap mMap) {
        Double sLat = getIntent().getDoubleExtra("latSample", latVal);
        Double sLong = getIntent().getDoubleExtra("longSample", longVal);
        LatLng mvmtTracker = new LatLng(sLat, sLong);
        mMap.addMarker(new MarkerOptions().position(mvmtTracker).title("Test"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mvmtTracker));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.READ_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
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