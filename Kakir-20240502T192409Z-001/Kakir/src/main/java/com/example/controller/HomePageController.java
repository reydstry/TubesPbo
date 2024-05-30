package com.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomePageController {
    @FXML
    private void toFindPage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/com/example/pasien page.fxml"))));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    private void toStockPage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/com/example/stock page.fxml"))));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    private ImageView cariObat;
    @FXML
    private ImageView tambahObat;

    @FXML
    private void initialize() {
        cariObat.setOnMouseEntered(event -> {
            cariObat.setImage(new Image("com/example/image/cari3.png"));
        });
        cariObat.setOnMouseExited(event -> {
            cariObat.setImage(new Image("com/example/image/cari.png"));
        });
        cariObat.setOnMousePressed(event -> {
            cariObat.setImage(new Image("com/example/image/cari2.png"));
        });
        tambahObat.setOnMouseEntered(event -> {
            tambahObat.setImage(new Image("com/example/image/tambah3.png"));
        });
        tambahObat.setOnMouseExited(event -> {
            tambahObat.setImage(new Image("com/example/image/tambah.png"));
        });
        tambahObat.setOnMousePressed(event -> {
            tambahObat.setImage(new Image("com/example/image/tambah2.png"));
        });

    }
}
