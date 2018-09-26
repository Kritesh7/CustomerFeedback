package com.kriteshmaurya.customerfeedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Config_Connection.isOnline(SplashActivity.this);
                if (Config_Connection.internetStatus == true) {

                    Intent i = new Intent(SplashActivity.this,BaseActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Config_Connection.toastShow("No Internet Connection!", SplashActivity.this);
                }


            }
        }, SPLASH_TIME_OUT);

    }
}
