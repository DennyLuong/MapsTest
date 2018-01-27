package com.example.denny.mapstest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = getApplicationContext();
        final CharSequence text = "Launching Google Maps";
        final int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);

        Button gMaps = (Button) findViewById(R.id.googsMaps);
        Button mapViewer = (Button) findViewById(R.id.mapview);

        gMaps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast.show();
                Toast.makeText(context, text, duration).show();
                Uri gmmIntentUri = Uri.parse("geo:37.7749, -122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        mapViewer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                toast.show();
//                Toast.makeText(context, "Opening Map Viewer Screen", duration).show();
                openMapViewerActivity();
            }
        });
    }

    private void openMapViewerActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
