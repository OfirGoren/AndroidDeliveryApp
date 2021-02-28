package com.example.deliveryofirapp.Utils;

import android.text.Editable;

import com.google.android.material.textfield.TextInputEditText;

public class EditTextUtils {

    /**
     * the method get view of TextInputEditText and return the text inside the view
     *
     * @param textInputEditText set view of textInputEditText
     * @return value inside the TextInputEditText
     */
    public static String getTextFromEditText(TextInputEditText textInputEditText) {
        String emptyPhone = "";
        Editable editable = textInputEditText.getText();
        if (editable != null) {
            return editable.toString();
        }
        return emptyPhone;
    }

}
