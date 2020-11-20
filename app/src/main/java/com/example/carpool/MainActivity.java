package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDexApplication;

import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;



    public class MainActivity extends AppCompatActivity {


        public void logout(View view) {
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }



    }
