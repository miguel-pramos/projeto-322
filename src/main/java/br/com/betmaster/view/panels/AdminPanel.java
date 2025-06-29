package br.com.betmaster.view.panels;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private CreateTeamPanel createTeamPanel;
    private CreateMatchPanel createMatchPanel;

    public AdminPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        createTeamPanel = new CreateTeamPanel();
        createMatchPanel = new CreateMatchPanel();

        tabbedPane.addTab("Criar Time", createTeamPanel);
        tabbedPane.addTab("Criar Partida", createMatchPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public CreateTeamPanel getCreateTeamPanel() {
        return createTeamPanel;
    }

    public CreateMatchPanel getCreateMatchPanel() {
        return createMatchPanel;
    }
}
