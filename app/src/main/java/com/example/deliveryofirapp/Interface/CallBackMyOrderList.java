package com.example.deliveryofirapp.Interface;

import com.example.deliveryofirapp.Objects.MyOrders;

import java.util.ArrayList;

public interface CallBackMyOrderList {

    void setOrderListInRecyclerView(ArrayList<MyOrders> myOrders);

}
