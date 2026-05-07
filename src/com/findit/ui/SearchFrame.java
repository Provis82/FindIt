package com.findit.ui;

import com.findit.dao.ClaimDAO;
import com.findit.dao.ItemDAO;
import com.findit.model.Item;
import com.findit.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchFrame extends JFrame {

    private User currentUser;
    private ItemDAO itemDAO;
    private ClaimDAO claimDAO;
    private JTextField txtSearch;
    private JComboBox<String> cmbCategory;
    private JTable tblResults;
    private DefaultTableModel tableModel;
    private JLabel lblCount;

    public SearchFrame(User user) {
        this.currentUser = user;
        this.itemDAO = new ItemDAO();
        this.claimDAO = new ClaimDAO();
        initComponents();
        setupForm();
        loadAllItems();
    }

    private void setupForm() {
        setTitle("FindIt — Search Items");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(null);
        getContentPane().setBackground(new Color(244, 246, 248));

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(26, 42, 108));
        header.setBounds(0, 0, 900, 60);
        header.setLayout(null);

        JLabel lblTitle = new JLabel("🔍 Search Lost & Found Items");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(290, 15, 350, 30);
        header.add(lblTitle);
        add(header);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBounds(20, 75, 855, 80);
        searchPanel.setLayout(null);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSearch.setBounds(15, 25, 60, 25);
        searchPanel.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(80, 22, 300, 32);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchPanel.add(txtSearch);

        JLabel lblCat = new JLabel("Category:");
        lblCat.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCat.setBounds(400, 25, 75, 25);
        searchPanel.add(lblCat);

        String[] categories = {"All", "Electronics", "Documents", "Personal Items",
                               "Stationery", "Clothing", "Keys", "Other"};
        cmbCategory = new JComboBox<>(categories);
        cmbCategory.setBounds(480, 22, 160, 32);
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchPanel.add(cmbCategory);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(655, 22, 90, 32);
        btnSearch.setBackground(new Color(26, 42, 108));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(e -> handleSearch());
        searchPanel.add(btnSearch);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(755, 22, 80, 32);
        btnClear.setBackground(new Color(108, 117, 125));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClear.setFocusPainted(false);
        btnClear.setBorderPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            cmbCategory.setSelectedIndex(0);
            loadAllItems();
        });
        searchPanel.add(btnClear);

        add(searchPanel);

        // Results count
        lblCount = new JLabel("Showing all items");
        lblCount.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblCount.setForeground(new Color(108, 117, 125));
        lblCount.setBounds(20, 162, 300, 20);
        add(lblCount);

        // Table
        String[] columns = {"ID", "Title", "Category", "Location", "Date Found", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblResults = new JTable(tableModel);
        tblResults.setRowHeight(28);
        tblResults.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblResults.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblResults.getTableHeader().setBackground(new Color(244, 246, 248));
        tblResults.setSelectionBackground(new Color(210, 220, 245));

        JScrollPane scrollPane = new JScrollPane(tblResults);
        scrollPane.setBounds(20, 185, 855, 340);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(scrollPane);

        // Claim Button
        JButton btnClaim = new JButton("📋 Submit Claim for Selected Item");
        btnClaim.setBounds(20, 535, 280, 40);
        btnClaim.setBackground(new Color(40, 167, 69));
        btnClaim.setForeground(Color.WHITE);
        btnClaim.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClaim.setFocusPainted(false);
        btnClaim.setBorderPainted(false);
        btnClaim.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClaim.addActionListener(e -> handleClaim());
        add(btnClaim);

        // Back Button
        JButton btnBack = new JButton("← Back to Dashboard");
        btnBack.setBounds(700, 535, 175, 40);
        btnBack.setBackground(new Color(108, 117, 125));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> dispose());
        add(btnBack);
    }

    private void loadAllItems() {
        tableModel.setRowCount(0);
        List<Item> items = itemDAO.getAllItems();
        for (Item item : items) {
            tableModel.addRow(new Object[]{
                item.getId(),
                item.getTitle(),
                item.getCategory(),
                item.getLocation(),
                item.getDateFound(),
                item.getStatus()
            });
        }
        lblCount.setText("Showing " + items.size() + " items");
    }

    private void handleSearch() {
        String keyword = txtSearch.getText().trim();
        String category = cmbCategory.getSelectedItem().toString();

        tableModel.setRowCount(0);
        List<Item> items;

        if (!keyword.isEmpty()) {
            items = itemDAO.searchItems(keyword);
        } else if (!category.equals("All")) {
            items = itemDAO.filterByCategory(category);
        } else {
            items = itemDAO.getAllItems();
        }

        for (Item item : items) {
            tableModel.addRow(new Object[]{
                item.getId(),
                item.getTitle(),
                item.getCategory(),
                item.getLocation(),
                item.getDateFound(),
                item.getStatus()
            });
        }
        lblCount.setText("Showing " + items.size() + " result(s)");
    }

    private void handleClaim() {
        int selectedRow = tblResults.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an item to claim!",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        String itemTitle = tableModel.getValueAt(selectedRow, 1).toString();
        String status = tableModel.getValueAt(selectedRow, 5).toString();

        if (!status.equals("available")) {
            JOptionPane.showMessageDialog(this,
                "This item is no longer available for claiming!",
                "Not Available", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String proof = JOptionPane.showInputDialog(this,
            "To claim \"" + itemTitle + "\", please describe something\n" +
            "specific about the item to prove it's yours:",
            "Verify Ownership", JOptionPane.QUESTION_MESSAGE);

        if (proof != null && !proof.trim().isEmpty()) {
            com.findit.model.Claim claim = new com.findit.model.Claim(
                itemId, currentUser.getId(), proof.trim());
            boolean success = claimDAO.submitClaim(claim);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "✓ Claim submitted successfully!\nAn admin will review your claim.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to submit claim. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}