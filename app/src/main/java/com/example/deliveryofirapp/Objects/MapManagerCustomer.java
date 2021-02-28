package com.example.deliveryofirapp.Objects;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.deliveryofirapp.Interface.CallBackAddress;
import com.example.deliveryofirapp.Utils.Addresses;
import com.example.deliveryofirapp.Utils.CoordinatesMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapManagerCustomer implements OnMapReadyCallback {

    private static final String SEARCH = "Search...";
    private static final String NOT_AVAILABLE = "Not Available";

    private CallBackAddress callBackAddress;
    private final Context context;
    private boolean isAlreadySearch;


    public MapManagerCustomer(Context context, SupportMapFragment supportMapFragment) {
        this.context = context;
        isAlreadySearch = false;
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        //initialize listener
        CameraIdListener(googleMap);
        CameraMoveListener(googleMap);


        LatLng sydney = new LatLng(CoordinatesMap.getInstance().getLatitude(), CoordinatesMap.getInstance().getLongitude());

        //move camera according to coordinates
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        setMyLocationEnabled(googleMap);
        //animate the camera
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));


    }

    private void setMyLocationEnabled(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }

    private void CameraMoveListener(GoogleMap googleMap) {

        googleMap.setOnCameraMoveListener(() -> {
            //when the text is no search...
            if (!isAlreadySearch) {
                //change the text to search in Class HomeCustomerFragment
                passCallBackAddress(SEARCH);
                //change true because we change the text to search
                isAlreadySearch = true;
            }

        });

    }

    //initialize callback from class HomeCustomerFragment
    public void setCallBackAddress(CallBackAddress callBackAddress) {
        this.callBackAddress = callBackAddress;
    }


    private void passCallBackAddress(String address) {
        //when callback initialize
        if (this.callBackAddress != null) {
            // activate callback in home Fragment send current address on map
            callBackAddress.callBackLocation(address);
        }
    }

    //when we stop to move the map this listener activate
    private void CameraIdListener(GoogleMap googleMap) {

        googleMap.setOnCameraIdleListener(() -> {

            // get the address according to current location coordinate
            String address = Addresses.getInstance().getAddress(googleMap.getCameraPosition().target.latitude, googleMap.getCameraPosition().target.longitude);
            //when the marker in israel and there is address
            if (address == null || !address.contains(StaticVariables.ISRAEL)) {
                //activate callback in homeFragment with message Not Available
                passCallBackAddress(NOT_AVAILABLE);
            } else {
                //activate call back in homeFragment with current address
                passCallBackAddress(address);
            }
            //when the text is no longer search...
            isAlreadySearch = false;

        });


    }


}