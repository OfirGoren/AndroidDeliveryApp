package com.example.deliveryofirapp.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryofirapp.R;
import com.example.deliveryofirapp.Utils.ColorUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class AdapterAllOrders extends RecyclerView.Adapter<AdapterAllOrders.ViewHolder> {


    private final ArrayList<OrderDetails> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context context;

    // data is passed into the constructor
    public AdapterAllOrders(Context context, ArrayList<OrderDetails> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_all_orders, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDetails orderDetails = mData.get(position);
        holder.all_orders_LBL_name.setText(orderDetails.getName());
        holder.all_orders_LBL_number.setText(orderDetails.getNumber());
        holder.all_orders_LBL_home_address.setText(orderDetails.getOrigin());
        holder.all_orders_LBL_destination.setText(orderDetails.getDestination());
        holder.all_orders_IMG_car_or_motorcycle.setImageResource(context.getResources().
                getIdentifier(orderDetails.getIconName(), "drawable", context.getPackageName()));
        ColorUtils.getInstance().changeImageViewColor(holder.all_orders_IMG_car_or_motorcycle, R.color.orange);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView all_orders_LBL_name;
        TextView all_orders_LBL_number;
        TextView all_orders_LBL_home_address;
        TextView all_orders_LBL_destination;
        ImageView all_orders_IMG_person;
        ImageView all_orders_IMG_phone;
        ImageView all_orders_IMG_flag;
        ImageView all_orders_IMG_home;
        ImageView all_orders_IMG_car_or_motorcycle;


        ViewHolder(View itemView) {
            super(itemView);
            all_orders_LBL_name = itemView.findViewById(R.id.all_orders_LBL_name);
            all_orders_LBL_number = itemView.findViewById(R.id.all_orders_LBL_number);
            all_orders_LBL_home_address = itemView.findViewById(R.id.all_orders_LBL_home_address);
            all_orders_LBL_destination = itemView.findViewById(R.id.all_orders_LBL_destination);
            all_orders_IMG_person = itemView.findViewById(R.id.all_orders_IMG_person);
            all_orders_IMG_phone = itemView.findViewById(R.id.all_orders_IMG_phone);
            all_orders_IMG_flag = itemView.findViewById(R.id.all_orders_IMG_flag);
            all_orders_IMG_home = itemView.findViewById(R.id.all_orders_IMG_home);
            all_orders_IMG_car_or_motorcycle = itemView.findViewById(R.id.all_orders_IMG_car_or_motorcycle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    OrderDetails getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

