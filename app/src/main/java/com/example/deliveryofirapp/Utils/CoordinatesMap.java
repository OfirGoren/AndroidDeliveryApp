package com.example.deliveryofirapp.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

public class CoordinatesMap {
    public static final int REQUEST_PERMISSIONS_CODE_LOCATION = 44;
    //get the application context Therefore can not be leak
    @SuppressLint("StaticFieldLeak")
    private static CoordinatesMap instance;
    private final LocationManager lm;
    private Location location;
    private final Context context;

    private CoordinatesMap(Context context) {
        this.lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;

    }

    public static CoordinatesMap getInstance() {

        return instance;

    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new CoordinatesMap(context);
        }
    }

    /**
     * To get the current location, first ask the user to activate
     * the location services (you can use the ActivityCompat.requestPermissions method)
     *
     * @return when there isn't Access to Location return 0
     */
    public double getLongitude() {
        getLastKnownLocation();
        if (hasPermission()) {
            return this.location.getLongitude();
        }
        return 0;

    }

    /**
     * To get the current location, first ask the user to activate
     * the location services (you can use the ActivityCompat.requestPermissions method)
     *
     * @return when there isn't Access to Location return 0 ;
     */
    public double getLatitude() {
        getLastKnownLocation();
        if (hasPermission()) {
            return this.location.getLatitude();
        }
        return 0;
    }


    private boolean hasPermission() {
        return this.location != null;
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        this.location = this.lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }


}
