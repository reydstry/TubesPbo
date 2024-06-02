package com.example.Dao;

import com.example.kasir.Obat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoCart extends Dao<Obat>{
    public DaoCart(Connection connection) {
        super(connection);
    }

    @Override
    public Obat getById(int id) {
        return null;
    }

    @Override
    public ArrayList<Obat> getAll() {
        ArrayList<Obat> obatList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dtacart");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String namaObat = result.getString("namaObat");
                String jenisObat = result.getString("jenisObat");
                int jumlah = result.getInt("jumlah");
                double harga = result.getDouble("HargaObat");
                String keluhan = result.getString("Keluhan");
                Obat obat = new Obat(namaObat, harga, jenisObat,0,keluhan ,jumlah );
                obatList.add(obat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obatList;
    }
}
