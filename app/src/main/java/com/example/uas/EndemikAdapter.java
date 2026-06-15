package com.example.uas;

import android.content.Intent; // Added missing import
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

// 1. Ensure the generic type matches: <EndemikAdapter.ViewHolder>
public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {
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
    // 2. Changed EndemikViewHolder to ViewHolder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 3. Changed listEndemik to list (to match your variable name above)
        EndemikModel data = list.get(position);

        holder.tvNama.setText(data.getNama());

        Glide.with(holder.itemView.getContext())
                .load(data.getGambarUrl())
                .into(holder.img); // 4. Changed imgItem to img (to match ViewHolder class)

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("NAMA", data.getNama());
            intent.putExtra("GAMBAR", data.getGambarUrl());
            intent.putExtra("DESKRIPSI", data.getDeskripsi()); // Deskripsi dikirim ke sini
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama; // tvDesc dihapus
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            img = itemView.findViewById(R.id.imgItem);
        }
    }
}