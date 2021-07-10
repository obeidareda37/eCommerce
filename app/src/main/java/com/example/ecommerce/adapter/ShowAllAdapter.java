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
import com.example.ecommerce.models.ShowAll;
import com.example.ecommerce.ui.activity.DetailedActivity;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ShowAllViewHolder> {
    private Context context;
    private List<ShowAll> list;

    public ShowAllAdapter(Context context, List<ShowAll> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShowAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowAllViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.itemImage);
        holder.name.setText(list.get(position).getName());
        holder.cost.setText("$"+list.get(position).getPrice());
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

    public class ShowAllViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView cost;
        private TextView name;

        public ShowAllViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
            cost = itemView.findViewById(R.id.item_cost);
            name = itemView.findViewById(R.id.item_name);
        }
    }
}
