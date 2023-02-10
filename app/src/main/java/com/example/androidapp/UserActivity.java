package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserActivity extends AppCompatActivity implements RecViewAdapter.OnRecyclerListener {

    RecyclerView recyclerView;
    ArrayList<String> product, brand, old_price, new_price, available;
    String usname;
    DBHelper DB;
    RecViewAdapter adapter;
    Double longitude, lattitude;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODR = 100;
    Button buttonMyCart, buttonLogOutUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        FindLocation();
        buttonMyCart = findViewById(R.id.buttonMyCart);
        buttonLogOutUser = findViewById(R.id.buttonLogOutUser);

        buttonMyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), MyCart.class);
                intent.putExtra("uname", usname);
                startActivity(intent);
            }
        });

        buttonLogOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String usname = intent.getStringExtra("uname");

        DB = new DBHelper(this);
        product = new ArrayList<>();
        brand = new ArrayList<>();
        old_price = new ArrayList<>();
        new_price = new ArrayList<>();
        available = new ArrayList<>();


        recyclerView = findViewById(R.id.recView);
        adapter = new RecViewAdapter(this,product,brand,old_price,new_price,available,usname,longitude,lattitude, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void AskforPermission() {
        ActivityCompat.requestPermissions(UserActivity.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, REQUEST_CODR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODR){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                FindLocation();
            }else{
                Toast.makeText(UserActivity.this, "Permission Required", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void displaydata(){
        Cursor cursor = DB.getData();
        if(cursor.getCount()==0){
            Toast.makeText(UserActivity.this, "Nema produkti", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while(cursor.moveToNext()){
                product.add(cursor.getString(0));
                brand.add(cursor.getString(1));
                old_price.add(cursor.getString(2));
                new_price.add(cursor.getString(3));
                available.add(cursor.getString(4));
            }
        }
    }

    public void FindLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        Geocoder geocoder = new Geocoder(UserActivity.this, Locale.getDefault());
                        List<Address> addressList = null;
                        try {
                            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            lattitude = addressList.get(0).getLatitude();
                            longitude = addressList.get(0).getLongitude();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            AskforPermission();
        }
    }

    @Override
    public void onRecyclerClick(int position) {
        String Product = product.get(position);
        String Brand = brand.get(position);
        String Old_price = old_price.get(position);
        String New_price = new_price.get(position);
        String Available = available.get(position).toString();
        boolean insert = DB.insertToCart(Product, Brand, Old_price, New_price, Available);
        if(insert == true){
            Toast.makeText(UserActivity.this, "Uspeshno dodaden product vo koshnichka", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(UserActivity.this, "Neuspeshno dodaden product vo koshnichka", Toast.LENGTH_SHORT).show();
        }
    }
}