package com.example.uas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewanFragment extends Fragment {

    private List<EndemikModel> allDataList = new ArrayList<>();
    private List<EndemikModel> searchList = new ArrayList<>();

    private EditText etSearchNama;
    private Spinner spinnerWilayah;
    private LinearLayout layoutSearch;
    private RecyclerView rv;
    private EndemikAdapter adapter;
    private static final String TAG = "DEBUG_UAS";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        rv = view.findViewById(R.id.rvEndemik);
        etSearchNama = view.findViewById(R.id.etSearch);
        spinnerWilayah = view.findViewById(R.id.spinnerWilayah);
        layoutSearch = view.findViewById(R.id.layoutSearch);

        // 1. Inisialisasi Tombol di Header
        ImageView btnGoToLikes = view.findViewById(R.id.btnGoToLikes);
        ImageView btnGoToProfile = view.findViewById(R.id.btnGoToProfile);
        ImageView btnToggleDark = view.findViewById(R.id.btnToggleDark);

        if (layoutSearch != null) {
            layoutSearch.setVisibility(View.VISIBLE);
        }

        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new EndemikAdapter(searchList);
        rv.setAdapter(adapter);

        // 2. Klik Ikon Favorit
        btnGoToLikes.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoriteFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 3. KLIK IKON PROFIL
        if (btnGoToProfile != null) {
            btnGoToProfile.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // 4. Toggle Dark Mode
        updateDarkModeIcon(btnToggleDark);
        btnToggleDark.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("theme", Context.MODE_PRIVATE);
            boolean isDark = prefs.getBoolean("dark_mode", false);
            prefs.edit().putBoolean("dark_mode", !isDark).apply();
            AppCompatDelegate.setDefaultNightMode(isDark ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
            getActivity().recreate();
        });

        // Listener untuk Input Nama (Ketik)
        etSearchNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String wilayah = spinnerWilayah.getSelectedItem() != null ? spinnerWilayah.getSelectedItem().toString() : "Semua Wilayah";
                filterGanda(s.toString(), wilayah);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Listener untuk Dropdown Wilayah (Pilih)
        spinnerWilayah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterGanda(etSearchNama.getText().toString(), parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ambilData();
        return view;
    }

    private void ambilData() {
        ApiClient.getService().getAllEndemik().enqueue(new Callback<List<EndemikModel>>() {
            @Override
            public void onResponse(Call<List<EndemikModel>> call, Response<List<EndemikModel>> response) {
                if (isAdded() && response.isSuccessful() && response.body() != null) {
                    allDataList.clear();

                    Set<String> wilayahSet = new HashSet<>();

                    for (EndemikModel item : response.body()) {
                        // Pastikan di Model kamu "tipe" sudah di-map ke "jenis" via @SerializedName
                        if (item.getJenis() != null && item.getJenis().equalsIgnoreCase("Hewan")) {
                            allDataList.add(item);
                            if (item.getAsal() != null && !item.getAsal().isEmpty()) {
                                wilayahSet.add(item.getAsal());
                            }
                        }
                    }

                    List<String> listWilayah = new ArrayList<>(wilayahSet);
                    Collections.sort(listWilayah);
                    listWilayah.add(0, "Semua Wilayah");

                    setupSpinner(listWilayah);

                    searchList.clear();
                    searchList.addAll(allDataList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EndemikModel>> call, Throwable t) {
                Log.e(TAG, "Gagal koneksi: " + t.getMessage());
            }
        });
    }

    private void setupSpinner(List<String> listWilayah) {
        if (getContext() == null) return;
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, listWilayah);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWilayah.setAdapter(spinnerAdapter);
    }

    private void filterGanda(String queryNama, String queryWilayah) {
        searchList.clear();
        String qNama = queryNama.toLowerCase().trim();

        for (EndemikModel item : allDataList) {
            String nama = (item.getNama() != null) ? item.getNama().toLowerCase() : "";
            String asal = (item.getAsal() != null) ? item.getAsal() : "";

            boolean matchNama = qNama.isEmpty() || nama.contains(qNama);
            boolean matchWilayah = queryWilayah.equals("Semua Wilayah") || asal.equalsIgnoreCase(queryWilayah);

            if (matchNama && matchWilayah) {
                searchList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void updateDarkModeIcon(ImageView btn) {
        SharedPreferences prefs = requireContext().getSharedPreferences("theme", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("dark_mode", false);
        btn.setImageResource(isDark ? R.drawable.ic_sun : R.drawable.ic_moon);
    }
}