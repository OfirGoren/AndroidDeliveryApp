package com.example.deliveryofirapp.Fragmens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.deliveryofirapp.Activity.OrderDelivery;
import com.example.deliveryofirapp.Interface.CallBackAddress;
import com.example.deliveryofirapp.Objects.MapManagerCustomer;
import com.example.deliveryofirapp.Objects.StaticVariables;
import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.CheckPermissions;
import com.example.deliveryofirapp.Utils.ColorUtils;
import com.example.deliveryofirapp.Utils.CoordinatesMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.button.MaterialButton;


public class HomeCustomerFragment extends Fragment implements CallBackAddress, View.OnClickListener {

    public static final String ORIGIN = "ORIGIN";
    public static final String CAR_OR_MOTORCYCLE = "CAR_OR_MOTORCYCLE";


    private View view;
    private SupportMapFragment home_map;
    private TextView home_LBL_position;
    private MaterialButton home_BTN_order_delivery;
    private ImageView home_IMG_icon_car;
    private TextView home_LBL_up_to_5kg;
    private TextView home_LBL_up_to_10kg;
    private ImageView home_IMG_icon_motorcycle;
    private LinearLayout home_LAY_motorcycle_and_5kg;
    private LinearLayout home_LAY_motorcycle_and_10kg;
    private Context context;


    // true is car , false is motorcycle
    private boolean vehicleType = false;


    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context.getApplicationContext();
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_customer_home, container, false);

        findViewById();


        this.home_map = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.home_map);

        setOnClickListener();


        checkAndAskPermission();

        return view;

    }


    private void setOnClickListener() {
        home_LAY_motorcycle_and_5kg.setOnClickListener(this);
        home_LAY_motorcycle_and_10kg.setOnClickListener(this);
        home_BTN_order_delivery.setOnClickListener(this);
    }

    private void findViewById() {
        home_BTN_order_delivery = view.findViewById(R.id.home_BTN_order_delivery);
        home_LBL_position = view.findViewById(R.id.home_LBL_position);
        home_IMG_icon_car = view.findViewById(R.id.home_IMG_icon_car);
        home_LBL_up_to_5kg = view.findViewById(R.id.home_LBL_up_to_5kg);
        home_LBL_up_to_10kg = view.findViewById(R.id.home_LBL_up_to_10kg);
        home_IMG_icon_motorcycle = view.findViewById(R.id.home_IMG_icon_motorcycle);
        home_LAY_motorcycle_and_5kg = view.findViewById(R.id.home_LAY_motorcycle_and_5kg);
        home_LAY_motorcycle_and_10kg = view.findViewById(R.id.home_LAY_car_and_10kg);


    }

    //check if there is permission location , if there isn't ask permission
    // if there is initialize map
    private void checkAndAskPermission() {
        //when there is already permission
        if (CheckPermissions.getInstance().checkIfAlreadyHavePermissionLocation()) {
            // initialize the map
            initializeMapCustomer();

        } else {
            // request permission from the user
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CoordinatesMap.REQUEST_PERMISSIONS_CODE_LOCATION);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CoordinatesMap.REQUEST_PERMISSIONS_CODE_LOCATION) {
            // when the permission is granted
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //initialize the map
                initializeMapCustomer();

            }
        }

    }

    private void initializeMapCustomer() {
        MapManagerCustomer mapManagerCustomer = new MapManagerCustomer(this.context, this.home_map);
        mapManagerCustomer.setCallBackAddress(this);
    }

    //set text from listener in MapManagerCustomer
    @Override
    public void callBackLocation(String address) {

        home_LBL_position.setText(address);
    }


    @Override
    public void onClick(View v) {


        int id = v.getId();

        if (id == R.id.home_LAY_car_and_10kg) {
            vehicleType = true;
            ColorUtils.getInstance().changeImageViewColor(home_IMG_icon_car, R.color.orange);
            ColorUtils.getInstance().changeTextViewColor(home_LBL_up_to_10kg, R.color.orange);
            ColorUtils.getInstance().changeImageViewColor(home_IMG_icon_motorcycle, R.color.gray);
            ColorUtils.getInstance().changeTextViewColor(home_LBL_up_to_5kg, R.color.gray);
        } else if (id == R.id.home_LAY_motorcycle_and_5kg) {
            vehicleType = false;
            ColorUtils.getInstance().changeImageViewColor(home_IMG_icon_motorcycle, R.color.orange);
            ColorUtils.getInstance().changeTextViewColor(home_LBL_up_to_5kg, R.color.orange);
            ColorUtils.getInstance().changeImageViewColor(home_IMG_icon_car, R.color.gray);
            ColorUtils.getInstance().changeTextViewColor(home_LBL_up_to_10kg, R.color.gray);
        } else if (id == R.id.home_BTN_order_delivery) {
            String vehicleType = whichVehicleMakeDelivery();
            openDeliveryIntent(vehicleType);
        }


    }

    private String whichVehicleMakeDelivery() {
        if (vehicleType) {
            return StaticVariables.CAR;
        } else {
            return StaticVariables.MOTORCYCLE;

        }

    }

    private void openDeliveryIntent(String vehicleType) {
        Intent intent = new Intent(getActivity(), OrderDelivery.class);
        // pass the address without a country name
        intent.putExtra(ORIGIN, home_LBL_position.getText().toString().replace(", " + StaticVariables.ISRAEL, ""));

        intent.putExtra(CAR_OR_MOTORCYCLE, vehicleType);
        startActivity(intent);

    }


}

