package com.example.carpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.carpool.R;
import com.example.carpool.utils.PreferenceConnector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText etEmailId, etPassword;
    private LinearLayout llLogin, llRegisterNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        initializeUi();
        initializeListeners();
    }

    private void initializeUi() {
        llLogin = findViewById(R.id.llLogin);
        etEmailId = findViewById(R.id.etEmailId);
        firebaseAuth = FirebaseAuth.getInstance();
        etPassword = findViewById(R.id.etPassword);
        llRegisterNow = findViewById(R.id.llRegisterNow);
    }

    private void initializeListeners() {
        llLogin.setOnClickListener(this);
        llRegisterNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llLogin:
                prepareLoginDetails();
                break;
            case R.id.llRegisterNow:
                prepareRegisterNowDetails();
                break;
            default:
                break;
        }
    }

    private void prepareLoginDetails() {
        String emailId = etEmailId.getText().toString();
        String password = etPassword.getText().toString();
        if (!emailId.isEmpty()) {
            if (!password.isEmpty()) {
                showProgressBar(LoginActivity.this);
                firebaseAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToHome();
                            closeProgressbar();
                            PreferenceConnector.writeBoolean(LoginActivity.this, getString(R.string.logged_in), true);
                            Toast.makeText(LoginActivity.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            closeProgressbar();
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.please_enter_email_id), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToHome() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    private void prepareRegisterNowDetails() {
        Intent registerNowIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerNowIntent);
    }
}
