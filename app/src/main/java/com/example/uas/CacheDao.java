package com.example.uas;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CacheEndemikEntity> items);

    @Query("SELECT * FROM cache_table")
    List<CacheEndemikEntity> getAll();

    @Query("DELETE FROM cache_table")
    void deleteAll();
}
