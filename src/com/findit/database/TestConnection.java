package com.findit.database;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Connected: " + (conn != null));
            
            // Test query
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println("User: " + rs.getString("email") + " | Pass: " + rs.getString("password"));
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}