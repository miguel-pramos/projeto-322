package br.com.betmaster.view;

import br.com.betmaster.controller.LoginController;

import javax.swing.*;
import java.awt.*;

/**
 * Painel (JPanel) contendo os campos de texto e botões para a tela de login.
 */
public class LoginView extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginController loginController;

    public LoginView() {
        this.loginController = new LoginController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Rótulo e campo de usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        // Rótulo e campo de senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Botão de login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        add(loginButton, gbc);

        // Ação do botão
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loginController.login(username, password);
        });
    }
}
