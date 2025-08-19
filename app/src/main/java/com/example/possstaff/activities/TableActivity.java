package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.possstaff.R;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {
    String zone; int tables; String role;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_table);

        zone   = getIntent().getStringExtra("zone");
        tables = getIntent().getIntExtra("tables", 0);
        role   = getIntent().getStringExtra("role");

        TextView tv = findViewById(R.id.tvZoneTitle);
        tv.setText(getString(R.string.zone_title, zone)); // “Khu A - Chọn bàn”

        GridView grid = findViewById(R.id.gridTables);
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= tables; i++) ids.add("B" + i);

        // Dùng layout item_table + id text1 (đã tạo ở trên)
        ArrayAdapter<String> ad = new ArrayAdapter<>(
                this,
                R.layout.item_table,
                R.id.text1,
                ids
        );
        grid.setAdapter(ad);

        grid.setOnItemClickListener((parent, view, position, id) -> {
            int tableNo = position + 1;
            Intent i = new Intent(this, MenuActivity.class);
            i.putExtra("zone", zone);
            i.putExtra("tableNo", tableNo);
            i.putExtra("role", role);
            startActivity(i);
        });
    }
}
