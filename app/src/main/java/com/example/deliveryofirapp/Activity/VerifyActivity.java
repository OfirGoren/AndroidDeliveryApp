package com.example.deliveryofirapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.SharedPref;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_CHECK_BOX_STATE = "KEY_CHECK_BOX_STATE";

    private String verificationCodeBySystem;
    private ProgressBar verify_progressBar;
    private LinearLayout verify_LAY_back;
    private String phoneNum = "+972";
    private boolean isCheck;
    private MaterialButton verify_BTN_verify_code;
    private TextInputLayout verify_EDT_number_verify;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        viewsById();
        infoFromIntent();
        startProcessingAuth();
        VerifyButtonClickListener();
        saveInJsonCheckBoxState();
        clickListener();

    }

    private void clickListener() {
        verify_LAY_back.setOnClickListener(this);
    }

    private void saveInJsonCheckBoxState() {
        SharedPref.getInstance().putBoolean(KEY_CHECK_BOX_STATE, isCheck);

    }

    private void VerifyButtonClickListener() {
        verify_BTN_verify_code.setOnClickListener(v -> {
            String code = verify_EDT_number_verify.getEditText().getText().toString();
            verify_progressBar.setVisibility(View.VISIBLE);
            verifyCode(code);

        });
    }

    private void viewsById() {
        verify_EDT_number_verify = findViewById(R.id.verify_EDT_number_verify);
        verify_BTN_verify_code = findViewById(R.id.verify_BTN_verify_code);
        verify_progressBar = findViewById(R.id.verify_progressBar);
        verify_LAY_back = findViewById(R.id.verify_LAY_back);

    }

    private void infoFromIntent() {
        Intent intent = getIntent();
        phoneNum += intent.getStringExtra(ConnectActivity.PHONE_NUMBER_KEY);

        isCheck = intent.getBooleanExtra(ConnectActivity.CHECK_BOX_KEY, false);


    }

    private void startProcessingAuth() {

        mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // The SMS verification code has been sent to the provided phone number
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

    };

    private void toast(String message) {
        Toast.makeText(VerifyActivity.this, message, Toast.LENGTH_LONG).show();

    }

    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            verify_progressBar.setVisibility(View.INVISIBLE);
            toast("Try from the beginning");
            finish();
        }


    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        //if the check box is checked
                        if (isCheck) {
                            openCourierIntent();
                        } else {
                            openCustomerIntent();
                        }
                        finish();
                        // Sign in success, update UI with the signed-in user's information
                        // Log.d(TAG, "signInWithCredential:success");

                    } else {
                        // Sign in failed, display a message and update the UI
                        toast("Try Again");
                        verify_progressBar.setVisibility(View.INVISIBLE);
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }

    private void openCourierIntent() {
        Intent intent = new Intent(VerifyActivity.this, CourierActivity.class);
        this.startActivity(intent);

    }

    private void openCustomerIntent() {
        Intent intent = new Intent(VerifyActivity.this, CustomerActivity.class);
        this.startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.verify_LAY_back) {
            finish();

        }


    }


}