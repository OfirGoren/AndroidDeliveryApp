package com.example.deliveryofirapp.Utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {


    /**
     * get the current date including time
     *
     * @return current date and time
     */
    public static String currentDate() {
        DateFormat df = new SimpleDateFormat("d MMM, yyyy 'at' HH:mm:ss a", Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());

    }
}