package com.example.possstaff.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.possstaff.R;
import com.example.possstaff.models.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {

    private final List<HistoryItem> data;

    public HistoryAdapter(List<HistoryItem> d) {
        this.data = (d == null) ? new ArrayList<>() : d;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        HistoryItem it = data.get(position);

        // "Khu %1$s - Bàn %2$d"
        h.title.setText(h.itemView.getContext()
                .getString(R.string.history_title_format, it.zone, it.tableNo));

        h.detail.setText(it.detail);

        // "Tổng: %1$,d VNĐ"
        h.total.setText(h.itemView.getContext()
                .getString(R.string.history_total_format, it.total));

        h.time.setText(it.createdAt);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HistoryItem> newData) {
        data.clear();
        if (newData != null) data.addAll(newData);
        notifyDataSetChanged();
    }

    // PHẢI public static để tránh cảnh báo visibility
    public static class VH extends RecyclerView.ViewHolder {
        final TextView title, detail, total, time;

        public VH(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.tvTitle);
            detail = v.findViewById(R.id.tvDetail);
            total  = v.findViewById(R.id.tvTotal);
            time   = v.findViewById(R.id.tvTime);
        }
    }
}
