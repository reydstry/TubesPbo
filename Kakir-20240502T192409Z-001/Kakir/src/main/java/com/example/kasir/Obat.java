package com.example.kasir;

public class Obat {
    private String nameObat;
    private double harga;
    private String jenisObat;
    private int dosis;
    private String keluhan;
    private int jumlah; 

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Obat(String nameObat, double harga, String jenisObat, int dosis, String keluhan) {
        this.nameObat = nameObat;
        this.harga = harga;
        this.jenisObat = jenisObat;
        this.dosis = dosis;
        this.keluhan = keluhan;
    }

    public String getNameObat() {
        return nameObat;
    }

    public void setNameObat(String nameObat) {
        this.nameObat = nameObat;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getJenisObat() {
        return jenisObat;
    }

    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getKeluhan() {return keluhan;}

    public void setKeluhan(String keluhan) {this.keluhan = keluhan;}
    @Override
    public String toString() {
        return "Obat{" +
                "nameObat='" + nameObat + '\'' +
                ", harga=" + harga +
                ", jenisObat='" + jenisObat + '\'' +
                ", dosis=" + dosis +
                ", keluhan='" + keluhan + '\'' +
                '}';
    }
}
