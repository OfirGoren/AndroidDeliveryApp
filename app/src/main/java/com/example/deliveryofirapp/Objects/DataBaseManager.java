package com.example.deliveryofirapp.Objects;

import androidx.annotation.NonNull;

import com.example.deliveryofirapp.Interface.CallBackAllOrdersList;
import com.example.deliveryofirapp.Interface.CallBackImage;
import com.example.deliveryofirapp.Interface.CallBackMyOrderList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DataBaseManager {

    private final String USERS = "users";
    private final String ALL_ORDERS = "allOrders";
    private final String IMAGE = "image";

    private CallBackMyOrderList callBackMyOrderList;
    private CallBackAllOrdersList callBackAllOrdersList;
    private CallBackImage callBackImage;
    private final FirebaseDatabase database;
    private DatabaseReference myRef;
    private final FirebaseUser user;
    private final ArrayList<OrderDetails> AllOrders;
    private final ArrayList<MyOrders> myOrders;
    private ValueEventListener mValueEventListener;

    public DataBaseManager() {
        this.database = FirebaseDatabase.getInstance();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.AllOrders = new ArrayList<>();
        this.myOrders = new ArrayList<>();

    }

    public void setCallBackImage(CallBackImage callBackImage) {
        this.callBackImage = callBackImage;

    }

    public void setCallBackMyOrderList(CallBackMyOrderList callBackMyOrderList) {
        this.callBackMyOrderList = callBackMyOrderList;
    }

    public void setCallBackAllOrdersList(CallBackAllOrdersList callBackAllOrdersList) {
        this.callBackAllOrdersList = callBackAllOrdersList;
    }

    // upload the order detail to server according to user id
    public void uploadOrderToServer(OrderDetails orderDetail) {
        this.myRef = database.getReference(USERS);
        DatabaseReference databaseParticular = this.myRef.child(this.user.getUid()).push();
        orderDetail.setId(databaseParticular.getKey());
        databaseParticular.setValue(orderDetail);


    }

    // upload the order detail to server together
    public void uploadOrderToServerDifferentPath(OrderDetails orderDetail) {
        this.myRef = database.getReference(ALL_ORDERS);
        DatabaseReference databaseParticular = this.myRef.push();
        orderDetail.setId(databaseParticular.getKey());
        databaseParticular.setValue(orderDetail);
    }

    public void uploadImageUriToServer(String uri) {
        this.myRef = database.getReference(USERS);
        this.myRef.child(this.user.getUid()).child(IMAGE).setValue(uri);

    }


    public void getImageFromServer() {
        this.myRef = database.getReference(USERS);
        myRef.child(this.user.getUid()).child(IMAGE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                String path = dataSnapshot.getValue(String.class);
                activateCallBackImage(path);

            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    private void activateCallBackImage(String path) {

        if (this.callBackImage != null) {
            callBackImage.CallBackImage(path);
        }
    }


    public void getMyOrdersList() {
        this.myRef = database.getReference(USERS);
        myRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    //when the key not image
                    if (!child.getKey().equals(IMAGE)) {
                        //get the my order from server
                        OrderDetails order = child.getValue(OrderDetails.class);
                        //add to array list
                        myOrders.add(new MyOrders(order.getOrigin(), order.getDestination(), order.getDate()));
                    }
                }
                // when we add all the my orders from server
                activateCallBackMyOrderList(myOrders);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    public void removeListenerAllOrders() {

        this.myRef = database.getReference("allOrders");
        if (mValueEventListener != null) {
            myRef.removeEventListener(mValueEventListener);

        }

    }

    public void getAllOrderList() {

        this.myRef = database.getReference("allOrders");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AllOrders.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    if (!child.getKey().equals(IMAGE)) {
                        OrderDetails order = child.getValue(OrderDetails.class);
                        AllOrders.add(order);
                    }
                }
                //when we finish to add all the orders from server
                activateCallBackAllOrdersList(AllOrders);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
        mValueEventListener = valueEventListener;


    }


    private void activateCallBackAllOrdersList(ArrayList<OrderDetails> orderDetails) {
        if (this.callBackAllOrdersList != null) {
            this.callBackAllOrdersList.setAllOrdersListInRecyclerView(orderDetails);
        }
    }


    private void activateCallBackMyOrderList(ArrayList<MyOrders> myOrders) {

        if (this.callBackMyOrderList != null) {
            callBackMyOrderList.setOrderListInRecyclerView(myOrders);
        }

    }


    public void removeOrderFromAllOrdersDataBase(String id) {
        this.myRef = database.getReference(ALL_ORDERS);
        this.myRef.child(id).removeValue();


    }
}
