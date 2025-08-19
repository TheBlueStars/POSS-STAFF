package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.adapters.HistoryAdapter;
import com.example.possstaff.data.HistoryRepository;
import com.example.possstaff.models.HistoryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_history);

        RecyclerView rv=findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<HistoryItem> list=new HistoryRepository(this).getAll();
        rv.setAdapter(new HistoryAdapter(list));

        BottomNavigationView bottom = findViewById(R.id.bottomNav);
        bottom.setSelectedItemId(R.id.nav_history);
        bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                finish(); // quay lại màn trước (MenuActivity)
                return true;
            }
            if (id == R.id.nav_history) return true;
            if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }
}
