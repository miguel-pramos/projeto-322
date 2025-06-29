package br.com.betmaster.view.panels;

import javax.swing.*;
import java.awt.*;

public class CreateTeamPanel extends JPanel {

    private JTextField nameField;
    private JTextField attackField;
    private JTextField midfieldField;
    private JTextField defenceField;
    private JButton createButton;

    public CreateTeamPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Attack
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Ataque:"), gbc);
        gbc.gridx = 1;
        attackField = new JTextField(20);
        add(attackField, gbc);

        // Midfield
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Meio-campo:"), gbc);
        gbc.gridx = 1;
        midfieldField = new JTextField(20);
        add(midfieldField, gbc);

        // Defence
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Defesa:"), gbc);
        gbc.gridx = 1;
        defenceField = new JTextField(20);
        add(defenceField, gbc);

        // Button
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        createButton = new JButton("Criar Time");
        add(createButton, gbc);
    }

    public String getTeamName() {
        return nameField.getText();
    }

    public String getAttack() {
        return attackField.getText();
    }

    public String getMidfield() {
        return midfieldField.getText();
    }

    public String getDefence() {
        return defenceField.getText();
    }

    public JButton getCreateButton() {
        return createButton;
    }
}
