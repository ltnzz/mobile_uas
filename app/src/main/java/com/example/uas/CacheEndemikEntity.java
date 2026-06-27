package com.example.uas;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cache_table")
public class CacheEndemikEntity {
    @PrimaryKey
    @NonNull
    public String nama;
    public String asal;
    public String gambar;
    public String deskripsi;
    public String jenis;

    public CacheEndemikEntity(@NonNull String nama, String asal, String gambar, String deskripsi, String jenis) {
        this.nama = nama;
        this.asal = asal;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.jenis = jenis;
    }
}
