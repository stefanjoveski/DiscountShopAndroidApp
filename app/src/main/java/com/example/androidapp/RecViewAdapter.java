package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.RecViewHolder> {

    private Context context;
    private ArrayList product,brand,old_price,new_price,available;
    private String usname;
    private Double longitude, lattitude;
    private OnRecyclerListener monRecyclerListener;


    public RecViewAdapter(Context context, ArrayList product, ArrayList brand, ArrayList old_price, ArrayList new_price, ArrayList available, String usname, Double longitude, Double lattitude, OnRecyclerListener onRecyclerListener) {
        this.context = context;
        this.product = product;
        this.brand = brand;
        this.old_price = old_price;
        this.new_price = new_price;
        this.available = available;
        this.usname = usname;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.monRecyclerListener = onRecyclerListener;
    }


    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new RecViewHolder(v, monRecyclerListener);


    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        holder.productName.setText(String.valueOf(product.get(position)));
        holder.productBrand.setText(String.valueOf(brand.get(position)));
        holder.productPrice.setText(String.valueOf(old_price.get(position)));
        holder.productDiscount.setText(String.valueOf(new_price.get(position)));
        holder.productExpire.setText(String.valueOf(available.get(position)));

    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName, productBrand, productPrice, productDiscount, productExpire;
        OnRecyclerListener onRecyclerListener;

        public RecViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productBrand = itemView.findViewById(R.id.productBrand);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDiscount = itemView.findViewById(R.id.productDiscount);
            productExpire = itemView.findViewById(R.id.productExpire);
            this.onRecyclerListener = onRecyclerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRecyclerListener.onRecyclerClick(getAdapterPosition());
        }
    }

    public interface OnRecyclerListener{
        void onRecyclerClick(int position);
    }
}
