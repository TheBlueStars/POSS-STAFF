package com.example.possstaff.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.possstaff.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final DBHelper helper;
    public OrderRepository(Context c){ helper=new DBHelper(c); }

    public void addItem(String zone,int tableNo,int menuId,String name,int price,int deltaQty){
        SQLiteDatabase db = helper.getWritableDatabase();
        // upsert
        db.execSQL("INSERT OR IGNORE INTO orders(zone,tableNo,menuId,name,price,qty) VALUES(?,?,?,?,?,0)",
                new Object[]{zone,tableNo,menuId,name,price});
        db.execSQL("UPDATE orders SET qty = MAX(0, qty + ?) WHERE zone=? AND tableNo=? AND menuId=?",
                new Object[]{deltaQty, zone, tableNo, menuId});
        db.execSQL("DELETE FROM orders WHERE qty=0 AND zone=? AND tableNo=? AND menuId=?",
                new Object[]{zone, tableNo, menuId});
    }

    public List<OrderItem> getItems(String zone,int tableNo){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT menuId,name,price,qty FROM orders WHERE zone=? AND tableNo=? ORDER BY name",
                new String[]{zone, String.valueOf(tableNo)});
        List<OrderItem> list = new ArrayList<>();
        while(c.moveToNext()){
            list.add(new OrderItem(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3)));
        }
        c.close(); return list;
    }

    public int total(String zone,int tableNo){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(price*qty) FROM orders WHERE zone=? AND tableNo=?",
                new String[]{zone, String.valueOf(tableNo)});
        int t=0; if(c.moveToFirst()) t = c.isNull(0)?0:c.getInt(0); c.close(); return t;
    }

    public void clearTable(String zone,int tableNo){
        helper.getWritableDatabase().execSQL("DELETE FROM orders WHERE zone=? AND tableNo=?",
                new Object[]{zone, tableNo});
    }

    public void transferTable(String zone,int from,int to){
        helper.getWritableDatabase().execSQL(
                "UPDATE orders SET tableNo=? WHERE zone=? AND tableNo=?",
                new Object[]{to, zone, from});
    }
}
