package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDexApplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class SearchRide extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button mfromNJIT, mtoNJIT, mback;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ride);
        mtoNJIT = findViewById(R.id.searchtoNJIT);
        mfromNJIT = findViewById(R.id.searchfromNJIT);
        mback = findViewById(R.id.back1);

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}
