package com.cubepcr.cubepcr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class MainActivity extends Activity {
    ProgressBar progress;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new BarUpdate()).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class BarUpdate implements Runnable {
        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {

            for (int i = 0; i <= 60; i++) {
                try {
                    Thread.sleep(60);
                    value = i;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(value == 60)
                {
                    Intent j = new Intent(getApplicationContext(), menu_page.class);
                    startActivity(j);
                    finish();
                }
                }
            }
        }
    }







