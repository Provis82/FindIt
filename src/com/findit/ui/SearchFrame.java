package com.findit.ui;

import com.findit.model.User;
import javax.swing.*;

public class SearchFrame extends JFrame {
    public SearchFrame(User user) {
        setTitle("Search Items");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}