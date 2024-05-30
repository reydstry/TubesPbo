package com.example.controller;

import com.example.Database.ControllerDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    static Connection conn = ControllerDataBase.getConnection();
    @FXML
    private TextField namaObat;
    @FXML
    private TextField banyakStock;

    @FXML
    public void addToData() throws SQLException {

        String obat = namaObat.getText();
        String banyak= banyakStock.getText();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE dtaobat SET stock = stock + " + banyak +" WHERE namaObat = ?");
        pstmt.setString(1, obat);
        pstmt.executeUpdate();

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
    @FXML
    private Text tambahStock;

    @FXML
    private void initialize() {
        tambahStock.setOnMouseEntered(event -> {
            tambahStock.setFill(Color.DARKGREEN);
        });

        tambahStock.setOnMouseExited(event -> {
            tambahStock.setFill(Color.GREEN);
        });
    }
}
