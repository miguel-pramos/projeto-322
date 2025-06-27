package br.com.betmaster.controller;

import br.com.betmaster.model.dao.UserDAO;
import br.com.betmaster.view.LoginView;

import javax.swing.JOptionPane;

/**
 * Orquestra a lógica de autenticação, mediando entre a LoginView e o UserDAO.
 */
public class LoginController {

    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    /**
     * Tenta autenticar um usuário.
     *
     * @param username o nome de usuário.
     * @param password a senha.
     * @return true se a autenticação for bem-sucedida, false caso contrário.
     */
    public boolean login(String username, String password) {
        if (userDAO.validatePassword(username, password)) {
            JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.", "Erro de Login",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
