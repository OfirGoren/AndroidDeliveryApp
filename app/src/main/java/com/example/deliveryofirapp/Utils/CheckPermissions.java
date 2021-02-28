package com.example.deliveryofirapp.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class CheckPermissions {
    @SuppressLint("StaticFieldLeak")
    private static CheckPermissions instance;
    private final Context context;

    private CheckPermissions(Context context) {
        this.context = context;

    }

    public static CheckPermissions getInstance() {

        return instance;

    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new CheckPermissions(context);
        }
    }

    public boolean checkIfAlreadyHavePermissionSReadStorage() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkIfAlreadyHavePermissionLocation() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
