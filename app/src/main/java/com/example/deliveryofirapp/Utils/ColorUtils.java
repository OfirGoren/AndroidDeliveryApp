package com.example.deliveryofirapp.Utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorUtils {

    private static ColorUtils instance;
    private Context context;

    private ColorUtils(Context context) {
        this.context = context;
    }

    public static ColorUtils getInstance() {

        return instance;

    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new ColorUtils(context);
        }
    }

    /**
     * the method get ImageView and id color and change the color of ImageViewColor
     * (good for icons)
     *
     * @param imageView set imageView that you want to change his color
     * @param idColor   set id color
     */
    public void changeImageViewColor(ImageView imageView, int idColor) {
        imageView.setColorFilter(context.getColor(idColor),
                PorterDuff.Mode.SRC_ATOP);

    }

    /**
     * the method get TextView and id color and change the color of TextView
     *
     * @param textView set textView that you want to change his color
     * @param idColor  set id color
     */
    public void changeTextViewColor(TextView textView, int idColor) {
        textView.setTextColor(context.getColor(idColor));

    }


}
