package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.auth.User;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private EditText name, email, phone, car_no;
    private ProgressBar progressBar;
    private Button mBack, mUpdate;
    private String key;
    private String Name, Email, Phone, Car_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        key = getIntent().getStringExtra("key");
        Name = getIntent().getStringExtra("Name");
        Email = getIntent().getStringExtra("Email");
        Phone = getIntent().getStringExtra("Phone");
        Car_no = getIntent().getStringExtra("Car_no");



        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



    }
}
