package br.com.betmaster.view;

import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.enums.UserRole;
import br.com.betmaster.view.panels.AdminPanel;
import br.com.betmaster.view.panels.MatchesPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {

    private JTabbedPane tabbedPane;
    private MatchesPanel matchesPanel;
    private BetsPanel betsPanel;
    private AdminPanel adminPanel; // Painel para funcionalidades de admin

    public DashboardView(User user) {
        setLayout(new BorderLayout());
        initComponents(user.getRole());
    }

    private void initComponents(UserRole role) {
        tabbedPane = new JTabbedPane();
        matchesPanel = new MatchesPanel();
        betsPanel = new BetsPanel();

        tabbedPane.addTab("Partidas", matchesPanel);
        tabbedPane.addTab("Apostas", betsPanel);

        // Adiciona a aba de Admin se o usuário for um administrador
        if (role == UserRole.ADMIN) {
            adminPanel = new AdminPanel();
            tabbedPane.addTab("Admin", adminPanel);
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    public MatchesPanel getMatchesPanel() {
        return matchesPanel;
    }

    public BetsPanel getBetsPanel() {
        return betsPanel;
    }

    public AdminPanel getAdminPanel() {
        return adminPanel;
    }
}
