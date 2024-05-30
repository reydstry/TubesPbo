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
