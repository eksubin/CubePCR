package com.cubepcr.cubepcr;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class PCR_Timer extends ActionBarActivity {
int PCR_time;
    TextView mytext;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcr__timer);
        Intent get_intent = getIntent();
        PCR_time = get_intent.getIntExtra("time",1);
        mytext = (TextView)findViewById(R.id.timer_text);

        mp = MediaPlayer.create(this, R.raw.abcd);
        new CountDownTimer(500000 , 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                int seconds = (int)millisUntilFinished/1000;
                int minutes = seconds/60;
                int hour = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;
                hour = hour % 60;

                mytext.setText(hour + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds));
            }
            public void onFinish()
            {
                mytext.setText("done");
                mp.start();


            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pcr__timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Process not over",Toast.LENGTH_SHORT).show();
    }
}
