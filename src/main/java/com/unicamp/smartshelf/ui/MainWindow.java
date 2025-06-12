package com.unicamp.smartshelf.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame {
    
    public MainWindow() {
        
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
