package com.example.uas;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // <--- Add this line
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    boolean isLiked = false; // Status like sederhana

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView img = findViewById(R.id.imgDetail);
        TextView nama = findViewById(R.id.tvNamaDetail);
        TextView desc = findViewById(R.id.tvDeskripsiDetail);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnLike = findViewById(R.id.btnLikeDetail);
        TextView tvTitleBar = findViewById(R.id.tvTitleBar);

        String nameStr = getIntent().getStringExtra("NAMA");
        String imgStr = getIntent().getStringExtra("GAMBAR");
        String descStr = getIntent().getStringExtra("DESKRIPSI");

        nama.setText(nameStr);
        tvTitleBar.setText(nameStr); // Judul tengah sesuai nama hewan
        desc.setText(descStr);
        Glide.with(this).load(imgStr).into(img);

        // Tombol Back
        btnBack.setOnClickListener(v -> finish());

        // Tombol Like
        // Di dalam onCreate DetailActivity.java
        FavoriteManager favoriteManager = new FavoriteManager(this);
        String idHewan = getIntent().getStringExtra("NAMA"); // Gunakan Nama sebagai ID unik sementara

        if (favoriteManager.isFavorite(idHewan)) {
            btnLike.setImageResource(R.drawable.ic_love_on);
        } else {
            btnLike.setImageResource(R.drawable.ic_love_off);
        }

        btnLike.setOnClickListener(v -> {
            if (favoriteManager.isFavorite(idHewan)) {
                favoriteManager.removeFavorite(idHewan);
                btnLike.setImageResource(R.drawable.ic_love_off);
                Toast.makeText(this, "Dihapus dari Favorit", Toast.LENGTH_SHORT).show();
            } else {
                favoriteManager.addFavorite(idHewan);
                btnLike.setImageResource(R.drawable.ic_love_on);
                Toast.makeText(this, "Disimpan ke Favorit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}