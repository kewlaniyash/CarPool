package com.example.carpool.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.carpool.R;
import com.example.carpool.utils.PreferenceConnector;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llSearch, llOfferRide, llUpdateDetails, llLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        initializeUi();
        initializeListeners();
    }

    private void initializeUi() {
        llSearch = findViewById(R.id.llSearch);
        llLogout = findViewById(R.id.llLogout);
        llOfferRide = findViewById(R.id.llOfferRide);
        llUpdateDetails = findViewById(R.id.llUpdateDetails);
    }

    private void initializeListeners() {
        llSearch.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        llOfferRide.setOnClickListener(this);
        llUpdateDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llSearch:
                prepareSearchDetails();
                break;
            case R.id.llLogout:
                prepareLogoutDetails();
                break;
            case R.id.llOfferRide:
                prepareOfferRideDetails();
                break;
            case R.id.llUpdateDetails:
                prepareUpdateDetails();
                break;
            default:
                break;
        }
    }

    private void prepareSearchDetails() {
        Intent searchRideIntent = new Intent(this, SearchRideActivity.class);
        startActivity(searchRideIntent);
    }

    private void prepareLogoutDetails() {
        showLogoutAlertDialog();
    }

    private void prepareOfferRideDetails() {
        Intent offerRideIntent = new Intent(this, OfferRideActivity.class);
        startActivity(offerRideIntent);
    }

    private void prepareUpdateDetails() {
        Intent updateDetailsIntent = new Intent(this, UpdateProfileActivity.class);
        startActivity(updateDetailsIntent);
    }

    private void showLogoutAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialogBuilder.setMessage(getString(R.string.are_you_sure_do_you_want_to_logout));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearDataInPreferences();
                goToLogin();
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goToLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }

    private void clearDataInPreferences() {
        PreferenceConnector.clearData(this);
    }
}
