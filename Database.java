package com.example.reds0n.foodorderclient;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.reds0n.foodorderclient.orders;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reds0n on 3/31/18.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="myDB.db";//RestooDB  was here
    private static final int DB_VER=1;
    public Database(Context context) {
        super(context, DB_NAME, null,DB_VER );
    }

    public List<orders> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Productid,Productname,Quantity,Price,Discount,Image"};
        String sqlTable = "OrderDetails";


        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<orders> results = new ArrayList<>();
        if(c.moveToFirst()){
            do {
                results.add(new orders(c.getString(c.getColumnIndex("Productid")),
                        c.getString(c.getColumnIndex("Productname")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("Image"))
                ));
            } while (c.moveToNext());
        }

        return results;
    }


    public void addToCart(orders Orders){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetails(Productid,Productname,Quantity,Price,Discount,Image) VALUES('%s','%s','%s','%s','%s','%s');",
                Orders.getProductid(),
                Orders.getProductname(),
                Orders.getQuantity(),
                Orders.getPrice(),
                Orders.getDiscount(),
                Orders.getImage()
                );

        db.execSQL(query);


    }



    public void delete(orders Orders){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetails(Productid,Productname,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
        Orders.getProductid(),
                Orders.getProductname(),
                Orders.getQuantity(),
                Orders.getPrice(),
                Orders.getDiscount()
                );

        db.execSQL(query);


    }

    public void cleanCart(){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetails");

        db.execSQL(query);


    }
}
