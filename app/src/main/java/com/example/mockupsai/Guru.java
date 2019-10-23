package com.example.mockupsai;

public class Guru {
    private String nama, telp;
    private int gambar;

    public Guru() {}

    public Guru(String nama, String telp, int gambar) {
        this.nama = nama;
        this.telp= telp;
        this.gambar = gambar;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
