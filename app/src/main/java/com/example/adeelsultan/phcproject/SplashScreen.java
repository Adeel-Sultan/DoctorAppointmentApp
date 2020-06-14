package com.example.adeelsultan.phcproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() != null){
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                    finish();
                }
                else{
                    Intent homeIntent = new Intent(SplashScreen.this, LoginAsPatient.class);
                    startActivity(homeIntent);
                    finish();
            }

            }
        },SPLASH_TIME_OUT);
    }
}
