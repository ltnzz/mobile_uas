package com.example.uas;

import com.google.gson.annotations.SerializedName;

public class EndemikModel {
    @SerializedName("nama")
    private String nama;

    @SerializedName("deskripsi")
    private String deskripsi;

    // INI YANG PALING PENTING:
    // Di JSON namanya "tipe", di Java kita sebut "jenis"
    @SerializedName("tipe")
    private String jenis;

    // Di JSON namanya "foto", di Java kita sebut "gambarUrl"
    @SerializedName("foto")
    private String gambarUrl;

    // Getter untuk Jenis/Tipe
    public String getJenis() {
        return jenis;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambarUrl() {
        return gambarUrl;
    }
}