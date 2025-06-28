package br.com.betmaster.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MatchesPanel extends JPanel {

    private JTable matchesTable;
    private DefaultTableModel tableModel;
    private JButton betButton;

    public MatchesPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        String[] columnNames = { "Time A", "Time B", "Data", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        matchesTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(matchesTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        betButton = new JButton("Apostar");
        buttonPanel.add(betButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getMatchesTable() {
        return matchesTable;
    }

    public JButton getBetButton() {
        return betButton;
    }
}
