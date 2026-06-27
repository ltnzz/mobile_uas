package com.example.uas;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EndemikEntity.class, CacheEndemikEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EndemikDao endemikDao();
    public abstract CacheDao cacheDao();
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "endemik_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}