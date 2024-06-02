package com.example.kasir;

public class Obat {
    private String nameObat;
    private double harga;
    private String jenisObat;
    private int stock;
    private String keluhan;
    private int jumlah;

    public Obat(String nameObat, double harga, String jenisObat, int stock, String keluhan) {
        this.nameObat = nameObat;
        this.harga = harga;
        this.jenisObat = jenisObat;
        this.stock = stock;
        this.keluhan = keluhan;
    }

    public Obat(String nameObat, double harga, String jenisObat, int stock, String keluhan, int jumlah) {
        this(nameObat, harga, jenisObat, stock, keluhan);
        this.jumlah = jumlah;
    }

    public String getNameObat() {
        return nameObat;
    }

    public double getHarga() {
        return harga;
    }

    public String getJenisObat() {
        return jenisObat;
    }

    public int getStock() {
        return stock;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int setJumlah(int jumlah) {
        this.jumlah = jumlah;
        return jumlah;
    }

    @Override
    public String toString() {
        return "Obat{" +
                "nameObat='" + nameObat + '\'' +
                ", harga=" + harga +
                ", jenisObat='" + jenisObat + '\'' +
                ", dosis=" + stock +
                ", keluhan='" + keluhan + '\'' +
                ", jumlah=" + jumlah +
                '}';
    }
}
