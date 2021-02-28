package com.example.deliveryofirapp.Fragmens;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryofirapp.Interface.CallBackMyOrderList;
import com.example.deliveryofirapp.Objects.AdapterMyOrders;
import com.example.deliveryofirapp.Objects.DataBaseManager;
import com.example.deliveryofirapp.Objects.MyOrders;
import com.example.deliveryofirapp.R;

import java.util.ArrayList;

public class MyOrdersCustomerFragment extends Fragment implements AdapterMyOrders.ItemClickListener, CallBackMyOrderList {

    private View view;
    private RecyclerView my_orders_LST_orders;
    private ProgressBar my_order_progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_my_orders_customer, container, false);
        findViewsId();


        return view;
    }


    private void findViewsId() {
        my_orders_LST_orders = view.findViewById(R.id.fra_my_orders_recycler_orders);
        my_order_progressBar = view.findViewById(R.id.fra_my_orders_progressBar);
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onStart() {
        super.onStart();
        turnOnCallBackMyListDataBase();


    }

    private void turnOnCallBackMyListDataBase() {
        DataBaseManager dataBaseManager = new DataBaseManager();
        dataBaseManager.setCallBackMyOrderList(this);
        dataBaseManager.getMyOrdersList();
    }

    @Override
    public void setOrderListInRecyclerView(ArrayList<MyOrders> myOrders) {

        //when the myOrderList download from database
        //cancel the progress bar and show the recyclerView
        my_order_progressBar.setVisibility(View.GONE);
        my_orders_LST_orders.setVisibility(View.VISIBLE);

        my_orders_LST_orders.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterMyOrders adapter = new AdapterMyOrders(getContext(), myOrders);
        my_orders_LST_orders.setAdapter(adapter);
        adapter.setClickListener(this);


    }
}
