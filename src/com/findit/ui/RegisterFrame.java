package com.findit.ui;

import com.findit.dao.UserDAO;
import com.findit.model.User;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private UserDAO userDAO;
    private JTextField txtFullName, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister, btnBack;
    private JLabel lblMessage;

    public RegisterFrame() {
        this.userDAO = new UserDAO();
        initComponents();
        setupForm();
    }

    private void setupForm() {
        setTitle("FindIt — Register");
        setSize(450, 480);
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
        header.setBounds(0, 0, 450, 60);
        header.setLayout(null);

        JLabel lblTitle = new JLabel("🔍 Create Account");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(145, 15, 220, 30);
        header.add(lblTitle);
        add(header);

        // Full Name
        JLabel lblName = new JLabel("Full Name: *");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblName.setBounds(40, 80, 150, 25);
        add(lblName);

        txtFullName = new JTextField();
        txtFullName.setBounds(40, 105, 370, 35);
        txtFullName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtFullName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtFullName);

        // Email
        JLabel lblEmail = new JLabel("Email: *");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setBounds(40, 150, 150, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(40, 175, 370, 35);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtEmail);

        // Password
        JLabel lblPassword = new JLabel("Password: *");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setBounds(40, 220, 150, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 245, 370, 35);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtPassword);

        // Confirm Password
        JLabel lblConfirm = new JLabel("Confirm Password: *");
        lblConfirm.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblConfirm.setBounds(40, 290, 200, 25);
        add(lblConfirm);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(40, 315, 370, 35);
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtConfirmPassword);

        // Register Button
        btnRegister = new JButton("Create Account");
        btnRegister.setBounds(40, 370, 175, 40);
        btnRegister.setBackground(new Color(26, 42, 108));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegister.setFocusPainted(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.addActionListener(e -> handleRegister());
        add(btnRegister);

        // Back Button
        btnBack = new JButton("← Back to Login");
        btnBack.setBounds(235, 370, 175, 40);
        btnBack.setBackground(new Color(108, 117, 125));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        add(btnBack);

        // Message
        lblMessage = new JLabel("");
        lblMessage.setBounds(40, 420, 370, 25);
        lblMessage.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
    }

    private void handleRegister() {
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirm = new String(txtConfirmPassword.getPassword()).trim();

        // Validation
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Please fill in all fields!");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Please enter a valid email!");
            return;
        }

        if (!password.equals(confirm)) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Passwords do not match!");
            return;
        }

        if (password.length() < 6) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Password must be at least 6 characters!");
            return;
        }

        if (userDAO.emailExists(email)) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Email already registered!");
            return;
        }

        User user = new User(fullName, email, password, "student");
        boolean success = userDAO.register(user);
        if (success) {
            lblMessage.setForeground(new Color(0, 153, 0));
            lblMessage.setText("✓ Account created successfully!");
            Timer timer = new Timer(1500, e -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("✗ Registration failed. Try again!");
        }
    }
}