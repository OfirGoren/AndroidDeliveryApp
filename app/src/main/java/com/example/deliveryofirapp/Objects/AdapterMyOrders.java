package com.example.deliveryofirapp.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryofirapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterMyOrders extends RecyclerView.Adapter<AdapterMyOrders.ViewHolder> {

    private final ArrayList<MyOrders> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    // data is passed into the constructor
    public AdapterMyOrders(Context context, ArrayList<MyOrders> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NotNull
    @Override
    public AdapterMyOrders.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_orders_list, parent, false);
        return new AdapterMyOrders.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyOrders myOrders = mData.get(position);
        holder.my_orders_LBL_home_address.setText(myOrders.getOrigin());
        holder.my_orders_LBL_destination.setText(myOrders.getDestination());
        holder.my_orders_LBL_date.setText(myOrders.getDate());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView my_orders_LBL_home_address;
        TextView my_orders_LBL_destination;
        TextView my_orders_LBL_date;
        ImageView my_orders_IMG_home;
        ImageView my_orders_IMG_flag;
        ImageView my_orders_IMG_date;


        ViewHolder(View itemView) {
            super(itemView);
            my_orders_LBL_home_address = itemView.findViewById(R.id.my_orders_LBL_home_address);
            my_orders_LBL_destination = itemView.findViewById(R.id.my_orders_LBL_destination);
            my_orders_LBL_date = itemView.findViewById(R.id.my_orders_LBL_date);
            my_orders_IMG_home = itemView.findViewById(R.id.my_orders_IMG_home);
            my_orders_IMG_flag = itemView.findViewById(R.id.my_orders_IMG_flag);
            my_orders_IMG_date = itemView.findViewById(R.id.my_orders_IMG_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    MyOrders getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(AdapterMyOrders.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
