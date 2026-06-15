package com.example.uas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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

public class TumbuhanFragment extends Fragment {

    private List<EndemikModel> allDataList = new ArrayList<>();
    private List<EndemikModel> searchList = new ArrayList<>();

    private EditText etSearch;
    private RecyclerView rv;
    private EndemikAdapter adapter;
    private static final String TAG = "DEBUG_TUMBUHAN";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gunakan layout yang sama karena strukturnya identik
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        rv = view.findViewById(R.id.rvEndemik);
        etSearch = view.findViewById(R.id.etSearch);
        ImageView btnSearchTrigger = view.findViewById(R.id.btnSearchTrigger);
        ImageView btnGoToLikes = view.findViewById(R.id.btnGoToLikes);

        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Tampilkan/Sembunyikan kolom Search
        btnSearchTrigger.setOnClickListener(v -> {
            if (etSearch.getVisibility() == View.GONE) {
                etSearch.setVisibility(View.VISIBLE);
                etSearch.requestFocus();
            } else {
                etSearch.setVisibility(View.GONE);
                etSearch.setText("");
                filterSearch("");
            }
        });

        // Pindah ke Halaman Favorit
        btnGoToLikes.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoriteFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Fitur Search Real-time
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSearch(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        ambilData();
        return view;
    }

    private void filterSearch(String text) {
        searchList.clear();
        if (text.isEmpty()) {
            searchList.addAll(allDataList);
        } else {
            for (EndemikModel item : allDataList) {
                if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(item);
                }
            }
        }
        adapter = new EndemikAdapter(searchList);
        rv.setAdapter(adapter);
    }

    private void ambilData() {
        ApiClient.getService().getAllEndemik().enqueue(new Callback<List<EndemikModel>>() {
            @Override
            public void onResponse(Call<List<EndemikModel>> call, Response<List<EndemikModel>> response) {
                if (isAdded() && response.isSuccessful() && response.body() != null) {
                    allDataList.clear();

                    for (EndemikModel item : response.body()) {
                        // FILTER: Hanya ambil yang jenisnya "Tumbuhan"
                        if (item.getJenis() != null && item.getJenis().equalsIgnoreCase("Tumbuhan")) {
                            allDataList.add(item);
                        }
                    }

                    searchList.clear();
                    searchList.addAll(allDataList);
                    adapter = new EndemikAdapter(searchList);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<EndemikModel>> call, Throwable t) {
                Log.e(TAG, "Gagal koneksi: " + t.getMessage());
            }
        });
    }
}