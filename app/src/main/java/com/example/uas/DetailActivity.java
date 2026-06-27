package com.example.uas;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private AppDatabase db; // Ganti FavoriteManager dengan AppDatabase
    private boolean isFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. Inisialisasi Database ROOM
        db = AppDatabase.getInstance(this);

        // 2. Ambil Data dari Intent
        EndemikModel data = (EndemikModel) getIntent().getSerializableExtra("DATA_ENDEMIK");

        // 3. Inisialisasi View
        ImageView img = findViewById(R.id.imgDetail);
        TextView nama = findViewById(R.id.tvNamaDetail);
        TextView desc = findViewById(R.id.tvDescDetail);
        FloatingActionButton fab = findViewById(R.id.fabFavorite);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // 4. Setup Toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        if (data != null) {
            nama.setText(data.getNama());
            desc.setText(data.getDeskripsi());

            Glide.with(this)
                    .load(data.getGambar())
                    .placeholder(android.R.drawable.progress_horizontal)
                    .into(img);

            // 5. Cek Status Favorit dari ROOM Database
            isFav = db.endemikDao().isFavorit(data.getNama());
            updateFabIcon(fab);

            // 6. Klik FAB untuk Simpan/Hapus dari ROOM
            fab.setOnClickListener(v -> {
                // Buat object entity untuk dikirim ke database
                EndemikEntity entity = new EndemikEntity(
                        data.getNama(),
                        data.getAsal(),
                        data.getGambar(),
                        data.getDeskripsi(),
                        data.getJenis()
                );

                if (isFav) {
                    db.endemikDao().delete(entity); // Hapus dari ROOM
                    Toast.makeText(this, "Dihapus dari Koleksi Favorit", Toast.LENGTH_SHORT).show();
                } else {
                    db.endemikDao().insert(entity); // Simpan ke ROOM
                    Toast.makeText(this, "Berhasil Menambah Koleksi Favorit", Toast.LENGTH_SHORT).show();
                }

                isFav = !isFav; // Toggle status
                updateFabIcon(fab); // Update tampilan icon
            });

        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateFabIcon(FloatingActionButton fab) {
        if (isFav) {
            // Gunakan icon love on (warna penuh)
            fab.setImageResource(R.drawable.ic_love_on);
        } else {
            // Gunakan icon love off (garis luar saja)
            fab.setImageResource(R.drawable.ic_love_off);
        }
    }
}