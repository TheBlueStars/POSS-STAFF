package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.adapters.ProductAdapter;
import com.example.possstaff.data.MenuRepository;
import com.example.possstaff.data.OrderRepository;
import com.example.possstaff.models.MenuItem;
import com.example.possstaff.models.Role;
import com.example.possstaff.utils.RolePermissions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements ProductAdapter.Listener {
    String zone; int tableNo; Role role;
    MenuRepository menuRepo; OrderRepository orderRepo;
    ProductAdapter adapter; List<MenuItem> data = new ArrayList<>();

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_menu);

        zone=getIntent().getStringExtra("zone");
        tableNo=getIntent().getIntExtra("tableNo",0);
        role= Role.valueOf(getIntent().getStringExtra("role"));

        ((TextView)findViewById(R.id.tvTableTitle)).setText("Khu "+zone+" - Bàn "+tableNo);

        menuRepo=new MenuRepository(this);
        orderRepo=new OrderRepository(this);

        RecyclerView rv=findViewById(R.id.rvMenu);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter=new ProductAdapter(data, this);
        rv.setAdapter(adapter);
        load("");

        EditText search=findViewById(R.id.edtSearch);
        search.addTextChangedListener(new TextWatcher(){
            @Override public void beforeTextChanged(CharSequence s,int st,int c,int a){}
            @Override public void onTextChanged(CharSequence s,int st,int b,int c){ load(s.toString()); }
            @Override public void afterTextChanged(Editable s){}
        });

        // Bottom nav
        BottomNavigationView bottom = findViewById(R.id.bottomNav);
        bottom.setSelectedItemId(R.id.nav_home);
        bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            }
            if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    private void load(String keyword){
        data = menuRepo.getAll(keyword);
        adapter.setData(data);
    }

    @Override public void onAdd(MenuItem m){
        if(!RolePermissions.canOrder(role)){
            Toast.makeText(this,"Không có quyền order",Toast.LENGTH_SHORT).show(); return;
        }
        orderRepo.addItem(zone, tableNo, m.id, m.name, m.price, +1);
        Toast.makeText(this,"+1 "+m.name,Toast.LENGTH_SHORT).show();
    }
    @Override public void onClick(MenuItem m){ /* mở chi tiết nếu cần */ }
}
