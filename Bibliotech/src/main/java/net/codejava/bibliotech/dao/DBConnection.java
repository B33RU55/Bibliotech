package net.codejava.bibliotech.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // The "path location" (JDBC URL) for your database
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotech_db?useSSL=false&serverTimezone=UTC";
    
    // Update these if your phpMyAdmin user/pass are different
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}