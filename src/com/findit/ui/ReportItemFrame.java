package com.findit.ui;

import com.findit.model.User;
import javax.swing.*;

public class ReportItemFrame extends JFrame {
    public ReportItemFrame(User user) {
        setTitle("Report Found Item");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}