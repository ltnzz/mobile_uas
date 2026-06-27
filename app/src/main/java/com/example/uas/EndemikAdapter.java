package com.example.uas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {
    // Pastikan namanya konsisten, kita gunakan "list"
    private List<EndemikModel> list;

    public EndemikAdapter(List<EndemikModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // PERBAIKAN: Ganti listData menjadi list
        EndemikModel data = list.get(position);

        holder.tvNama.setText(data.getNama());

        // TAMPILKAN WILAYAH (REGION)
        if (data.getAsal() != null) {
            holder.tvAsal.setText("Wilayah: " + data.getAsal());
        } else {
            holder.tvAsal.setText("Wilayah: Tidak diketahui");
        }

        // Load Gambar (Pastikan holder.imgItem sudah didefinisikan di ViewHolder)
        Glide.with(holder.itemView.getContext())
                .load(data.getGambar())
                .into(holder.imgItem);

        // Intent ke Detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("DATA_ENDEMIK", data);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvAsal; // Tambahkan tvAsal
        ImageView imgItem; // Samakan nama dengan yang dipakai di Glide

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAsal = itemView.findViewById(R.id.tvAsal); // Inisialisasi tvAsal
            imgItem = itemView.findViewById(R.id.imgItem); // Inisialisasi imgItem
        }
    }
}