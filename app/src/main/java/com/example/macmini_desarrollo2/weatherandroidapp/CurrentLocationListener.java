package com.example.macmini_desarrollo2.weatherandroidapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by macmini_desarrollo2 on 03/06/16.
 */
public class CurrentLocationListener implements LocationListener{

    public  String latitude;


    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();


        String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        Log.d("Tag_2", "My Current Location" + myLocation);
        latitude = String.valueOf(location.getLatitude());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
