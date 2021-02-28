package com.example.deliveryofirapp.Objects;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.BitmapUtils;
import com.example.deliveryofirapp.Utils.CoordinatesMap;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapManagerCourier implements OnMapReadyCallback {
    private GoogleMap gMap;
    private Marker marker;
    private final MarkerOptions markerOptions;
    private final Context context;
    private final LocationManager locationManager;


    public MapManagerCourier(Context context, SupportMapFragment supportMapFragment) {
        markerOptions = new MarkerOptions();
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        supportMapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(CoordinatesMap.getInstance().getLatitude(), CoordinatesMap.getInstance().getLongitude());

        //when the location changed
        onLocationChanged();
        //add marker on map
        marker = gMap.addMarker(markerOptions
                .position(sydney)
                .flat(true)
                .alpha(0.7f)
                .icon(BitmapUtils.getInstance().bitmapDescriptorFromVector(R.drawable.navigation, 150)));
        //move the camera on map
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // animate camera
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));


    }


    private void onLocationChanged() {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, location -> {

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(18)
                    .build();
            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
            gMap.animateCamera(cu);
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));


        });


    }


}
