package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import javax.annotation.Nullable;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        }, 4000);
    }
}
