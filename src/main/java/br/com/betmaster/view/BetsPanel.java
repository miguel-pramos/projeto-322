package br.com.betmaster.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BetsPanel extends JPanel {

    private JTable betsTable;
    private DefaultTableModel tableModel;

    public BetsPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        String[] columnNames = { "Partida", "Time Apostado", "Valor", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        betsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(betsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
