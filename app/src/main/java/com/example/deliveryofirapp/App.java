package com.example.deliveryofirapp;

import android.app.Application;

import com.example.deliveryofirapp.Utils.Addresses;
import com.example.deliveryofirapp.Utils.BitmapUtils;
import com.example.deliveryofirapp.Utils.ColorUtils;
import com.example.deliveryofirapp.Utils.CoordinatesMap;
import com.example.deliveryofirapp.Utils.ImageUtils;
import com.example.deliveryofirapp.Utils.CheckPermissions;
import com.example.deliveryofirapp.Utils.SharedPref;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Addresses.init(this);
        SharedPref.init(this);
        CoordinatesMap.init(this);
        BitmapUtils.init(this);
        ImageUtils.init(this);
        ColorUtils.init(this);
        CheckPermissions.init(this);


    }

}
