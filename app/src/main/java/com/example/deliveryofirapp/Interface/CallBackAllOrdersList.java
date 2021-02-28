package com.example.deliveryofirapp.Interface;

import com.example.deliveryofirapp.Objects.OrderDetails;

import java.util.ArrayList;

public interface CallBackAllOrdersList {

    void setAllOrdersListInRecyclerView(ArrayList<OrderDetails> orderDetails);
}
