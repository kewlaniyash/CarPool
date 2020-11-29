package com.example.carpool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;
import com.example.carpool.adapter.DisplayRideDetailsAdapter;
import com.example.carpool.application.AppController;
import com.example.carpool.model.RideDetails;

import java.util.LinkedList;

public class DisplayRideDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBackArrow;
    private RecyclerView rvDetails;

    private LinkedList<RideDetails> listOfRideDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_display_ride_details);
        getData();
        initializeUi();
        initializeListeners();
        initializeAdapter();
    }

    private void getData() {
        listOfRideDetails = AppController.getInstance().getListOfRideDetails();
    }

    private void initializeUi() {
        rvDetails = findViewById(R.id.rvDetails);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    private void initializeListeners() {
        ivBackArrow.setOnClickListener(this);
    }

    private void initializeAdapter() {
        DisplayRideDetailsAdapter displayRideDetailsAdapter = new DisplayRideDetailsAdapter(this, listOfRideDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvDetails.setLayoutManager(layoutManager);
        rvDetails.setItemAnimator(new DefaultItemAnimator());
        rvDetails.setAdapter(displayRideDetailsAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
