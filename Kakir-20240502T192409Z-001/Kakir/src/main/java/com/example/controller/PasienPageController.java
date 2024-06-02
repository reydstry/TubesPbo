package com.example.controller;

import com.example.Dao.DaoCart;
import com.example.Dao.DaoObat;
import com.example.Database.ControllerDataBaseObat;
import com.example.kasir.Obat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class PasienPageController {

    @FXML
    private TextField namaPasien;
    @FXML
    private TextField umurPasien;
    @FXML
    private TextField namaObat;
    @FXML
    private TableView<Obat> tableObat;
    @FXML
    private TableColumn<Obat, String> namaObatColumn;
    @FXML
    private TableColumn<Obat, String> jenisObatColumn;
    @FXML
    private TableColumn<Obat, Integer> dosisObatColumn;
    @FXML
    private TableColumn<Obat, Double> hargaObatColumn;
    @FXML
    private TableColumn<Obat, String> keluhanColumn;
    @FXML
    static Connection conn = ControllerDataBaseObat.getConnection();
    static DaoObat daoObat = new DaoObat(conn);
    static DaoCart daoCart = new DaoCart(conn);

    private ObservableList<Obat> cart = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        namaObatColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameObat()));
        jenisObatColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJenisObat()));
        dosisObatColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        hargaObatColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHarga()).asObject());
        keluhanColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKeluhan()));

        tableObat.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                TablePosition tablePosition = tableObat.getSelectionModel().getSelectedCells().get(0);
                int row = tablePosition.getRow();
                Obat selectedObat = tableObat.getItems().get(row);

                if (selectedObat != null) {
                    int quantity = getQuantityFromUser(selectedObat);
                    if (quantity > 0) {
                        addOrUpdateCart(selectedObat, quantity);
                    }
                }
            }
        });

        namaObat.textProperty().addListener((observable, oldValue, newValue) -> updateTable(newValue));

        updateTable("");
        cart.addAll(daoCart.getAll());
    }

    private int getQuantityFromUser(Obat obat) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Input Quantity");
        dialog.setHeaderText("Input the quantity for " + obat.getNameObat());
        dialog.setContentText("Please enter the quantity:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int quantity = Integer.parseInt(result.get());
                if (quantity <= obat.getStock()) {
                    return quantity;
                } else {
                    showAlert(Alert.AlertType.WARNING, "Stock Warning", "Requested quantity exceeds available stock.");
                    return -1;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number.");
            }
        }
        return -1;
    }

    private void addOrUpdateCart(Obat selectedObat, int quantity) {
        if (quantity <= 0) {
            return;
        }

        boolean found = false;
        for (Obat obat : cart) {
            if (obat.getNameObat().equals(selectedObat.getNameObat())) {
                int newQuantity = obat.getJumlah() + quantity;
                if (newQuantity <= selectedObat.getStock()) {
                    obat.setJumlah(newQuantity);
                    updateObatInDatabase(obat);
                    found = true;
                    showAlert(Alert.AlertType.INFORMATION, "Information", "Updated cart with additional quantity.");
                } else {
                    showAlert(Alert.AlertType.WARNING, "Stock Warning", "Requested quantity exceeds available stock.");
                }
                return;
            }
        }

        if (!found) {
            if (quantity <= selectedObat.getStock()) {
                selectedObat.setJumlah(quantity);
                cart.add(selectedObat);
                insertObatToDatabase(selectedObat);
                showAlert(Alert.AlertType.INFORMATION, "Information", "Added new item to cart.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Stock Warning", "Requested quantity exceeds available stock.");
            }
        }
    }

    private void updateObatInDatabase(Obat obat) {
        String sql = "UPDATE dtacart SET jumlah = ? WHERE namaObat = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obat.getJumlah());
            pstmt.setString(2, obat.getNameObat());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertObatToDatabase(Obat obat) {
        String sql = "INSERT INTO dtacart (namaObat, jenisObat, jumlah, hargaObat, keluhan) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obat.getNameObat());
            pstmt.setString(2, obat.getJenisObat());
            pstmt.setInt(3, obat.getJumlah());
            pstmt.setDouble(4, obat.getHarga());
            pstmt.setString(5, obat.getKeluhan());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTable(String keyword) {
        ObservableList<Obat> obatList = FXCollections.observableArrayList(daoObat.search(keyword));
        tableObat.setItems(obatList);
    }

    @FXML
    private void toKasirPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kasir page.fxml"));
        Parent root = loader.load();

        KasirPageController kasirPageController = loader.getController();
        kasirPageController.setObats(namaPasien.getText(), umurPasien.getText());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void toHomePage(ActionEvent event) throws IOException {
        Connection connection = ControllerDataBaseObat.getConnection();
        System.out.println(connection);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/main Hole.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toCartPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cart page.fxml"));
        Parent root = loader.load();

        CartPageController cartPageController = loader.getController();
        cartPageController.setObats(namaPasien.getText(), umurPasien.getText());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
