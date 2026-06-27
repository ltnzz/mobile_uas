package com.example.uas; // Sesuaikan dengan package kamu

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // WAJIB ADA
import android.os.Looper;  // Tambahan agar lebih aman di versi Android baru
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Menghilangkan Action Bar di Splash Screen agar full screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Pindah ke MainActivity setelah 3 detik
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Agar user tidak bisa balik lagi ke splash saat klik back
        }, 3000);
    }
}