package com.findit.dao;

import com.findit.database.DatabaseConnection;
import com.findit.model.Claim;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    // Submit a new claim
    public boolean submitClaim(Claim claim) {
        String sql = "INSERT INTO claims (item_id, claimant_id, proof_answer, status) " +
                     "VALUES (?, ?, ?, 'pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, claim.getItemId());
            ps.setInt(2, claim.getClaimantId());
            ps.setString(3, claim.getProofAnswer());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Submit claim error: " + e.getMessage());
            return false;
        }
    }

    // Get all claims (for admin)
    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM claims ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                claims.add(mapClaim(rs));
            }
        } catch (SQLException e) {
            System.err.println("Get claims error: " + e.getMessage());
        }
        return claims;
    }

    // Get claims by user
    public List<Claim> getClaimsByUser(int userId) {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM claims WHERE claimant_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                claims.add(mapClaim(rs));
            }
        } catch (SQLException e) {
            System.err.println("Get user claims error: " + e.getMessage());
        }
        return claims;
    }

    // Approve or reject a claim
    public boolean updateClaimStatus(int claimId, String status) {
        String sql = "UPDATE claims SET status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, claimId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update claim error: " + e.getMessage());
            return false;
        }
    }

    // Get pending claims count
    public int getPendingClaimsCount() {
        String sql = "SELECT COUNT(*) FROM claims WHERE status='pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Pending count error: " + e.getMessage());
        }
        return 0;
    }

    // Map ResultSet to Claim
    private Claim mapClaim(ResultSet rs) throws SQLException {
        Claim claim = new Claim();
        claim.setId(rs.getInt("id"));
        claim.setItemId(rs.getInt("item_id"));
        claim.setClaimantId(rs.getInt("claimant_id"));
        claim.setProofAnswer(rs.getString("proof_answer"));
        claim.setStatus(rs.getString("status"));
        return claim;
    }
}