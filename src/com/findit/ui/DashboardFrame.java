package com.findit.ui;

import com.findit.dao.ItemDAO;
import com.findit.model.Item;
import com.findit.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {

    private User currentUser;
    private ItemDAO itemDAO;
    private JTable tblItems;
    private DefaultTableModel tableModel;
    private JLabel lblTotal, lblAvailable, lblClaimed, lblReturned;

    public DashboardFrame(User user) {
        this.currentUser = user;
        this.itemDAO = new ItemDAO();
        initComponents();
        setupForm();
        loadStats();
        loadRecentItems();
    }

    private void setupForm() {
        setTitle("FindIt — Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(244, 246, 248));

        // ── Top Navigation Bar ──────────────────────────────
        JPanel navbar = new JPanel();
        navbar.setBackground(new Color(26, 42, 108));
        navbar.setLayout(null);
        navbar.setPreferredSize(new Dimension(950, 60));

        JLabel lblLogo = new JLabel("🔍 FindIt");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLogo.setBounds(20, 15, 150, 30);
        navbar.add(lblLogo);

        JLabel lblWelcome = new JLabel("Welcome, " + currentUser.getFullName() + " 👋");
        lblWelcome.setForeground(new Color(179, 212, 255));
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblWelcome.setBounds(300, 18, 350, 25);
        navbar.add(lblWelcome);

        JButton btnReport = new JButton("📦 Report Found Item");
        btnReport.setBounds(620, 12, 180, 35);
        btnReport.setBackground(Color.WHITE);
        btnReport.setForeground(new Color(26, 42, 108));
        btnReport.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnReport.setFocusPainted(false);
        btnReport.setBorderPainted(false);
        btnReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReport.addActionListener(e -> openReportItem());
        navbar.add(btnReport);

        JButton btnSearch = new JButton("🔍 Search");
        btnSearch.setBounds(810, 12, 110, 35);
        btnSearch.setBackground(new Color(220, 53, 69));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(e -> openSearch());
        navbar.add(btnSearch);

        add(navbar, BorderLayout.NORTH);

        // ── Main Content ────────────────────────────────────
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setBackground(new Color(244, 246, 248));

        // Stats Cards
        String[] statLabels = {"Total Items", "Available", "Claimed", "Returned"};
        Color[] statColors = {
            new Color(26, 42, 108),
            new Color(40, 167, 69),
            new Color(255, 193, 7),
            new Color(23, 162, 184)
        };
        JLabel[] statValues = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setBackground(Color.WHITE);
            card.setBounds(20 + i * 225, 20, 205, 100);
            card.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, statColors[i]));

            JLabel lblValue = new JLabel("0");
            lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
            lblValue.setForeground(statColors[i]);
            lblValue.setBounds(20, 15, 150, 40);
            card.add(lblValue);

            JLabel lblLabel = new JLabel(statLabels[i]);
            lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblLabel.setForeground(new Color(108, 117, 125));
            lblLabel.setBounds(20, 58, 150, 20);
            card.add(lblLabel);

            statValues[i] = lblValue;
            content.add(card);
        }

        lblTotal     = statValues[0];
        lblAvailable = statValues[1];
        lblClaimed   = statValues[2];
        lblReturned  = statValues[3];

        // Recent Items Title
        JLabel lblRecent = new JLabel("Recent Items");
        lblRecent.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRecent.setForeground(new Color(26, 42, 108));
        lblRecent.setBounds(20, 135, 200, 30);
        content.add(lblRecent);

        // Table
        String[] columns = {"ID", "Title", "Category", "Location", "Date Found", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblItems = new JTable(tableModel);
        tblItems.setRowHeight(28);
        tblItems.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblItems.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblItems.getTableHeader().setBackground(new Color(244, 246, 248));
        tblItems.setSelectionBackground(new Color(210, 220, 245));

        JScrollPane scrollPane = new JScrollPane(tblItems);
        scrollPane.setBounds(20, 170, 895, 350);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        content.add(scrollPane);

        // Logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(850, 535, 80, 30);
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        content.add(btnLogout);

        add(content, BorderLayout.CENTER);
    }

    private void loadStats() {
        lblTotal.setText(String.valueOf(itemDAO.getTotalItems()));
        lblAvailable.setText(String.valueOf(itemDAO.getCountByStatus("available")));
        lblClaimed.setText(String.valueOf(itemDAO.getCountByStatus("claimed")));
        lblReturned.setText(String.valueOf(itemDAO.getCountByStatus("returned")));
    }

    private void loadRecentItems() {
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
    }

    private void openReportItem() {
        new ReportItemFrame(currentUser).setVisible(true);
    }

    private void openSearch() {
        new SearchFrame(currentUser).setVisible(true);
    }
}