package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
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

public class MyCart extends AppCompatActivity implements CartViewAdapter.OnRecyclerListenerCart {
    RecyclerView recyclerViewC;
    ArrayList<String> pname, pbrand, pold_price, pnew_price, pavailable;
    DBHelper DB;
    CartViewAdapter cartViewAdapter;
    Double longitude, lattitude;
    String usname;
    Button buttonOrder;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODR = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        FindLocation();

        Intent intent = getIntent();
        String usname = intent.getStringExtra("uname");
        DB = new DBHelper(this);
        pname = new ArrayList<>();
        pbrand = new ArrayList<>();
        pold_price = new ArrayList<>();
        pnew_price = new ArrayList<>();
        pavailable = new ArrayList<>();
        recyclerViewC = findViewById(R.id.recyclerViewCart);
        cartViewAdapter = new CartViewAdapter(this,pname,pbrand,pold_price,pnew_price,pavailable,usname,longitude,lattitude, this);
        recyclerViewC.setAdapter(cartViewAdapter);
        recyclerViewC.setLayoutManager(new LinearLayoutManager(this));
        displaydataC();
        buttonOrder = findViewById(R.id.buttonOrder);

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), Order.class);
                startActivity(intent);
            }
        });
    }

    private void AskforPermission() {
        ActivityCompat.requestPermissions(MyCart.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, REQUEST_CODR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODR){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                FindLocation();
            }else{
                Toast.makeText(MyCart.this, "Permission Required", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void FindLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        Geocoder geocoder = new Geocoder(MyCart.this, Locale.getDefault());
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

    private void displaydataC() {
        Cursor cursorr = DB.getDataCart();
        if(cursorr.getCount()==0){
            Toast.makeText(MyCart.this, "Nemate produkti vo koshnichkata", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while(cursorr.moveToNext()){
                pname.add(cursorr.getString(0));
                pbrand.add(cursorr.getString(1));
                pold_price.add(cursorr.getString(2));
                pnew_price.add(cursorr.getString(3));
                pavailable.add(cursorr.getString(4));
            }
        }
    }

    @Override
    public void onRecyclerClickCart(int position) {
        String prodname = pname.get(position);
        DB.deleteFromCart(prodname);
    }
}