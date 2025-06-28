package br.com.betmaster.view;

import br.com.betmaster.controller.LoginController;

import javax.swing.*;

/**
 * A janela principal da aplicação.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("BetMaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Adiciona a tela de login ao frame principal
        LoginView loginView = new LoginView();
        new LoginController(loginView, this);
        add(loginView);
    }
}
