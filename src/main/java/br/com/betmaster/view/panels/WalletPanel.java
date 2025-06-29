package br.com.betmaster.view.panels;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WalletPanel extends JPanel {

    private JLabel balanceLabel;
    private JTable transactionsTable;
    private JButton depositButton;
    private JButton withdrawButton;

    public WalletPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new TitledBorder("Minha Carteira"));

        // Painel do Saldo
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        balanceLabel = new JLabel("Saldo: R$ 0,00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balancePanel.add(balanceLabel);

        // Tabela de Transações
        String[] columnNames = { "ID", "Descrição", "Valor" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        transactionsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionsTable);

        // Painel de Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        depositButton = new JButton("Depositar");
        withdrawButton = new JButton("Sacar");
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);

        add(balancePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JLabel getBalanceLabel() {
        return balanceLabel;
    }

    public JTable getTransactionsTable() {
        return transactionsTable;
    }

    public JButton getDepositButton() {
        return depositButton;
    }

    public JButton getWithdrawButton() {
        return withdrawButton;
    }
}
