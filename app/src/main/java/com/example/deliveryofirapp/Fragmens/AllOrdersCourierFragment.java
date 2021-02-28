package com.example.deliveryofirapp.Fragmens;

import android.content.Intent;
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

import com.example.deliveryofirapp.Interface.CallBackAfterTakeDelivery;
import com.example.deliveryofirapp.Interface.CallBackAllOrdersList;
import com.example.deliveryofirapp.Objects.AdapterAllOrders;
import com.example.deliveryofirapp.Objects.DataBaseManager;
import com.example.deliveryofirapp.Objects.OrderDetails;
import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.IntentUtils;

import java.util.ArrayList;

public class AllOrdersCourierFragment extends Fragment implements AdapterAllOrders.ItemClickListener, CallBackAllOrdersList {

    private View view;
    private RecyclerView fragment_all_orders_LST_orders;
    private ArrayList<OrderDetails> arrayAllOrder;
    private CallBackAfterTakeDelivery callBackAfterTakeDelivery;
    private ProgressBar all_orders_progressBar;
    private DataBaseManager dataBaseManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_all_orders_courier, container, false);

        arrayAllOrder = new ArrayList<>();
        findViewsId();
        dataBaseManager = new DataBaseManager();
        dataBaseManager.setCallBackAllOrdersList(this);
        dataBaseManager.getAllOrderList();


        return view;

    }


    public void setCallBackAfterTakeDelivery(CallBackAfterTakeDelivery callBackAfterTakeDelivery) {
        this.callBackAfterTakeDelivery = callBackAfterTakeDelivery;
    }


    private void findViewsId() {
        fragment_all_orders_LST_orders = view.findViewById(R.id.fra_all_orders_LST_orders);
        all_orders_progressBar = view.findViewById(R.id.fra_all_orders_progressBar);
    }


    @Override
    public void onItemClick(View view, int position) {


        if (arrayAllOrder != null) {
            String origin = arrayAllOrder.get(position).getOrigin();
            String destination = arrayAllOrder.get(position).getDestination();
            String name = arrayAllOrder.get(position).getName();
            String phoneNum = arrayAllOrder.get(position).getNumber();

            sendDestinationAddress(destination);
            openGoogleMapTurnByTurnNav(origin);
            setContactInfoCallBack(name, phoneNum);
            callBackCloseDrawerAndHideFragment();
            dataBaseManager.removeOrderFromAllOrdersDataBase(arrayAllOrder.get(position).getId());


        }

    }

    // close the drawer and hide current fragment in omeCourierFragment class
    private void callBackCloseDrawerAndHideFragment() {
        if (callBackAfterTakeDelivery != null) {
            callBackAfterTakeDelivery.CallBackCloseDrawerAndHideCurrentFragment();
        }
    }

    // send the name and phone number to homeCourierFragment class
    private void setContactInfoCallBack(String name, String phoneNum) {
        if (callBackAfterTakeDelivery != null) {
            callBackAfterTakeDelivery.CallBackContactDetail(name, phoneNum);
        }
    }

    // send the destination to homeCourierFragment class
    private void sendDestinationAddress(String destination) {
        if (callBackAfterTakeDelivery != null) {
            callBackAfterTakeDelivery.CallBackDestinationAddress(destination);
        }
    }

    private void openGoogleMapTurnByTurnNav(String origin) {
        Intent mapIntent = IntentUtils.getIntentOpenGoogleMap(origin);
        startActivity(mapIntent);
        activateButtonVisible();


    }

    // make button visible in homeCourierFragment class
    private void activateButtonVisible() {
        if (callBackAfterTakeDelivery != null) {
            callBackAfterTakeDelivery.CallBackArriveButton();
        }
    }

    @Override
    public void setAllOrdersListInRecyclerView(ArrayList<OrderDetails> orderDetails) {

        //when the AllOrders download from database
        //cancel the progress bar and show the recyclerView
        fragment_all_orders_LST_orders.setVisibility(View.VISIBLE);
        all_orders_progressBar.setVisibility(View.GONE);

        fragment_all_orders_LST_orders.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterAllOrders adapter = new AdapterAllOrders(getContext(), orderDetails);
        fragment_all_orders_LST_orders.setAdapter(adapter);
        arrayAllOrder = orderDetails;
        adapter.setClickListener(this);

    }

    // remove the real time database listener
    public void removeListener() {
        if (dataBaseManager != null) {
            dataBaseManager.removeListenerAllOrders();
        }
    }
}
