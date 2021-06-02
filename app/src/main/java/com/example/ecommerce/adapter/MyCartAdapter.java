package com.example.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.MyCart;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    Context context;
    List<MyCart> list;
    int totalAmount=0;

    public MyCartAdapter(Context context, List<MyCart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, int position) {

        holder.date.setText(list.get(position).getCurrentDate());
        holder.name.setText(list.get(position).getProductName());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"$");
        holder.totalPrice.setText(list.get(position).getTotalPrice()+"$");
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());

        //Total amount pass to Car Activity
        totalAmount = totalAmount+ list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder{
        TextView name ,price , date , time ,totalQuantity , totalPrice;
        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.product_Name);
            price =itemView.findViewById(R.id.product_Price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_Time);
            totalQuantity =itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}
