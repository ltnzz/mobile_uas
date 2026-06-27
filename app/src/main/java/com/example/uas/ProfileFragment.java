package com.example.uas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Cari tombol back berdasarkan ID
        ImageView btnBack = view.findViewById(R.id.btnBackProfile);

        // Aksi pas tombol back diklik
        btnBack.setOnClickListener(v -> {
            // Perintah untuk balik ke fragment sebelumnya (Hewan/Tumbuhan)
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}