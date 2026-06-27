package com.example.uas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView rv;
    private EndemikAdapter adapter;
    private List<EndemikModel> listFavorit = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        rv = v.findViewById(R.id.rvFavorit);

        // Setup RecyclerView
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new EndemikAdapter(listFavorit);
        rv.setAdapter(adapter);

        return v;
    }

    // Gunakan onResume agar setiap kali user kembali ke halaman ini, data di-refresh
    @Override
    public void onResume() {
        super.onResume();
        ambilDataFavorit();
    }

    private void ambilDataFavorit() {
        if (getContext() == null) return;

        AppDatabase db = AppDatabase.getInstance(getContext());
        List<EndemikEntity> entities = db.endemikDao().getAllFavorit();

        listFavorit.clear();
        for (EndemikEntity e : entities) {
            listFavorit.add(new EndemikModel(e.nama, e.asal, e.gambar, e.deskripsi, e.jenis));
        }

        // Beri tahu adapter kalau data berubah
        adapter.notifyDataSetChanged();

        // LOGIKA DEBUG: Cek apakah data kosong atau tidak
        if (listFavorit.isEmpty()) {
            Toast.makeText(getContext(), "Belum ada data favorit", Toast.LENGTH_SHORT).show();
        }
    }
}