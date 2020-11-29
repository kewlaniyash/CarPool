package com.example.carpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.carpool.R;
import com.example.carpool.utils.CommonMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSubmit;
    private ImageView ivBackArrow;
    private FirebaseAuth firebaseAuth;
    private EditText etName, etEmailId, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        initializeUi();
        initializeListeners();
    }

    private void initializeUi() {
        etName = findViewById(R.id.etName);
        tvSubmit = findViewById(R.id.tvSubmit);
        etEmailId = findViewById(R.id.etEmailId);
        firebaseAuth = FirebaseAuth.getInstance();
        etPassword = findViewById(R.id.etPassword);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    private void initializeListeners() {
        tvSubmit.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            case R.id.tvSubmit:
                prepareSubmitDetails();
                break;
            default:
                break;
        }
    }

    private void prepareSubmitDetails() {
        String name = etName.getText().toString();
        String emailId = etEmailId.getText().toString();
        String password = etPassword.getText().toString();
        if (!name.isEmpty()) {
            if (!emailId.isEmpty()) {
                if (CommonMethods.isValidEmailFormat(emailId)) {
                    if (!password.isEmpty()) {
                        if (password.length() >= 6) {
                            firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful, Please Try Again!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        goToHome();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(this, "Password must be 6 digits large or more", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        etPassword.setError(getString(R.string.please_enter_password));
                    }
                } else {
                    etEmailId.setError(getString(R.string.please_enter_valid_email_id));
                }
            } else {
                etEmailId.setError(getString(R.string.please_enter_email_id));
            }
        } else {
            etName.setError(getString(R.string.please_enter_name));
        }
    }

    private void goToHome() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }
}
