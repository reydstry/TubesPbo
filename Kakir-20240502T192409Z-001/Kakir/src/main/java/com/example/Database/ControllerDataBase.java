package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControllerDataBase {
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/tbobat", "root", "");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
