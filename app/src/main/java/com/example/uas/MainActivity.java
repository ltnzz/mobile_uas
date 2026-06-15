package com.example.uas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Simpan fragment saat ini agar bisa dicek
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        // Default fragment (Saat pertama buka)
        if (savedInstanceState == null) {
            currentFragment = new HewanFragment();
            loadFragment(currentFragment);
        }

        nav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.menu_hewan) {
                selectedFragment = new HewanFragment();
            } else if (itemId == R.id.menu_tumbuhan) {
                selectedFragment = new TumbuhanFragment();
            }

            // CEK: Jika user klik menu yang sama dengan yang sedang terbuka, jangan load ulang
            if (selectedFragment != null && (currentFragment == null || !selectedFragment.getClass().equals(currentFragment.getClass()))) {
                currentFragment = selectedFragment;
                return loadFragment(selectedFragment);
            }

            return false;
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    // Jangan pakai addToBackStack di BottomNav agar tidak menumpuk saat di-back
                    .commit();
            return true;
        }
        return false;
    }
}