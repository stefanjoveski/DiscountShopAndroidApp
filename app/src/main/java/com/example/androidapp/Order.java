package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {
    EditText orderName, orderCountry, orderCity, orderStreet;
    Button btnOrderNow;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderName = (EditText) findViewById(R.id.orderName);
        orderCountry = (EditText) findViewById(R.id.orderCountry);
        orderCity = (EditText) findViewById(R.id.orderCity);
        orderStreet = (EditText) findViewById(R.id.orderStreet);
        btnOrderNow = (Button) findViewById(R.id.btnOrderNow);
        DB = new DBHelper(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("notifikacija1", "notifikacija1", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oName = orderName.getText().toString();
                String oCountry = orderCountry.getText().toString();
                String oCity = orderCity.getText().toString();
                String oStreet = orderStreet.getText().toString();
                List<String> orderedProducts;
                orderedProducts = DB.getNamesCart();
                String oProd = String.join(", ", orderedProducts);


                boolean insert = DB.insertOrder(oName, oCountry, oCity, oStreet, oProd);
                if(insert == true){
                    Toast.makeText(Order.this, "Uspeshno ispratena narachka", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Order.this, "Neuspeshno ispratena narachka", Toast.LENGTH_SHORT).show();
                }
                boolean empty = DB.EmptyCart();
                if(empty == true){
                    //Intent intent  = new Intent(getApplicationContext(), UserActivity.class);
                    //startActivity(intent);
                    Intent intentNot = new Intent(Order.this, UserActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            Order.this,
                            101,
                            intentNot,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Order.this, "notifikacija1");
                    builder.setContentTitle("Uspeshna narachka");
                    builder.setContentText("Vashata naracha e uspeshno ispratena!");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setContentIntent(pendingIntent);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Order.this);
                    managerCompat.notify(1, builder.build());
                }else{
                    Toast.makeText(Order.this, "Neuspeshno ispratena narachka", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}