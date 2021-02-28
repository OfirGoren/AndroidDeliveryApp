package com.example.deliveryofirapp.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageUtils {


    private final Context context;
    //get the application context Therefore can not be leak
    @SuppressLint("StaticFieldLeak")
    public static ImageUtils instance;

    private ImageUtils(Context context) {
        this.context = context;
    }

    public static ImageUtils getInstance() {

        return instance;
    }

    public static void init(Context context) {

        if (instance == null) {
            instance = new ImageUtils(context);
        }

    }

    /**
     * the method get uri and return the Image Path of the picture
     *
     * @param uri set uri Image
     * @return image Path
     */
    public String getImagePath(Uri uri) {

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


}
