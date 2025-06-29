package br.com.betmaster.view;

import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.enums.UserRole;
import br.com.betmaster.view.panels.AdminPanel;
import br.com.betmaster.view.panels.MatchesPanel;
import br.com.betmaster.view.panels.WalletPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {

    private JTabbedPane tabbedPane;
    private MatchesPanel matchesPanel;
    private BetsPanel betsPanel;
    private WalletPanel walletPanel;
    private AdminPanel adminPanel; // Painel para funcionalidades de admin
    private JLabel balanceLabel;
    private JButton logoutButton;

    public DashboardView(User user) {
        setLayout(new BorderLayout());
        initComponents(user);
    }

    private void initComponents(User user) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + user.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        balanceLabel = new JLabel("Saldo: R$ 0,00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(welcomeLabel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(balanceLabel);
        rightPanel.add(logoutButton);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        tabbedPane = new JTabbedPane();
        matchesPanel = new MatchesPanel();
        betsPanel = new BetsPanel();
        walletPanel = new WalletPanel();

        tabbedPane.addTab("Partidas", matchesPanel);
        tabbedPane.addTab("Apostas", betsPanel);
        tabbedPane.addTab("Carteira", walletPanel);

        // Adiciona a aba de Admin se o usuário for um administrador
        if (user.getRole() == UserRole.ADMIN) {
            adminPanel = new AdminPanel();
            tabbedPane.addTab("Admin", adminPanel);
        }

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public MatchesPanel getMatchesPanel() {
        return matchesPanel;
    }

    public BetsPanel getBetsPanel() {
        return betsPanel;
    }

    public WalletPanel getWalletPanel() {
        return walletPanel;
    }

    public JLabel getBalanceLabel() {
        return balanceLabel;
    }

    public AdminPanel getAdminPanel() {
        return adminPanel;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }
}
