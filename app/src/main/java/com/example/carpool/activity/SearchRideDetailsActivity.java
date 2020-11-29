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

import com.example.carpool.R;
import com.example.carpool.application.AppController;
import com.example.carpool.model.RideDetails;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SearchRideDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Query query;
    private TextView tvSearch;
    private ImageView ivBackArrow;
    private EditText etFrom, etTo, clickedEditText;

    private LinkedList<RideDetails> listOfRideDetails;

    private String comingFrom = "";

    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private String latitude = "";
    private String longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_ride_details);
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
        tvSearch = findViewById(R.id.tvSearch);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    private void initializeListeners() {
        tvSearch.setOnClickListener(this);
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
            case R.id.tvSearch:
                prepareSearchDetails();
                break;
        }
    }

    private void prepareSearchDetails() {
        String from = etFrom.getText().toString();
        String to = etTo.getText().toString();
        DatabaseReference rideReference = FirebaseDatabase.getInstance().getReference(getString(R.string.api_rides));
        if (from.equalsIgnoreCase("NJIT")) {
            query = rideReference.orderByChild(getString(R.string.firebase_field_to)).equalTo(to);
        } else {
            query = rideReference.orderByChild(getString(R.string.firebase_field_from)).equalTo(from);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfRideDetails = new LinkedList<>();
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RideDetails rideDetails = snapshot.getValue(RideDetails.class);
                        if (rideDetails != null) {
                            listOfRideDetails.add(rideDetails);
                        }
                    }
                }

                if (listOfRideDetails != null) {
                    if (listOfRideDetails.size() != 0) {
                        AppController.getInstance().setListOfRideDetails(listOfRideDetails);
                        goToDisplayRideDetails();
                    } else {
                        Toast.makeText(SearchRideDetailsActivity.this, "No Rides available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchRideDetailsActivity.this, "No Rides available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void goToDisplayRideDetails() {
        Intent displayRideDetailsIntent = new Intent(this, DisplayRideDetailsActivity.class);
        startActivity(displayRideDetailsIntent);
    }

    public void showPlacesAutoComplete() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
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
