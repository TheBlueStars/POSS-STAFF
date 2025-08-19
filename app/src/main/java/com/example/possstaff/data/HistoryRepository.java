package com.example.possstaff.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.possstaff.models.HistoryItem;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private final DBHelper helper;
    public HistoryRepository(Context c){ helper=new DBHelper(c); }

    public void add(String zone,int tableNo,String detail,int total){
        helper.getWritableDatabase().execSQL("INSERT INTO history(zone,tableNo,detail,total) VALUES(?,?,?,?)",
                new Object[]{zone,tableNo,detail,total});
    }

    public List<HistoryItem> getAll(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,zone,tableNo,detail,total,createdAt FROM history ORDER BY id DESC",null);
        List<HistoryItem> list=new ArrayList<>();
        while(c.moveToNext()){
            list.add(new HistoryItem(c.getInt(0),c.getString(1),c.getInt(2),c.getString(3),c.getInt(4),c.getString(5)));
        }
        c.close(); return list;
    }
}
