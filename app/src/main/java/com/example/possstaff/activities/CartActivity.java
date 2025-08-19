package com.example.possstaff.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.adapters.OrderAdapter;
import com.example.possstaff.data.HistoryRepository;
import com.example.possstaff.data.OrderRepository;
import com.example.possstaff.models.OrderItem;
import com.example.possstaff.models.Role;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private String zone;
    private int tableNo;
    private Role role;

    private TextView tvTotal;
    private RecyclerView rv;
    private OrderAdapter adapter;
    private OrderRepository repo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        zone    = getIntent().getStringExtra("zone");
        tableNo = getIntent().getIntExtra("tableNo", 0);
        role    = Role.valueOf(getIntent().getStringExtra("role"));

        tvTotal = findViewById(R.id.tvTotal);
        rv      = findViewById(R.id.rvOrder);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        repo = new OrderRepository(this);

        // RecyclerView + Adapter
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<OrderItem> data = repo.getItems(zone, tableNo);

        adapter = new OrderAdapter(data, new OrderAdapter.Listener() {
            @Override public void onPlus(OrderItem it) {
                repo.addItem(zone, tableNo, it.id, it.name, it.price, +1);
                refresh();
            }
            @Override public void onMinus(OrderItem it) {
                repo.addItem(zone, tableNo, it.id, it.name, it.price, -1);
                refresh();
            }
            @Override public void onDelete(OrderItem it) {
                repo.addItem(zone, tableNo, it.id, it.name, it.price, -it.qty);
                refresh();
            }
        });
        rv.setAdapter(adapter);

        // tổng lần đầu
        updateTotal();

        // Gửi bếp + lưu lịch sử
        btnCheckout.setOnClickListener(v -> {
            int total = repo.total(zone, tableNo);
            if (total <= 0) {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }
            // tạo detail ngắn gọn
            StringBuilder sb = new StringBuilder();
            for (OrderItem o : repo.getItems(zone, tableNo)) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(o.name).append(" x").append(o.qty);
            }
            new HistoryRepository(this).add(zone, tableNo, sb.toString(), total);

            repo.clearTable(zone, tableNo);
            refresh();

            Toast.makeText(this, "Đã gửi bếp & lưu lịch sử!", Toast.LENGTH_SHORT).show();
            finish(); // quay lại menu
        });
    }

    /** Refresh lại danh sách và tổng tiền */
    private void refresh() {
        adapter.setData(repo.getItems(zone, tableNo));
        updateTotal();
    }

    private void updateTotal() {
        tvTotal.setText(getString(R.string.history_total_format, repo.total(zone, tableNo)));
    }
}
