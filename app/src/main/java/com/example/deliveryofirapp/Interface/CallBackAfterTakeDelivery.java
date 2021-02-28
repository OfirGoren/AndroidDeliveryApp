package com.example.deliveryofirapp.Interface;

public interface CallBackAfterTakeDelivery {
    void CallBackArriveButton();

    void CallBackDestinationAddress(String destination);

    void CallBackContactDetail(String name, String phoneNum);

    void CallBackCloseDrawerAndHideCurrentFragment();
}
