package com.example.deliveryofirapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.EditTextUtils;
import com.example.deliveryofirapp.Utils.SharedPref;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnectActivity extends AppCompatActivity {
    public static final String PHONE_NUMBER_KEY = "PHONE NUMBER";
    public static final String CHECK_BOX_KEY = "CHECK_BOX";
    private TextInputEditText connect_EDT_phone_number;
    private CheckBox connect_CBX_kind_account;
    private MaterialButton connect_BTN_log_in;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);


        user = FirebaseAuth.getInstance().getCurrentUser();

        //if the user already connected open intent according his register
        checkAndAutoConnected();

        findViewsId();
        logInClicked();


    }

    /*
      check if the user already connected , and if it's yes ,
      connect him according his account (customer or courier)
     */
    private void checkAndAutoConnected() {

        boolean customerOrCourier = SharedPref.getInstance().getBoolean(VerifyActivity.KEY_CHECK_BOX_STATE, false);
        if (user != null && customerOrCourier) {
            Intent intent = new Intent(ConnectActivity.this, CourierActivity.class);
            this.startActivity(intent);
        } else if (user != null) {
            Intent intent = new Intent(ConnectActivity.this, CustomerActivity.class);
            this.startActivity(intent);
        }

    }

    private void logInClicked() {
        connect_BTN_log_in.setOnClickListener(v -> {
            openNewIntent();
            clearEditText();
        });

    }

    private void clearEditText() {

        connect_EDT_phone_number.setText("");
    }

    private void openNewIntent() {

        String phoneNum = EditTextUtils.getTextFromEditText(connect_EDT_phone_number);
        Boolean isCheck = connect_CBX_kind_account.isChecked();
        Intent myIntent = new Intent(ConnectActivity.this, VerifyActivity.class);
        myIntent.putExtra(PHONE_NUMBER_KEY, phoneNum);
        myIntent.putExtra(CHECK_BOX_KEY, isCheck);
        this.startActivity(myIntent);
    }


    private void findViewsId() {
        connect_EDT_phone_number = findViewById(R.id.connect_EDT_phone_number);
        connect_CBX_kind_account = findViewById(R.id.connect_CBX_kind_account);
        connect_BTN_log_in = findViewById(R.id.connect_BTN_log_in);

    }


}