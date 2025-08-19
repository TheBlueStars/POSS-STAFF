package com.example.possstaff.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.possstaff.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    private final DBHelper helper;
    public MenuRepository(Context c){ helper=new DBHelper(c); }

    public List<MenuItem> getAll(String keyword){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT id,name,price FROM menu WHERE name LIKE ? ORDER BY name";
        Cursor c = db.rawQuery(sql, new String[]{"%"+(keyword==null?"":keyword)+"%"});
        while(c.moveToNext()){
            list.add(new MenuItem(c.getInt(0), c.getString(1), c.getInt(2)));
        }
        c.close(); return list;
    }
    public void add(String name,int price){
        helper.getWritableDatabase().execSQL("INSERT INTO menu(name,price) VALUES(?,?)", new Object[]{name,price});
    }
    public void update(int id,String name,int price){
        helper.getWritableDatabase().execSQL("UPDATE menu SET name=?, price=? WHERE id=?", new Object[]{name,price,id});
    }
    public void delete(int id){
        helper.getWritableDatabase().execSQL("DELETE FROM menu WHERE id=?", new Object[]{id});
    }
}
