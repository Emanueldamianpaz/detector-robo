package com.davinci.edu.detector_robo.gps;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class GPS_Location {

    private Context context;
    private double latitude;
    private double longitude;

    public GPS_Location(Context context) {
        this.context = context;
    }


    public String getLocation() {
        LocationManager enabledManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (enabledManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = enabledManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                //Location wasnt gathered
            } else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        return latitude + "," + longitude;
    }
}