package com.example.possstaff.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.adapters.MenuAdapter;
import com.example.possstaff.data.MenuRepository;
import com.example.possstaff.models.MenuItem;

import java.util.List;

public class AdminManageMenuActivity extends AppCompatActivity implements MenuAdapter.Listener {

    EditText edtName, edtPrice;
    RecyclerView rv;
    MenuAdapter adapter;
    MenuRepository repo;
    MenuItem selected = null;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_admin_manage_menu);

        repo = new MenuRepository(this);

        edtName = findViewById(R.id.edName);
        edtPrice = findViewById(R.id.edPrice);

        rv = findViewById(R.id.rvAdminMenu);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(repo.getAll(""), this);
        rv.setAdapter(adapter);

        findViewById(R.id.btnAddMenu).setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtName.getText()) || TextUtils.isEmpty(edtPrice.getText())) return;
            repo.add(edtName.getText().toString(), Integer.parseInt(edtPrice.getText().toString()));
            clearForm();
            load("");
            Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnUpdateMenu).setOnClickListener(v -> {
            if (selected == null) return;
            repo.update(selected.id, edtName.getText().toString(), Integer.parseInt(edtPrice.getText().toString()));
            clearForm();
            load("");
            Toast.makeText(this, "Đã sửa", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnDeleteMenu).setOnClickListener(v -> {
            if (selected == null) return;
            repo.delete(selected.id);
            clearForm();
            load("");
            Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
        });
    }

    private void clearForm() {
        selected = null;
        edtName.setText("");
        edtPrice.setText("");
    }

    private void load(String kw) {
        List<MenuItem> list = repo.getAll(kw);
        adapter.setData(list);
    }
    public void onClick(MenuItem item) {
        selected = item;
        edtName.setText(item.name);
        edtPrice.setText(String.valueOf(item.price));
    }

    // Không dùng trong màn Admin
    @Override public void onAdd(MenuItem item) { /* no-op */ }

    // Bổ sung method còn thiếu theo interface -> xóa trực tiếp từ list
    @Override
    public void onRemove(MenuItem item) {
        if (item == null) return;
        repo.delete(item.id);
        if (selected != null && selected.id == item.id) {
            clearForm();
        }
        load("");
        Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
    }
}
