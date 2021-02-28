package com.example.deliveryofirapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliveryofirapp.Fragmens.HomeCustomerFragment;
import com.example.deliveryofirapp.Objects.DataBaseManager;
import com.example.deliveryofirapp.Objects.OrderDetails;
import com.example.deliveryofirapp.Objects.StaticVariables;
import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.ColorUtils;
import com.example.deliveryofirapp.Utils.Date;
import com.example.deliveryofirapp.Utils.EditTextUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class OrderDelivery extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout order_LAY_car_and_up_to_10kg;
    private LinearLayout order_LAY_motorcycle_and_5kg;
    private TextView order_LBL_up_to_5kg;
    private LinearLayout order_LAY_back;
    private TextInputEditText order_EDT_origin;
    private TextInputEditText order_EDT_destination;
    private TextInputEditText order_EDT_phone;
    private TextInputEditText order_EDT_name;
    private TextView order_LBL_up_to_10kg;
    private ImageView order_IMG_icon_motorcycle;
    private ImageView order_IMG_icon_car;
    private MaterialButton order_BTN_confirm;
    private DataBaseManager dataBaseManager;
    private String origin = "";
    private String vehicleType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_delivery);

        dataBaseManager = new DataBaseManager();
        getValuesFromIntent();
        findViewsId();
        initializeColorsIcons();
        clickListener();
        insetOriginToEdt();

    }

    private void insetOriginToEdt() {
        order_EDT_origin.setText(origin);
    }

    private void initializeColorsIcons() {
        if (vehicleType.equals(StaticVariables.CAR)) {
            ColorUtils.getInstance().changeImageViewColor(order_IMG_icon_car, R.color.orange);
            ColorUtils.getInstance().changeTextViewColor(order_LBL_up_to_10kg, R.color.orange);
            ColorUtils.getInstance().changeImageViewColor(order_IMG_icon_motorcycle, R.color.gray);
            ColorUtils.getInstance().changeTextViewColor(order_LBL_up_to_5kg, R.color.gray);
        }
    }

    private void clickListener() {
        order_LAY_car_and_up_to_10kg.setOnClickListener(this);
        order_LAY_motorcycle_and_5kg.setOnClickListener(this);
        order_BTN_confirm.setOnClickListener(this);
        order_LAY_back.setOnClickListener(this);
    }

    private void getValuesFromIntent() {
        Intent intent = getIntent();
        origin = intent.getStringExtra(HomeCustomerFragment.ORIGIN);
        vehicleType = intent.getStringExtra(HomeCustomerFragment.CAR_OR_MOTORCYCLE);

    }

    private void findViewsId() {
        order_LAY_car_and_up_to_10kg = findViewById(R.id.order_LAY_car_and_up_to_10kg);
        order_LAY_motorcycle_and_5kg = findViewById(R.id.order_LAY_motorcycle_and_5kg);
        order_LBL_up_to_5kg = findViewById(R.id.order_LBL_up_to_5kg);
        order_LBL_up_to_10kg = findViewById(R.id.order_LBL_up_to_10kg);
        order_IMG_icon_motorcycle = findViewById(R.id.order_IMG_icon_motorcycle);
        order_IMG_icon_car = findViewById(R.id.order_IMG_icon_car);
        order_EDT_origin = findViewById(R.id.order_EDT_origin);
        order_BTN_confirm = findViewById(R.id.order_BTN_confirm);
        order_EDT_destination = findViewById(R.id.order_EDT_destination);
        order_EDT_phone = findViewById(R.id.order_EDT_phone);
        order_EDT_name = findViewById(R.id.order_EDT_name);
        order_LAY_back = findViewById(R.id.order_LAY_back);

    }


    @Override
    public void onClick(View v) {


        int id = v.getId();

        if (id == R.id.order_LAY_car_and_up_to_10kg) {
            vehicleType = StaticVariables.CAR;
            ColorUtils.getInstance().changeImageViewColor(order_IMG_icon_car, R.color.orange);
            ColorUtils.getInstance().changeTextViewColor(order_LBL_up_to_10kg, R.color.orange);
            ColorUtils.getInstance().changeImageViewColor(order_IMG_icon_motorcycle, R.color.gray);
            ColorUtils.getInstance().changeTextViewColor(order_LBL_up_to_5kg, R.color.gray);

        } else if (id == R.id.order_LAY_motorcycle_and_5kg) {
            vehicleType = StaticVariables.MOTORCYCLE;
            ColorUtils.getInstance().changeImageViewColor(order_IMG_icon_motorcycle, R.color.orange);
            ColorUtils.getInstance().changeTextViewColor(order_LBL_up_to_5kg, R.color.orange);
            ColorUtils.getInstance().changeImageViewColor(order_IMG_icon_car, R.color.gray);
            ColorUtils.getInstance().changeTextViewColor(order_LBL_up_to_10kg, R.color.gray);
        } else if (id == R.id.order_BTN_confirm) {
            saveInFireBase();
            finish();
        } else if (id == R.id.order_LAY_back) {
            finish();
        }
    }

    private void saveInFireBase() {

        OrderDetails orderDetails = new OrderDetails()
                .setNumber(EditTextUtils.getTextFromEditText(order_EDT_phone))
                .setName(EditTextUtils.getTextFromEditText(order_EDT_name))
                .setOrigin(EditTextUtils.getTextFromEditText(order_EDT_origin))
                .setDestination(EditTextUtils.getTextFromEditText(order_EDT_destination))
                .setDate(Date.currentDate())
                .setIconName(vehicleType.toLowerCase());

        dataBaseManager.uploadOrderToServer(orderDetails);
        dataBaseManager.uploadOrderToServerDifferentPath(orderDetails);
    }


}