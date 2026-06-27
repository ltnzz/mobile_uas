package com.example.uas;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class EndemikModel implements Serializable {
    @SerializedName("nama")
    private String nama;

    @SerializedName("tipe") // Di JSON kuncinya "tipe", bukan "jenis"
    private String jenis;

    @SerializedName("foto") // Di JSON kuncinya "foto", bukan "gambar"
    private String gambar;

    @SerializedName("asal")
    private String asal;

    @SerializedName("deskripsi")
    private String deskripsi;

    // Constructor Kosong (Wajib untuk Retrofit)
    public EndemikModel() {}

    // Constructor Lengkap (Untuk Room/Favorite)
    public EndemikModel(String nama, String asal, String gambar, String deskripsi, String jenis) {
        this.nama = nama;
        this.asal = asal;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.jenis = jenis;
    }

    // GETTER & SETTER
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }

    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}