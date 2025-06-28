package br.com.betmaster.controller;

import br.com.betmaster.model.dao.UserDAO;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.enums.UserRole;
import br.com.betmaster.view.LoginView;
import br.com.betmaster.view.RegisterView;

import javax.swing.*;

public class RegisterController {

    private RegisterView view;
    private UserDAO userDAO;
    private JFrame mainFrame;

    public RegisterController(RegisterView view, JFrame mainFrame) {
        this.view = view;
        this.userDAO = new UserDAO();
        this.mainFrame = mainFrame;
        initController();
    }

    private void initController() {
        view.getRegisterButton().addActionListener(e -> registerUser());
        view.getBackButton().addActionListener(e -> goBackToLogin());
    }

    private void registerUser() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDAO.getUserByUsername(username) != null) {
            JOptionPane.showMessageDialog(view, "Nome de usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(username, password, UserRole.USER);
        if (userDAO.createUser(newUser)) {
            JOptionPane.showMessageDialog(view, "Usuário cadastrado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            goBackToLogin();
        } else {
            JOptionPane.showMessageDialog(view, "Erro ao cadastrar usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToLogin() {
        mainFrame.getContentPane().removeAll();
        LoginView loginView = new LoginView();
        new LoginController(loginView, mainFrame);
        mainFrame.add(loginView);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
