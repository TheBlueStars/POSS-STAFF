package com.example.possstaff.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="possstaff.db";
    public DBHelper(Context ctx){ super(ctx, DB_NAME, null, 1); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE menu(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER)");
        db.execSQL("CREATE TABLE orders(zone TEXT, tableNo INTEGER, menuId INTEGER, name TEXT, price INTEGER, qty INTEGER, PRIMARY KEY(zone, tableNo, menuId))");
        db.execSQL("CREATE TABLE history(id INTEGER PRIMARY KEY AUTOINCREMENT, zone TEXT, tableNo INTEGER, detail TEXT, total INTEGER, createdAt DATETIME DEFAULT CURRENT_TIMESTAMP)");
        seed(db);
    }
    private void seed(SQLiteDatabase db){
        // Món ăn + bia theo yêu cầu
        String[] names = {
                "Cơm chiên dưa bò","Cơm chiên hải sản","Salad dầu dấm","Đậu hũ chiên giòn","Đậu hũ chiên sả ớt",
                "Gà chiên nước mắm","Gà nướng","Bò mỹ xiên que nướng","Rau muống xào tỏi","Cải thìa xào dầu hào",
                "Thịt heo xiên que","Ken bạc","Ken xanh","Tiger nâu","Tiger bạc","Sài Gòn Special","Blanc 1664"
        };
        int[] prices = {80000,90000,70000,50000,60000,120000,150000,100000,70000,80000,100000,25000,25000,27000,27000,26000,35000};
        for(int i=0;i<names.length;i++){
            db.execSQL("INSERT INTO menu(name,price) VALUES(?,?)", new Object[]{names[i], prices[i]});
        }
    }
    @Override public void onUpgrade(SQLiteDatabase db,int oldV,int newV){}
}
