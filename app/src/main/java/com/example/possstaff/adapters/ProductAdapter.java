package com.example.possstaff.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.possstaff.R;
import com.example.possstaff.models.MenuItem;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {
    public interface Listener { void onAdd(MenuItem m); void onClick(MenuItem m); }
    private List<MenuItem> data; private final Listener l;
    public ProductAdapter(List<MenuItem> d, Listener l){ data=d; this.l=l; }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int v) {
        View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_product, p, false);
        return new VH(view);
    }
    @Override public void onBindViewHolder(@NonNull VH h,int i){
        MenuItem m=data.get(i);
        h.name.setText(m.name);
        h.price.setText(String.format("%,d VNĐ", m.price));
        // Ảnh demo: set icon mặc định; có thể map theo tên món.
        h.img.setImageResource(android.R.drawable.ic_menu_gallery);
        h.itemView.setOnClickListener(v-> l.onClick(m));
        h.btnAdd.setOnClickListener(v-> l.onAdd(m));
    }
    @Override public int getItemCount(){ return data.size(); }
    public void setData(List<MenuItem> d){ data=d; notifyDataSetChanged(); }

    static class VH extends RecyclerView.ViewHolder{
        ImageView img; TextView name, price; Button btnAdd;
        VH(View v){ super(v);
            img=v.findViewById(R.id.img); name=v.findViewById(R.id.tvName);
            price=v.findViewById(R.id.tvPrice); btnAdd=v.findViewById(R.id.btnAdd);
        }
    }
}
