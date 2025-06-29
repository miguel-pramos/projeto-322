package br.com.betmaster.view.panels;

import javax.swing.*;

import br.com.betmaster.model.entity.Team;

import java.awt.*;

public class CreateMatchPanel extends JPanel {

    private JComboBox<Team> teamAComboBox;
    private JComboBox<Team> teamBComboBox;
    private JTextField dateField;
    private JButton createButton;

    public CreateMatchPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Team A
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Time A:"), gbc);
        gbc.gridx = 1;
        teamAComboBox = new JComboBox<>();
        add(teamAComboBox, gbc);

        // Team B
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Time B:"), gbc);
        gbc.gridx = 1;
        teamBComboBox = new JComboBox<>();
        add(teamBComboBox, gbc);

        // Date
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Data (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(20);
        add(dateField, gbc);

        // Button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        createButton = new JButton("Criar Partida");
        add(createButton, gbc);
    }

    public JComboBox<Team> getTeamAComboBox() {
        return teamAComboBox;
    }

    public JComboBox<Team> getTeamBComboBox() {
        return teamBComboBox;
    }

    public String getDate() {
        return dateField.getText();
    }

    public JButton getCreateButton() {
        return createButton;
    }
}
