package com.example.deliveryofirapp.Objects;

import com.example.deliveryofirapp.Interface.CallBackImage;
import com.example.deliveryofirapp.Utils.BitmapUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoadPicture implements CallBackImage {
    DataBaseManager dataBaseManager;
    CircleImageView nav_header_camera;

    public LoadPicture(CircleImageView circleImageView) {
        dataBaseManager = new DataBaseManager();
        dataBaseManager.setCallBackImage(this);
        dataBaseManager.getImageFromServer();
        this.nav_header_camera = circleImageView;


    }

    //when the picture download from the server update the view
    @Override
    public void CallBackImage(String path) {
        if (path != null && this.nav_header_camera != null) {
            this.nav_header_camera.setImageBitmap(BitmapUtils.getInstance().getImageBitmapAccordingPathImage(path));
        }
    }

}

