package com.findit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "findit_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@Admin$12345"; // your MySQL password here

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" +
            DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✓ Connected to findit_db!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver not found!");
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}