package br.com.betmaster.view;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterView() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // User field
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Usuário:"), gbc);
        gbc.gridx = 1;
        userField = new JTextField(20);
        add(userField, gbc);

        // Password field
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Buttons
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerButton = new JButton("Cadastrar");
        backButton = new JButton("Voltar");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        add(buttonPanel, gbc);
    }

    public String getUsername() {
        return userField.getText();
    }


    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
