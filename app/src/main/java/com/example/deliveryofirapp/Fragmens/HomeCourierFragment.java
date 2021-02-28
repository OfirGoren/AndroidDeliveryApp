package com.example.deliveryofirapp.Fragmens;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.deliveryofirapp.Objects.MapManagerCourier;
import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.CheckPermissions;
import com.example.deliveryofirapp.Utils.CoordinatesMap;
import com.example.deliveryofirapp.Utils.IntentUtils;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.button.MaterialButton;

public class HomeCourierFragment extends Fragment implements View.OnClickListener {


    private View view;
    private SupportMapFragment homeCourier;
    private Context context;
    private MaterialButton fragment_courier_BTN_arrive;
    private String destinationDelivery;
    private TextView fragment_courier_LBL_name;
    private TextView fragment_courier_LBL_phone;
    private RelativeLayout fragment_courier_layout_contact;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context.getApplicationContext();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_courier_home, container, false);

        this.homeCourier = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.fragment_courier_map);

        findViewsById();
        setClickListener();
        PermissionLocation();

        return view;


    }

    private void setClickListener() {
        fragment_courier_BTN_arrive.setOnClickListener(this);
        fragment_courier_layout_contact.setOnClickListener(this);
    }

    public void updateButtonVisible() {
        fragment_courier_BTN_arrive.setVisibility(View.VISIBLE);
    }

    private void findViewsById() {
        fragment_courier_BTN_arrive = view.findViewById(R.id.fragment_courier_BTN_arrive);
        fragment_courier_LBL_name = view.findViewById(R.id.fragment_courier_LBL_name);
        fragment_courier_LBL_phone = view.findViewById(R.id.fragment_courier_LBL_phone);
        fragment_courier_layout_contact = view.findViewById(R.id.fragment_courier_layout_contact);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_courier_BTN_arrive) {

            boolean deliveryIsFinished = checkIfDeliveryIsFinished();
            updateViewsAccordingToStatusDelivery(deliveryIsFinished);

        } else if (v.getId() == R.id.fragment_courier_layout_contact) {
            Intent intent = IntentUtils.getIntentOpenDialer(fragment_courier_LBL_phone.getText().toString());
            startActivity(intent);
        }
    }


    private void PermissionLocation() {
        //when there is already permission
        if (CheckPermissions.getInstance().checkIfAlreadyHavePermissionLocation()) {
            // initialize the map
            initializeMapCourier();

        } else {
            // request permission from the user
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CoordinatesMap.REQUEST_PERMISSIONS_CODE_LOCATION);

        }


    }

    private void initializeMapCourier() {
        // initialize map
        if (this.homeCourier != null) {
            new MapManagerCourier(this.context, this.homeCourier);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CoordinatesMap.REQUEST_PERMISSIONS_CODE_LOCATION) {
            // when the user confirmed the permission location
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //initialize the map
                initializeMapCourier();

            }
        }

    }


    private void updateViewsAccordingToStatusDelivery(boolean deliveryIsFinished) {
        //when the delivery is finished
        if (deliveryIsFinished) {
            fragment_courier_BTN_arrive.setVisibility(View.INVISIBLE);
            fragment_courier_BTN_arrive.setText(getResources().getString(R.string.im_picked_up_the_package));
            fragment_courier_layout_contact.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), R.string.delivery_is_finish, Toast.LENGTH_LONG).show();
        } else {
            changeTextButton();
            openTurnByTurnNav();

        }

    }

    private boolean checkIfDeliveryIsFinished() {

        return fragment_courier_BTN_arrive.getText().toString().equals(getResources().getString(R.string.i_delivered_the_package));
    }

    // activate the navigation to destination
    private void openTurnByTurnNav() {
        Intent intent = IntentUtils.getIntentOpenGoogleMap(destinationDelivery);
        startActivity(intent);

    }

    public void setContactDetail(String name, String phoneNum) {
        fragment_courier_layout_contact.setVisibility(View.VISIBLE);
        fragment_courier_LBL_name.setText(name);
        fragment_courier_LBL_phone.setText(phoneNum);

    }

    // save the destination
    public void PassDestinationNav(String destination) {

        this.destinationDelivery = destination;

    }

    private void changeTextButton() {
        fragment_courier_BTN_arrive.setText(R.string.i_delivered_the_package);
    }
}