package com.example.controller;

import com.example.Database.ControllerDataBase;
import com.example.kasir.Obat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KasirPageController {
    @FXML
    static Connection conn = ControllerDataBase.getConnection();
    @FXML
    private TextArea strukArea;
    @FXML
    private Text total;

    private List<String> cart = new ArrayList<>();
    private double totalHarga = 0.0;

    public void setObats(String namaPasien, String umurPasien, List<Obat> obats) {
        StringBuilder struk = new StringBuilder("Nama Pasien: " + namaPasien + "\nUmur Pasien: " + umurPasien + "\n\nDaftar Obat:\n");
        for (Obat obat : obats) {
            struk.append(obat.getNameObat()).append(" - ").append(obat.getJenisObat()).append(" - ").append(obat.getHarga()).append("\n");
            totalHarga += obat.getHarga();
            cart.add(obat.getNameObat());
        }
        total.setText("Total Harga: " + totalHarga);
        strukArea.setText(struk.toString());
    }

    @FXML
    public void minToData(javafx.event.ActionEvent event) throws IOException {
        String query = "UPDATE dtaobat SET stock = stock - 1 WHERE namaObat = ?";
        try (PreparedStatement pstmt2 = conn.prepareStatement(query)) {
            for (String obat : cart) {
                pstmt2.setString(1, obat);
                pstmt2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/com/example/pasien page.fxml"))));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
