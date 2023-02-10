package com.example.androidapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create Table products(product TEXT, brand TEXT, old_price TEXT, new_price TEXT, available TEXT)");
        MyDB.execSQL("create Table mycart(pname TEXT, pbrand TEXT, pold_price TEXT, pnew_price TEXT, pavailable TEXT)");
        MyDB.execSQL("create Table orders(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,oName TEXT, oCountry TEXT, oCity TEXT, oStreet TEXT, productNames TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists products");
        MyDB.execSQL("drop Table if exists mycart");
        MyDB.execSQL("drop Table if exists orders");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean insertProduct(String productName, String brandName, String oldP, String newP, String avail){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("product", productName);
        contentValues.put("brand", brandName);
        contentValues.put("old_price", oldP);
        contentValues.put("new_price", newP);
        contentValues.put("available", avail);
        long result = MyDB.insert("products", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM products", null);
        return cursor;
    }

    public boolean insertToCart(String PName, String BName, String OP, String NP, String Avail){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("pname", PName);
        contentValues.put("pbrand", BName);
        contentValues.put("pold_price", OP);
        contentValues.put("pnew_price", NP);
        contentValues.put("pavailable", Avail);
        long result = MyDB.insert("mycart", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getDataCart(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM mycart", null);
        return cursor;
    }

    public boolean deleteFromCart(String prodname){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("mycart", "pname=?", new String[] {prodname}) > 0;
    }

    public List<String> getNamesCart(){
        List<String> stringList = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT pname FROM mycart", null);

        if(cursor != null){
            cursor.moveToFirst();
            while(cursor.isAfterLast() == false){
                String names = (cursor.getString(cursor.getColumnIndexOrThrow("pname")));
                stringList.add(names);
                cursor.moveToNext();
            }
        }
        return stringList;
    }

    public boolean insertOrder(String orderName, String orderCountry, String orderCity, String orderStreet, String pNames){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("oName", orderName);
        contentValues.put("oCountry", orderCountry);
        contentValues.put("oCity", orderCity);
        contentValues.put("oStreet", orderStreet);
        contentValues.put("productNames", pNames);
        long result = MyDB.insert("orders", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean EmptyCart(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("mycart", null,null) > 0;
    }

    public Cursor getDataOrders(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM orders", null);
        return cursor;
    }
}