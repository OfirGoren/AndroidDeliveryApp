package com.example.deliveryofirapp.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.File;

public class BitmapUtils {
    //get the application context Therefore can not be leak
    @SuppressLint("StaticFieldLeak")
    private static BitmapUtils instance;
    private final Context context;


    private BitmapUtils(Context context) {
        this.context = context;
    }

    public static BitmapUtils getInstance() {
        return instance;
    }

    public static void init(Context context) {

        if (instance == null) {
            instance = new BitmapUtils(context);
        }

    }


    /**
     * the method take icon and create bitmap with the size you put
     *
     * @param vectorResId set vector id
     * @param size        set size of the icon
     * @return return the BitmapDescriptorFactory.fromBitmap(bitmap)
     */

    public BitmapDescriptor bitmapDescriptorFromVector(int vectorResId, int size) {


        Drawable vectorDrawable = ContextCompat.getDrawable(this.context, vectorResId);

        vectorDrawable.setBounds(0, 0, size, size);
        Bitmap bitmap = Bitmap.createBitmap(size, size, android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    /**
     * the method get the image path and return bitmap with the image
     *
     * @param path set image path
     * @return Bitmap
     */
    public Bitmap getImageBitmapAccordingPathImage(String path) {

        Bitmap myBitmap = null;
        if (path != null) {
            File imgFile = new File(path);

            if (imgFile.exists()) {

                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


            }
        }
        return myBitmap;
    }

}