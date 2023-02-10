package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    TextView txtViewProduct, txtViewBrand, txtViewPrice, txtViewDiscount, txtViewExpire;
    EditText editTxtProduct, editTxtBrand, editTxtPrice, editTxtDiscount, editTxtExpire;
    Button buttonAddProduct, buttonLogOut, buttonAdminOrders, buttonMap;
    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtViewProduct = findViewById(R.id.txtViewProduct);
        txtViewBrand = findViewById(R.id.txtViewBrand);
        txtViewPrice = findViewById(R.id.txtViewPrice);
        txtViewDiscount = findViewById(R.id.txtViewDiscount);
        txtViewExpire = findViewById(R.id.txtViewExpire);
        editTxtProduct = findViewById(R.id.editTxtProduct);
        editTxtBrand = findViewById(R.id.editTxtBrand);
        editTxtPrice = findViewById(R.id.editTxtPrice);
        editTxtDiscount = findViewById(R.id.editTxtDiscount);
        editTxtExpire = findViewById(R.id.editTxtExpire);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAdminOrders = findViewById(R.id.buttonAdminOrders);
        buttonMap = findViewById(R.id.buttonMap);
        DB = new DBHelper(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("notifikacija", "notifikacija", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product = editTxtProduct.getText().toString();
                String brand = editTxtBrand.getText().toString();
                String old_price = editTxtPrice.getText().toString();
                String new_price = editTxtDiscount.getText().toString();
                String expire = editTxtExpire.getText().toString();
                boolean insert = DB.insertProduct(product, brand, old_price, new_price, expire);
                if (insert == true) {
                    Toast.makeText(AdminActivity.this, "Uspeshno vnesen product", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Neuspeshno vnesen product", Toast.LENGTH_SHORT).show();
                }

                Intent intentNot = new Intent(AdminActivity.this, UserActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        AdminActivity.this,
                        100,
                        intentNot,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

                NotificationCompat.Builder builder = new NotificationCompat.Builder(AdminActivity.this, "notifikacija");
                builder.setContentTitle("Nov produkt dostapen!");
                builder.setContentText("Dodaden nov produkt: " + product);
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AdminActivity.this);
                managerCompat.notify(1, builder.build());
            }

        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        buttonAdminOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), AdminOrders.class);
                startActivity(intent);
            }
        });

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });


    }


}