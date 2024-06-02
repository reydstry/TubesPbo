package com.example.controller;

import com.example.Database.ControllerDataBaseObat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class StockPageController {
    @FXML
    private TextField namaObat;
    @FXML
    private TextField banyakStock;
    @FXML
    private Text tambahStock;

    private static final Connection conn;

    static {
        conn = ControllerDataBaseObat.getConnection();
    }

    @FXML
    public void addToData() {
        String obat = namaObat.getText();
        String banyak = banyakStock.getText();

        try {
            int banyakInt = Integer.parseInt(banyak);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE dtaobat SET stock = stock + ? WHERE namaObat = ?");
            pstmt.setInt(1, banyakInt);
            pstmt.setString(2, obat);
            System.out.println("Executing SQL: UPDATE dtaobat SET stock = stock + " + banyakInt + " WHERE namaObat = '" + obat + "'");
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Information", null, "Obat berhasil ditambah ke Stock.");
            } else {
                showAlert("Warning", null, "Obat tidak ditemukan dalam database.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", null, "Banyak stock harus berupa angka.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error", e.getMessage());
        }
    }

    @FXML
    private void toFindPage(ActionEvent event) throws IOException {
        navigateToPage(event, "/com/example/pasien page.fxml");
    }

    @FXML
    private void toHomePage(ActionEvent event) throws IOException {
        navigateToPage(event, "/com/example/main Hole.fxml");
    }

    private void navigateToPage(ActionEvent event, String resourcePath) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resourcePath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        tambahStock.setOnMouseEntered(event -> tambahStock.setFill(Color.DARKGREEN));
        tambahStock.setOnMouseExited(event -> tambahStock.setFill(Color.GREEN));
    }

    private static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
