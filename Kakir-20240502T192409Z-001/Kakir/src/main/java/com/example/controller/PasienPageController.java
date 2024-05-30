package com.example.controller;

import com.example.Dao.DaoObat;
import com.example.Database.ControllerDataBase;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private TableColumn<Obat, String> jenisObatcolumn;
    @FXML
    private TableColumn<Obat, Integer> dosisObatcolumn;
    @FXML
    private TableColumn<Obat, Double> hargaObatcolumn;
    @FXML
    private TableColumn<Obat, String> Keluhancolumn;
    @FXML
    static Connection conn = ControllerDataBase.getConnection();
    static DaoObat daoObat = new DaoObat(conn);

    private List<Obat> cart = new ArrayList<>();


    @FXML
    private void initialize() {
        namaObatColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameObat()));
        jenisObatcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJenisObat()));
        dosisObatcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDosis()).asObject());
        hargaObatcolumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHarga()).asObject());
        Keluhancolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKeluhan()));


        tableObat.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                TablePosition tablePosition = tableObat.getSelectionModel().getSelectedCells().get(0);
                int row = tablePosition.getRow();
                Obat selectedObat = tableObat.getItems().get(row);
                if (selectedObat != null) {
                    cart.add(selectedObat);
                    System.out.println("Obat ditambahkan ke keranjang: " + cart);
                } else {
                    System.out.println("Tidak ada obat yang dipilih.");
                }
            }
        });

        namaObat.textProperty().addListener((observable, oldValue, newValue) -> updateTable(newValue));

        updateTable("");
    }

    private void updateTable(String keyword) {
        ObservableList<Obat> obatList = FXCollections.observableArrayList(daoObat.searchObat(keyword));
        tableObat.setItems(obatList);
    }

    @FXML
    private void toKasirPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kasir page.fxml"));
        Parent root = loader.load();

        KasirPageController kasirPageController = loader.getController();
        kasirPageController.setObats(namaPasien.getText(), umurPasien.getText(),cart);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void toHomePage(ActionEvent event) throws IOException {
        Connection connection = ControllerDataBase.getConnection();
        System.out.println(connection);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/main Hole.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
