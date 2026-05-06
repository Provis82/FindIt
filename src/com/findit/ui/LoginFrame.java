package com.findit.ui;

import com.findit.dao.UserDAO;
import com.findit.model.User;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblMessage;
    private UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();
        initComponents();
        setupForm();
    }

    private void setupForm() {
        setTitle("FindIt — Campus Lost & Found");
        setSize(450, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Header Panel
        JPanel header = new JPanel();
        header.setBackground(new Color(26, 42, 108));
        header.setBounds(0, 0, 450, 90);
        header.setLayout(null);

        JLabel lblTitle = new JLabel("🔍 FindIt");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setBounds(155, 15, 200, 35);
        header.add(lblTitle);

        JLabel lblSub = new JLabel("Campus Lost & Found System");
        lblSub.setForeground(new Color(179, 212, 255));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setBounds(120, 52, 250, 20);
        header.add(lblSub);
        add(header);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setBounds(60, 115, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(60, 138, 330, 35);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtEmail);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setBounds(60, 183, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(60, 206, 330, 35);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(txtPassword);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(60, 258, 155, 38);
        btnLogin.setBackground(new Color(26, 42, 108));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> handleLogin());
        add(btnLogin);

        // Register Button
        btnRegister = new JButton("Register");
        btnRegister.setBounds(235, 258, 155, 38);
        btnRegister.setBackground(new Color(255, 255, 255));
        btnRegister.setForeground(new Color(26, 42, 108));
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setBorder(BorderFactory.createLineBorder(new Color(26, 42, 108), 2));
        btnRegister.addActionListener(e -> openRegister());
        add(btnRegister);

        // Message Label
        lblMessage = new JLabel("");
        lblMessage.setBounds(60, 305, 330, 25);
        lblMessage.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblMessage);
    }

    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("⚠ Please enter email and password!");
            return;
        }

        User user = userDAO.login(email, password);
        if (user != null) {
            lblMessage.setForeground(new Color(0, 153, 0));
            lblMessage.setText("✓ Welcome, " + user.getFullName() + "!");
            Timer timer = new Timer(1000, e -> {
                new DashboardFrame(user).setVisible(true);
                this.dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("✗ Invalid email or password!");
        }
    }

    private void openRegister() {
        new RegisterFrame().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}