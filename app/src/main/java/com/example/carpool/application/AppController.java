package com.example.carpool.application;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.carpool.model.RideDetails;

import java.util.LinkedList;


public class AppController extends MultiDexApplication {

    private static AppController mInstance;
    private LinkedList<RideDetails> listOfRideDetails;

    public static final String TAG = AppController.class.getSimpleName();

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public LinkedList<RideDetails> getListOfRideDetails() {
        return listOfRideDetails;
    }

    public void setListOfRideDetails(LinkedList<RideDetails> listOfRideDetails) {
        this.listOfRideDetails = listOfRideDetails;
    }
}
