package com.cubepcr.cubepcr;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class menu_page extends Activity {

    Animation myanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_page, menu);
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
    public void click1(View v){
        Animation doalpha = AnimationUtils.loadAnimation(this, R.anim.buttons);
        v.startAnimation(doalpha);

        startActivity(new Intent(this, Data.class));
    }
    public void click2(View v) {

        Animation doalpha = AnimationUtils.loadAnimation(this, R.anim.buttons);
        v.startAnimation(doalpha);

        startActivity(new Intent(this,DeviceList.class));
    }
    public void click3(View v) {

        Animation doalpha = AnimationUtils.loadAnimation(this, R.anim.buttons);
        v.startAnimation(doalpha);

        startActivity(new Intent(this,About_Page.class));
    }
}
