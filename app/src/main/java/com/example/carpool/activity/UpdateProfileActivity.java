package com.example.carpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.carpool.R;
import com.example.carpool.model.RideDetails;
import com.example.carpool.model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateProfileActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvUpdate;
    private FirebaseAuth fbAuth;
    private ImageView ivBackArrow;
    private EditText etName, etEmailId, etMobileNumber, etCarNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_profile);
        initializeUi();
        initializeListeners();
        prepareDetails();
    }

    private void initializeUi() {
        etName = findViewById(R.id.etName);
        fbAuth = FirebaseAuth.getInstance();
        tvUpdate = findViewById(R.id.tvUpdate);
        etEmailId = findViewById(R.id.etEmailId);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        etCarNumber = findViewById(R.id.etCarNumber);
        etMobileNumber = findViewById(R.id.etMobileNumber);
    }

    private void initializeListeners() {
        tvUpdate.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);
    }

    private void prepareDetails() {
        FirebaseUser firebaseUser = fbAuth.getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(getString(R.string.api_users)).child(userId);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    UserDetails userDetails = snapshot.getValue(UserDetails.class);
                    if (userDetails != null) {
                        String name = userDetails.getName();
                        String emailId = userDetails.getEmailId();
                        String mobileNumber = userDetails.getMobileNumber();
                        String carNumber = userDetails.getCarNumber();

                        etName.setText(name);
                        etEmailId.setText(emailId);
                        etMobileNumber.setText(mobileNumber);
                        etCarNumber.setText(carNumber);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            case R.id.tvUpdate:
                prepareUpdateDetails();
                break;
            default:
                break;
        }
    }

    private void prepareUpdateDetails() {
        showProgressBar(this);
        String name = etName.getText().toString();
        String emailId = etEmailId.getText().toString();
        String mobileNumber = etMobileNumber.getText().toString();
        String carNumber = etCarNumber.getText().toString();

        FirebaseUser firebaseUser = fbAuth.getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(getString(R.string.api_users)).child(userId);
        HashMap<String, Object> userHashMap = new HashMap<>();
        userHashMap.put(getString(R.string.firebase_field_name), name);
        userHashMap.put(getString(R.string.fireBase_email_id), emailId);
        userHashMap.put(getString(R.string.firebase_field_mobile_number), mobileNumber);
        userHashMap.put(getString(R.string.firebase_field_car_number), carNumber);
        userReference.updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                    closeProgressbar();
                } else {
                    closeProgressbar();
                }
            }
        });
    }
}
