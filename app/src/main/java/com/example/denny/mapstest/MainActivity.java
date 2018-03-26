package com.example.denny.mapstest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    //TODO Change Device Address and UUID
    private final String DEVICE_ADDRESS="00:06:66:D2:A8:E7"; //Bluesmirf BTA BlueTooth Address
    //UUID is General Usage. *I think*
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    Button startButton, sendButton, stopButton, clearButton;
    TextView textView;
    EditText editText;
    Button mapViewer;

    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = getApplicationContext();
        final CharSequence text = "Launching Google Maps";
        final int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
//        startButton = (Button) findViewById(R.id.connectBT);
        mapViewer = (Button) findViewById(R.id.mapview);
        textView = (TextView) findViewById(R.id.value);
//        stopButton = (Button) findViewById(R.id.buttonStop);
//        clearButton = (Button) findViewById(R.id.buttonClear);
//        sendButton = (Button) findViewById(R.id.buttonSend);
//        setUiEnabled(false);

        mapViewer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
//                toast.show();
                Toast.makeText(context, "Opening Map Viewer Screen", duration).show();
                openMapViewerActivity();
            }
        });
    }

//    public void setUiEnabled(boolean bool)
//    {
//        startButton.setEnabled(!bool);
//        sendButton.setEnabled(bool);
//        stopButton.setEnabled(bool);
//        textView.setEnabled(bool);
//    }

//    public boolean BTinit()
//    {
//        boolean found=false;
//        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
//        }
//        if(!bluetoothAdapter.isEnabled())
//        {
//            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableAdapter, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
//        if(bondedDevices.isEmpty())
//        {
//            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            for (BluetoothDevice iterator : bondedDevices)
//            {
//                if(iterator.getAddress().equals(DEVICE_ADDRESS))
//                {
//                    device=iterator;
//                    found=true;
//                    break;
//                }
//            }
//        }
//        return found;
//    }

//    public boolean BTconnect()
//    {
//        boolean connected=true;
//        try {
//            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
//            socket.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//            connected=false;
//        }
//        if(connected)
//        {
//            try {
//                outputStream=socket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                inputStream=socket.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return connected;
//    }

//    public void onClickStart(View view) {
//        if(BTinit())
//        {
//            if(BTconnect())
//            {
//                setUiEnabled(true);
//                deviceConnected=true;
//                beginListenForData();
//                textView.append("\nConnection Opened!\n");
//            }
//
//        }
//    }

//    void beginListenForData()
//    {
//        final Handler handler = new Handler();
//        stopThread = false;
//        buffer = new byte[1024];
//        Thread thread  = new Thread(new Runnable()
//        {
//            public void run()
//            {
//                while(!Thread.currentThread().isInterrupted() && !stopThread)
//                {
//                    try
//                    {
//                        int byteCount = inputStream.available();
//                        if(byteCount > 0)
//                        {
//                            byte[] rawBytes = new byte[byteCount];
//                            inputStream.read(rawBytes);
//                            final String string=new String(rawBytes,"UTF-8");
//                            handler.post(new Runnable() {
//                                public void run()
//                                {
//                                    textView.append(string);
//                                }
//                            });
//
//                        }
//                    }
//                    catch (IOException ex)
//                    {
//                        stopThread = true;
//                    }
//                }
//            }
//        });
//
//        thread.start();
//    }

//    public void onClickSend(View view) {
//        String string = editText.getText().toString();
//        string.concat("\n");
//        try {
//            outputStream.write(string.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        textView.append("\nSent Data:"+string+"\n");
//    }


//    public void onClickStop(View view) throws IOException {
//        stopThread = true;
//        outputStream.close();
//        inputStream.close();
//        socket.close();
//        setUiEnabled(false);
//        deviceConnected=false;
//        textView.append("\nConnection Closed!\n");
//    }
//
//    public void onClickClear(View view) {
//        textView.setText("");
//    }

    private void openMapViewerActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
