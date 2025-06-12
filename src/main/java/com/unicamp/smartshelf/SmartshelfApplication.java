package com.unicamp.smartshelf;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.unicamp.smartshelf.ui.MainWindow;

import javax.swing.*;

public class SmartshelfApplication {

    public static void main(String[] args) {
        // Set FlatLaf Look and Feel with default light theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to initialize FlatLaf");
            e.printStackTrace();
        }

        // Set system properties for better appearance
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Enable animated Look and Feel changes
        FlatAnimatedLafChange.showSnapshot();

        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                new MainWindow().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Erro ao inicializar a aplicação: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Utility method to change theme dynamically
     */
    public static void changeTheme(String themeName) {
        try {
            LookAndFeel newLaf = switch (themeName.toLowerCase()) {
                case "light" -> new FlatLightLaf();
                case "dark" -> new FlatDarkLaf();
                case "onedark" -> new FlatOneDarkIJTheme();
                case "arc" -> new FlatArcIJTheme();
                default -> new FlatLightLaf();
            };

            FlatAnimatedLafChange.showSnapshot();
            UIManager.setLookAndFeel(newLaf);
            FlatLaf.updateUI();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();

        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to change theme to: " + themeName);
            e.printStackTrace();
        }
    }
}
