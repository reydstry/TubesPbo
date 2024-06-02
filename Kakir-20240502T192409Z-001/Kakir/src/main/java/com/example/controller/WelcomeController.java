package com.example.controller;

import com.example.Database.ControllerDataBaseObat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;

public class WelcomeController {
    @FXML
    private void toHomePage(MouseEvent event) throws IOException {

        Connection connection = ControllerDataBaseObat.getConnection();
        System.out.println(connection);
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/main Hole.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Text Continue;

    @FXML
    private void initialize() {

        Continue.setOnMouseEntered(event -> {
            Continue.setFill(Color.LIGHTGREEN);
        });

        Continue.setOnMouseExited(event -> {
            Continue.setFill(Color.GREEN);
        });

        Continue.setOnMousePressed(event -> {
            Continue.setFill(Color.DARKGREEN);
        });

    }
}
