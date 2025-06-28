package br.com.betmaster.controller;

import br.com.betmaster.model.dao.UserDAO;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.view.DashboardView;
import br.com.betmaster.view.LoginView;
import br.com.betmaster.view.RegisterView;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Orquestra a lógica de autenticação, mediando entre a LoginView e o UserDAO.
 */
public class LoginController {

    private LoginView view;
    private UserDAO userDAO;
    private JFrame mainFrame;

    public LoginController(LoginView view, JFrame mainFrame) {
        this.view = view;
        this.userDAO = new UserDAO();
        this.mainFrame = mainFrame;
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(e -> login());
        view.getRegisterButton().addActionListener(e -> showRegisterView());
    }

    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (userDAO.validatePassword(username, password)) {
            JOptionPane.showMessageDialog(view, "Login bem-sucedido!");

            User user = userDAO.getUserByUsername(username);

            mainFrame.getContentPane().removeAll();
            DashboardView dashboardView = new DashboardView(user);
            new DashboardController(dashboardView, user); // Adiciona o controller
            mainFrame.add(dashboardView);
            mainFrame.revalidate();
            mainFrame.repaint();
        } else {
            JOptionPane.showMessageDialog(view, "Usuário ou senha inválidos.", "Erro de Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisterView() {
        mainFrame.getContentPane().removeAll();
        RegisterView registerView = new RegisterView();
        new RegisterController(registerView, mainFrame);
        mainFrame.add(registerView);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
