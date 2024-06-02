package com.example.controller;

import com.example.Dao.DaoCart;
import com.example.Database.ControllerDataBaseObat;
import com.example.kasir.Obat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class KasirPageController {
    @FXML
    private TextArea strukArea;
    @FXML
    private Text total;

    static Connection conn = ControllerDataBaseObat.getConnection();
    static DaoCart daoCart = new DaoCart(conn);
    private ObservableList<Obat> obats;
    private double totalHarga = 0.0;
    private List<Obat> cart = new ArrayList<>();

    public void setObats(String namaPasien, String umurPasien) {

        StringBuilder struk = new StringBuilder("Nama Pasien: " + namaPasien + "\nUmur Pasien: " + umurPasien + "\n\nDaftar Obat:\n");
        obats = FXCollections.observableArrayList(daoCart.getAll());
        for (Obat obat : obats) {
            struk.append(obat.getNameObat()).append(" - ").append(obat.getJenisObat()).append(" - ").append(obat.getHarga()).append(" - ").append(obat.getJumlah()).append("\n");
            totalHarga += obat.getHarga() * obat.getJumlah();
            cart.add(obat);
        }
        total.setText("Total Harga: " + totalHarga);
        strukArea.setText(struk.toString());
    }

    @FXML
    private void minToData(javafx.event.ActionEvent event) throws IOException {
        System.out.println(cart);

        if (!cart.isEmpty()){
            for (Obat obat : cart) {
                stock(obat);
                deleteObatFromDatabase(obat);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Transaksi Berhasil!!!");
            alert.setContentText("Apakah Anda yakin ingin melanjutkan ke halaman home?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Connection connection = ControllerDataBaseObat.getConnection();
                System.out.println(connection);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/main Hole.fxml")));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(true);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Tidak Ada Obat Yang Akan Di Proses");
            alert.setContentText("Apakah Anda ingin menambahkan Obat?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Connection connection = ControllerDataBaseObat.getConnection();
                System.out.println(connection);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/pasien page.fxml")));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(true);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                alert.close();
            }
        }


    }
    private void deleteObatFromDatabase (Obat obat){
        String query = "DELETE FROM dtacart WHERE namaObat = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, obat.getNameObat());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void stock (Obat obat){
        String query = "UPDATE dtaobat SET stock = stock - ? WHERE namaObat = ?";
        try (PreparedStatement pstmt2 = conn.prepareStatement(query)) {
            for (Obat stock : cart) {
                pstmt2.setInt(1, stock.getJumlah());
                pstmt2.setString(2,stock.getNameObat());
                pstmt2.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toFindPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/com/example/pasien page.fxml"))));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
