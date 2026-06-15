package com.example.uas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    private RecyclerView rv;
    private List<EndemikModel> favList = new ArrayList<>();
    private FavoriteManager favoriteManager;
    // 1. TAMBAHKAN DEKLARASI ADAPTER DI SINI (Biar tidak error 'cannot find symbol')
    private EndemikAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, container, false);

        // Inisialisasi View
        rv = view.findViewById(R.id.rvEndemik);
        ImageView btnBack = view.findViewById(R.id.btnBackFav);

        // Setup RecyclerView
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favoriteManager = new FavoriteManager(getContext());

        // Logika Tombol Back
        btnBack.setOnClickListener(v -> {
            // Menggunakan getParentFragmentManager() karena getFragmentManager() sudah deprecated (jadul)
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        ambilDataFavorit();
        return view;
    }

    private void ambilDataFavorit() {
        ApiClient.getService().getAllEndemik().enqueue(new Callback<List<EndemikModel>>() {
            @Override
            public void onResponse(Call<List<EndemikModel>> call, Response<List<EndemikModel>> response) {
                // Tambahkan pengecekan isAdded() agar tidak crash saat fragment ditutup sebelum data datang
                if (isAdded() && response.isSuccessful() && response.body() != null) {
                    favList.clear();
                    for (EndemikModel item : response.body()) {
                        if (favoriteManager.isFavorite(item.getNama())) {
                            favList.add(item);
                        }
                    }

                    // 2. CEK JIKA KOSONG (Biar tidak cuma putih doang tapi ada info ke user)
                    if (favList.isEmpty()) {
                        Toast.makeText(getContext(), "Belum ada hewan favorit", Toast.LENGTH_SHORT).show();
                    }

                    // 3. SET ADAPTER (Sudah aman karena variabel adapter sudah dideklarasikan di atas)
                    adapter = new EndemikAdapter(favList);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<EndemikModel>> call, Throwable t) {
                if (isAdded()) {
                    Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}