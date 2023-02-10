package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {
    private Context context;
    private ArrayList pname, pbrand, pold_price, pnew_price, pavailable;
    private String usname;
    private Double longitude, lattitude;
    private OnRecyclerListenerCart conRecyclerListener;


    public CartViewAdapter(Context context, ArrayList pname, ArrayList pbrand, ArrayList pold_price, ArrayList pnew_price, ArrayList pavailable, String usname, Double longitude, Double lattitude, OnRecyclerListenerCart onRecyclerListenerCart) {
        this.context = context;
        this.pname = pname;
        this.pbrand = pbrand;
        this.pold_price = pold_price;
        this.pnew_price = pnew_price;
        this.pavailable = pavailable;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.usname = usname;
        this.conRecyclerListener = onRecyclerListenerCart;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentrycart,parent,false);
        return new CartViewHolder(v, conRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.productNameC.setText(String.valueOf(pname.get(position)));
        holder.productBrandC.setText(String.valueOf(pbrand.get(position)));
        holder.productPriceC.setText(String.valueOf(pold_price.get(position)));
        holder.productDiscountC.setText(String.valueOf(pnew_price.get(position)));
        holder.productExpireC.setText(String.valueOf(pavailable.get(position)));
    }

    @Override
    public int getItemCount() {
        return pname.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productNameC, productBrandC, productPriceC, productDiscountC, productExpireC;
        OnRecyclerListenerCart onRecyclerListenerCart;
        public CartViewHolder(@NonNull View itemView, OnRecyclerListenerCart onRecyclerListenerCart) {
            super(itemView);
            productNameC = itemView.findViewById(R.id.productNameC);
            productBrandC = itemView.findViewById(R.id.productBrandC);
            productPriceC = itemView.findViewById(R.id.productPriceC);
            productDiscountC = itemView.findViewById(R.id.productDiscountC);
            productExpireC = itemView.findViewById(R.id.productExpireC);
            this.onRecyclerListenerCart = onRecyclerListenerCart;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onRecyclerListenerCart.onRecyclerClickCart(getAdapterPosition());
        }
    }

    public interface OnRecyclerListenerCart{
        void onRecyclerClickCart(int position);
    }
}
