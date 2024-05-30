package com.example.Dao;

import com.example.kasir.Obat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoObat extends Dao<Obat> {
    public DaoObat(Connection connection) {
        super(connection);
    }

    public ArrayList<Obat> searchObat(String keyword) {
        ArrayList<Obat> obatList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM dtaobat WHERE namaObat LIKE ? OR jenisObat LIKE ? OR Keluhan LIKE ?");
            String searchKeyword = "%" + keyword + "%";
            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String namaObat = result.getString("namaObat");
                String jenisObat = result.getString("jenisObat");
                int stock = result.getInt("Stock");
                double harga = result.getDouble("HargaObat");
                String keluhan = result.getString("Keluhan");
                Obat obat = new Obat(namaObat, harga, jenisObat, stock, keluhan);
                obatList.add(obat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obatList;
    }

    @Override
    public ArrayList<Obat> getAll() {
        ArrayList<Obat> obatList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dtaobat");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String namaObat = result.getString("namaObat");
                String jenisObat = result.getString("jenisObat");
                int stock = result.getInt("Stock");
                double harga = result.getDouble("HargaObat");
                String keluhan = result.getString("Keluhan");
                Obat obat = new Obat(namaObat, harga, jenisObat, stock, keluhan);
                obatList.add(obat);
            }
            return obatList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Obat getById(int id) {
        return null;
    }
}
