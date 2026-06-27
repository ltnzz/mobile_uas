package com.example.uas;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EndemikDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EndemikEntity item);

    @Delete
    void delete(EndemikEntity item);

    @Query("SELECT * FROM favorit_table")
    List<EndemikEntity> getAllFavorit();

    @Query("SELECT EXISTS (SELECT 1 FROM favorit_table WHERE nama = :namaItem)")
    boolean isFavorit(String namaItem);
}