package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.possstaff.R;
import com.example.possstaff.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_settings);

        // demo switch
        Switch sw = findViewById(R.id.swDark);
        sw.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this, isChecked ? "Bật tối (demo)" : "Tắt tối (demo)", Toast.LENGTH_SHORT).show());

        // logout
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            new SessionManager(this).logout();
            Toast.makeText(this,"Đã đăng xuất",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });

        // bottom nav
        BottomNavigationView bottom = findViewById(R.id.bottomNav);
        bottom.setSelectedItemId(R.id.nav_settings);
        bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) { finish(); return true; }
            if (id == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            }
            if (id == R.id.nav_settings) return true;
            return false;
        });
    }
}
