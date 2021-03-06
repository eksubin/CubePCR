package com.cubepcr.cubepcr;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;


public class ledControl extends ActionBarActivity {

    Button btnOn, btnDis;
    // Text views
    TextView Denat_temp,Denat_time,Anneal_temp,Anneal_time,Extension_temp,Extension_time;
    // strings
    String Dtemp,Dtime,Etemp,Etime,Atemp,Atime;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    String aa,bb;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_led_control);

        //call the widgtes
        btnOn = (Button)findViewById(R.id.button2);
        btnDis = (Button)findViewById(R.id.button4);
        Denat_temp = (TextView)findViewById(R.id.denat_temp);
        Denat_time = (TextView)findViewById(R.id.denat_time);
        Extension_temp = (TextView)findViewById(R.id.extension_temp);
        Extension_time = (TextView)findViewById(R.id.extension_time);
        Anneal_temp = (TextView)findViewById(R.id.anneal_temp);
        Anneal_time = (TextView)findViewById(R.id.anneal_time);

        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth
        btnOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {   Animation doalpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttons);
                arg0.startAnimation(doalpha);
                turnOnLed();      //method to turn on
            }
        });



        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });


    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }



    private void turnOnLed()
    {
        if (btSocket!=null)
        {
           Dtemp = Denat_temp.getText().toString();
            Dtime = Denat_time.getText().toString();
            Etemp = Extension_temp.getText().toString();
            Etime = Extension_time.getText().toString();
            Atemp = Anneal_temp.getText().toString();
            Atime = Anneal_time.getText().toString();
            String total = Dtemp + "  "+ Dtime + "  " + Atemp + "  " + Atime + "  " + Etemp + "  " + Etime;
           try
            {
                btSocket.getOutputStream().write(total.getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
        int total_time = Integer.parseInt(Denat_time.getText().toString()) + Integer.parseInt(Extension_time.getText().toString()) + Integer.parseInt(Anneal_time.getText().toString());
        Intent call_timer = new Intent(getApplicationContext(),PCR_Timer.class);
        call_timer.putExtra("time",total_time);
        startActivity(call_timer);
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;
        else if(id == R.id.about_app) {
            Intent intent = new Intent(getApplicationContext(), About_Page.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
