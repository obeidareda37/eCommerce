package com.example.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.models.NewProducts;
import com.example.ecommerce.ui.activity.DetailedActivity;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.NewProductViewHolder> {

    private Context context;
    private List<NewProducts> list;

    public NewProductsAdapter(Context context, List<NewProducts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewProductViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImage);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewProductViewHolder extends RecyclerView.ViewHolder {

        ImageView newImage;
        TextView newName, newPrice;
        public NewProductViewHolder(@NonNull View itemView) {
            super(itemView);
            newImage = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice =itemView.findViewById(R.id.new_price);

        }
    }
}
