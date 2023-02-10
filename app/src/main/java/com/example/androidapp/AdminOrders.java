package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminOrders extends AppCompatActivity {
    RecyclerView recyclerViewOrders;
    ArrayList<String> ordName, ordCountry, ordCity, ordStreet, ordProducts;
    DBHelper DB;
    OrdersViewAdapter ordersViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        DB = new DBHelper(this);
        ordName = new ArrayList<>();
        ordCountry = new ArrayList<>();
        ordCity = new ArrayList<>();
        ordStreet = new ArrayList<>();
        ordProducts = new ArrayList<>();
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        ordersViewAdapter = new OrdersViewAdapter(this, ordName, ordCountry, ordCity, ordStreet, ordProducts);
        recyclerViewOrders.setAdapter(ordersViewAdapter);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        displaydataOrders();

    }

    private void displaydataOrders() {
        Cursor cursor = DB.getDataOrders();
        if(cursor.getCount() == 0){
            Toast.makeText(AdminOrders.this, "Nema narachki", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while(cursor.moveToNext()){
                ordName.add(cursor.getString(1));
                ordCountry.add(cursor.getString(2));
                ordCity.add(cursor.getString(3));
                ordStreet.add(cursor.getString(4));
                ordProducts.add(cursor.getString(5));
            }
        }
    }
}