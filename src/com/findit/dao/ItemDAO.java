package com.findit.dao;

import com.findit.database.DatabaseConnection;
import com.findit.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // Add new item
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (title, description, category, location, date_found, status, posted_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getCategory());
            ps.setString(4, item.getLocation());
            ps.setString(5, item.getDateFound());
            ps.setString(6, "available");
            ps.setInt(7, item.getPostedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add item error: " + e.getMessage());
            return false;
        }
    }

    // Get all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY date_found DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            System.err.println("Get all items error: " + e.getMessage());
        }
        return items;
    }

    // Search items by keyword
    public List<Item> searchItems(String keyword) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE LOWER(title) LIKE ? OR " +
                     "LOWER(category) LIKE ? OR LOWER(location) LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            System.err.println("Search error: " + e.getMessage());
        }
        return items;
    }

    // Filter items by category
    public List<Item> filterByCategory(String category) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE LOWER(category) = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.toLowerCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            System.err.println("Filter error: " + e.getMessage());
        }
        return items;
    }

    // Update item status
    public boolean updateStatus(int itemId, String status) {
        String sql = "UPDATE items SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update status error: " + e.getMessage());
            return false;
        }
    }

    // Get total items count
    public int getTotalItems() {
        String sql = "SELECT COUNT(*) FROM items";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Count error: " + e.getMessage());
        }
        return 0;
    }

    // Get count by status
    public int getCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM items WHERE status=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Count by status error: " + e.getMessage());
        }
        return 0;
    }

    // Map ResultSet to Item
    private Item mapItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setTitle(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setCategory(rs.getString("category"));
        item.setLocation(rs.getString("location"));
        item.setDateFound(rs.getString("date_found"));
        item.setStatus(rs.getString("status"));
        item.setPostedBy(rs.getInt("posted_by"));
        return item;
    }
}