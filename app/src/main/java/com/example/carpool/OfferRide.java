package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class OfferRide extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button mofferto, mofferfrom, mback1;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_ride);
        mofferto = findViewById(R.id.btn_ofrtoNJIT);
        mofferfrom = findViewById(R.id.btn_ofrfromNJIT);
        mback1 = findViewById(R.id.back2);

        mback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}
