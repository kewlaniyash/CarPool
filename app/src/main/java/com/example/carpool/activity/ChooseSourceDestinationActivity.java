package com.example.carpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.example.carpool.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChooseSourceDestinationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBackArrow;
    private TextView tvActionBarTitle, tvSubmit;
    private EditText etFrom, etTo, etCarNumber, clickedEditText;

    private String latitude = "";
    private String longitude = "";
    private String comingFrom = "";

    private int AUTOCOMPLETE_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_source_destination);
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        prepareDetails();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(getString(R.string.coming_from)))
                comingFrom = bundle.getString(getString(R.string.coming_from));
        }
    }

    private void initializeUi() {
        etTo = findViewById(R.id.etTo);
        etFrom = findViewById(R.id.etFrom);
        tvSubmit = findViewById(R.id.tvSubmit);
        etCarNumber = findViewById(R.id.etCarNumber);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        tvActionBarTitle = findViewById(R.id.tvActionBarTitle);
    }

    private void initializeListeners() {
        tvSubmit.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);
    }

    private void prepareDetails() {
        if (comingFrom.equalsIgnoreCase(getString(R.string.to_njit))) {
            etTo.setText("NJIT");
            etFrom.setOnClickListener(this);
        } else if (comingFrom.equalsIgnoreCase(getString(R.string.from_njit))) {
            etFrom.setText("NJIT");
            etTo.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            case R.id.etFrom:
                clickedEditText = etFrom;
                showPlacesAutoComplete();
                break;
            case R.id.etTo:
                clickedEditText = etTo;
                showPlacesAutoComplete();
                break;
            case R.id.tvSubmit:
                prepareSubmitDetails();
                break;
            default:
                break;
        }
    }

    public void showPlacesAutoComplete() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
            //  PlacesClient placesClient = Places.createClient(this);
        }
        // List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

    }

    private void prepareSubmitDetails() {
        String to = etTo.getText().toString();
        String from = etFrom.getText().toString();
        String carNumber = etCarNumber.getText().toString();

        if (!to.isEmpty()) {
            if (!from.isEmpty()) {
                if (!carNumber.isEmpty()) {
                    showProgressBar(ChooseSourceDestinationActivity.this);
                    DatabaseReference rideReference = FirebaseDatabase.getInstance().getReference(getString(R.string.api_rides));
                    HashMap<String, Object> rideHashMap = new HashMap<>();
                    rideHashMap.put(getString(R.string.firebase_field_from), from);
                    rideHashMap.put(getString(R.string.firebase_field_to), to);
                    rideHashMap.put(getString(R.string.firebase_field_car_number), carNumber);
                    rideReference.push().setValue(rideHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Toast.makeText(ChooseSourceDestinationActivity.this, getString(R.string.ride_details_added_successfully), Toast.LENGTH_SHORT).show();
                            }
                            closeProgressbar();
                        }
                    });
                } else {
                    etCarNumber.setError(getString(R.string.please_enter_car_number));
                }
            } else {
                etFrom.setError(getString(R.string.please_select_from));
            }
        } else {
            etTo.setError(getString(R.string.please_select_to));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("PLace", "Place: " + place.getName() + ", " + place.getId());
                latitude = String.valueOf(Objects.requireNonNull(place.getLatLng()).latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
                clickedEditText.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("PLace", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
