package com.example.uas;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class FavoriteManager {
    private static final String PREF_NAME = "favorite_prefs";
    private static final String KEY_FAVORITES = "favorites";
    private SharedPreferences prefs;

    public FavoriteManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void addFavorite(String id) {
        Set<String> favorites = getFavorites();
        favorites.add(id);
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }

    public void removeFavorite(String id) {
        Set<String> favorites = getFavorites();
        favorites.remove(id);
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }

    public boolean isFavorite(String id) {
        return getFavorites().contains(id);
    }

    public Set<String> getFavorites() {
        return new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<>()));
    }
}