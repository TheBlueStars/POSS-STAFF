package com.example.possstaff.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.models.MenuItem;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {
    public interface Listener {
        void onAdd(MenuItem m);
        void onRemove(MenuItem m);
    }
    private List<MenuItem> data;
    private final Listener listener;

    public MenuAdapter(List<MenuItem> d, Listener l){ this.data=d; this.listener=l; }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new VH(v);
    }
    @Override public void onBindViewHolder(@NonNull VH h, int i) {
        MenuItem m = data.get(i);
        h.name.setText(m.name);
        h.price.setText(String.format("%,d VNĐ", m.price));
        h.btnAdd.setOnClickListener(v -> listener.onAdd(m));
        h.btnRemove.setOnClickListener(v -> listener.onRemove(m));
    }
    @Override public int getItemCount(){ return data.size(); }
    public void setData(List<MenuItem> d){ this.data=d; notifyDataSetChanged(); }

    static class VH extends RecyclerView.ViewHolder{
        TextView name, price; Button btnAdd, btnRemove;
        VH(View v){ super(v);
            name=v.findViewById(R.id.tvName);
            price=v.findViewById(R.id.tvPrice);
            btnAdd=v.findViewById(R.id.btnAdd);
            btnRemove=v.findViewById(R.id.btnRemove);
        }
    }
}
