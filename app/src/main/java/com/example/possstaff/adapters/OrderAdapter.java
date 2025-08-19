package com.example.possstaff.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.models.OrderItem;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {

    public interface Listener {
        void onPlus(OrderItem it);
        void onMinus(OrderItem it);
        void onDelete(OrderItem it);
    }

    private List<OrderItem> data;
    private final Listener l;

    public OrderAdapter(List<OrderItem> d, Listener l) {
        this.data = d;
        this.l = l;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        OrderItem it = data.get(i);

        // TRỌNG TÂM: truyền it.price (int) trực tiếp cho %2$,d
        String namePrice = h.itemView.getContext()
                .getString(R.string.order_item_name_price, it.name, it.price);
        h.name.setText(namePrice);

        h.qty.setText(h.itemView.getContext()
                .getString(R.string.order_item_qty, it.qty));

        h.btnPlus.setOnClickListener(v -> l.onPlus(it));
        h.btnMinus.setOnClickListener(v -> l.onMinus(it));
        h.btnDelete.setOnClickListener(v -> l.onDelete(it));
    }

    @Override public int getItemCount() { return data == null ? 0 : data.size(); }

    public void setData(List<OrderItem> d) {
        this.data = d;
        notifyDataSetChanged();
    }

    // phải public static
    public static class VH extends RecyclerView.ViewHolder {
        final TextView name, qty;
        final Button btnPlus, btnMinus, btnDelete;
        public VH(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.tvItemName);
            qty = v.findViewById(R.id.tvItemQty);
            btnPlus  = v.findViewById(R.id.btnPlus);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnDelete= v.findViewById(R.id.btnDelete);
        }
    }
}
