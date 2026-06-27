package com.example.uas;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EndemikEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EndemikDao endemikDao();
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "endemik_db")
                    .allowMainThreadQueries() // Supaya simpel buat UAS
                    .build();
        }
        return instance;
    }
}