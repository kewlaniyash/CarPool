package com.example.carpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.carpool.R;

public class SearchRideActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBackArrow;
    private LinearLayout llToNJIT, llFromNJIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_ride);
        initializeUi();
        initializeListeners();
    }

    private void initializeUi() {
        llToNJIT = findViewById(R.id.llToNJIT);
        llFromNJIT = findViewById(R.id.llFromNJIT);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    private void initializeListeners() {
        llToNJIT.setOnClickListener(this);
        llFromNJIT.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llToNJIT:
                prepareToNJITDetails();
                break;
            case R.id.llFromNJIT:
                prepareFromNJITDetails();
                break;
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    private void prepareToNJITDetails() {
        Intent chooseSourceDestinationIntent = new Intent(this, SearchRideDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.coming_from), getString(R.string.to_njit));
        chooseSourceDestinationIntent.putExtras(bundle);
        startActivity(chooseSourceDestinationIntent);
    }

    private void prepareFromNJITDetails() {
        Intent chooseSourceDestinationIntent = new Intent(this, SearchRideDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.coming_from), getString(R.string.from_njit));
        chooseSourceDestinationIntent.putExtras(bundle);
        startActivity(chooseSourceDestinationIntent);
    }
}
