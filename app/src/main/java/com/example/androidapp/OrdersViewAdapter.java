package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersViewAdapter extends RecyclerView.Adapter<OrdersViewAdapter.OrdersViewHolder> {
    private Context context;
    private ArrayList orName, orCountry, orCity, orStreet, orProducts;

    public OrdersViewAdapter(Context context, ArrayList orName, ArrayList orCountry, ArrayList orCity, ArrayList orStreet, ArrayList orProducts) {
        this.context = context;
        this.orName = orName;
        this.orCountry = orCountry;
        this.orCity = orCity;
        this.orStreet = orStreet;
        this.orProducts = orProducts;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentryorders, parent, false);
        return new OrdersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        holder.orName.setText(String.valueOf(orName.get(position)));
        holder.orCountry.setText(String.valueOf(orCountry.get(position)));
        holder.orCity.setText(String.valueOf(orCity.get(position)));
        holder.orStreet.setText(String.valueOf(orStreet.get(position)));
        holder.orProducts.setText(String.valueOf(orProducts.get(position)));
    }

    @Override
    public int getItemCount() {
        return orName.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView orName, orCountry, orCity, orStreet, orProducts;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            orName = itemView.findViewById(R.id.fromName);
            orCountry = itemView.findViewById(R.id.fromCountry);
            orCity = itemView.findViewById(R.id.fromCity);
            orStreet = itemView.findViewById(R.id.fromStreet);
            orProducts = itemView.findViewById(R.id.fromProducts);

        }
    }
}
