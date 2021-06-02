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
import com.example.ecommerce.models.PopularProducts;
import com.example.ecommerce.ui.DetailedActivity;

import java.util.List;

public class PopularAdapter  extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private Context context;
    private List<PopularProducts> productsList;

    public PopularAdapter(Context context, List<PopularProducts> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Glide.with(context).load(productsList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(productsList.get(position).getName());
        holder.price.setText(String.valueOf(productsList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",productsList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, price;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.all_img);
            name = itemView.findViewById(R.id.all_name);
            price =itemView.findViewById(R.id.all_price);

        }
    }
}
