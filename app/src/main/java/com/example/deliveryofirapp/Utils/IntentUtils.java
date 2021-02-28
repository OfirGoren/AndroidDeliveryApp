package com.example.deliveryofirapp.Utils;

import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    /**
     * @param phoneNum set phone number that you want to open the Dialer
     * @return intent
     * after you get the intent , call startActivity(intent) to open the Dialer
     * with your number
     */
    public static Intent getIntentOpenDialer(String phoneNum) {
        Intent intent;
        if (phoneNum == null) {
            phoneNum = "";
        }

        intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));

        return intent;
    }

    /**
     * the method get address and return intent.
     *
     * @param address set address that you want to navigate with Google Map
     * @return intent
     * after you get the intent , call startActivity(intent) to activate the Google navigation
     */
    public static Intent getIntentOpenGoogleMap(String address) {

        if (address == null) {
            address = "";
        }
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        return mapIntent;
    }


}