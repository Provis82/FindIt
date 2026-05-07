package com.findit.ui;

import com.findit.dao.ItemDAO;
import com.findit.model.Item;
import com.findit.model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ReportItemFrame extends JFrame {

    private User currentUser;
    private ItemDAO itemDAO;
    private JTextField txtTitle, txtLocation;
    private JTextArea txtDescription;
    private JComboBox<String> cmbCategory;
    private JButton btnSubmit, btnCancel;
    private JLabel lblMessage;

    public ReportItemFrame(User user) {
        this.currentUser = user;
        this.itemDAO = new ItemDAO();
        initComponents();
        setupForm();
    }

    private void setupForm() {
        setTitle("FindIt — Report Found Item");
        setSize(500, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(26, 42, 108));
        header.setBounds(0, 0, 500, 60);
        header.setLayout(null);

        JLabel lblTitle = new JLabel("📦 Report Found Item");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(150, 15, 250, 30);
        header.add(lblTitle);
        add(header);

        // Item Title
        JLabel lblItemTitle = new JLabel("Item Title: *");
        lblItemTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblItemTitle.setBounds(40, 80, 150, 25);
        add(lblItemTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(40, 105, 420, 35);
        txtTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtTitle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtTitle);

        // Category
        JLabel lblCategory = new JLabel("Category: *");
        lblCategory.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCategory.setBounds(40, 150, 150, 25);
        add(lblCategory);

        String[] categories = {"Electronics", "Documents", "Personal Items",
                               "Stationery", "Clothing", "Keys", "Other"};
        cmbCategory = new JComboBox<>(categories);
        cmbCategory.setBounds(40, 175, 420, 35);
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        add(cmbCategory);

        // Location
        JLabel lblLocation = new JLabel("Where did you find it? *");
        lblLocation.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLocation.setBounds(40, 220, 200, 25);
        add(lblLocation);

        txtLocation = new JTextField();
        txtLocation.setBounds(40, 245, 420, 35);
        txtLocation.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtLocation.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtLocation);

        // Description
        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDesc.setBounds(40, 290, 150, 25);
        add(lblDesc);

        txtDescription = new JTextArea();
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setBounds(40, 315, 420, 80);
        add(scrollDesc);

        // Submit Button
        btnSubmit = new JButton("Submit Report");
        btnSubmit.setBounds(40, 410, 200, 40);
        btnSubmit.setBackground(new Color(26, 42, 108));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(e -> handleSubmit());
        add(btnSubmit);

        // Cancel Button
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(260, 410, 200, 40);
        btnCancel.setBackground(new Color(220, 53, 69));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);

        // Message
        lblMessage = new JLabel("");
        lblMessage.setBounds(40, 460, 420, 25);
        lblMessage.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
    }

    private void handleSubmit() {
        String title = txtTitle.getText().trim();
        String location = txtLocation.getText().trim();
        String category = cmbCategory.getSelectedItem().toString();
        String description = txtDescription.getText().trim();

        if (title.isEmpty() || location.isEmpty()) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Please fill in all required fields!");
            return;
        }

        Item item = new Item(
            title, description, category,
            location, LocalDate.now().toString(),
            currentUser.getId()
        );

        boolean success = itemDAO.addItem(item);
        if (success) {
            lblMessage.setForeground(new Color(0, 153, 0));
            lblMessage.setText("✓ Item reported successfully!");
            Timer timer = new Timer(1500, e -> dispose());
            timer.setRepeats(false);
            timer.start();
        } else {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("✗ Failed to submit report. Try again!");
        }
    }
}