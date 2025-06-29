package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Transaction;
import br.com.betmaster.model.entity.Wallet;
import br.com.betmaster.model.enums.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDAO {

    public Wallet createWallet(int userId) {
        String sql = "INSERT INTO wallets(user_id, balance) VALUES(?, ?)";
        Wallet wallet = new Wallet();
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDouble(2, 0.0);
            pstmt.executeUpdate();

            // Busca o ID da carteira criada
            String selectSql = "SELECT id FROM wallets WHERE user_id = ?";
            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                selectPstmt.setInt(1, userId);
                ResultSet rs = selectPstmt.executeQuery();
                if (rs.next()) {
                    wallet.setId(rs.getInt("id"));
                }
            }
            return wallet;
        } catch (SQLException e) {
            System.err.println("Erro ao criar carteira: " + e.getMessage());
            return null;
        }
    }

    public Wallet getWalletByUserId(int userId) {
        String sql = "SELECT * FROM wallets WHERE user_id = ?";
        Wallet wallet = null;
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                wallet = new Wallet();
                wallet.setId(rs.getInt("id"));
                wallet.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carteira por user_id: " + e.getMessage());
        }
        return wallet;
    }

    public void updateWallet(Wallet wallet, Connection conn) throws SQLException {
        String sql = "UPDATE wallets SET balance = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, wallet.getBalance());
            pstmt.setInt(2, wallet.getId());
            pstmt.executeUpdate();
        }
    }

    public void updateWallet(Wallet wallet) {
        try (Connection conn = DatabaseManager.getConnection()) {
            updateWallet(wallet, conn);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carteira: " + e.getMessage());
        }
    }

    public boolean performTransaction(Wallet wallet, double amount, TransactionType type) {
        // Validação prévia
        double currentBalance = wallet.getBalance();
        double newBalance = currentBalance + (type.isCredit ? amount : -amount);

        if (newBalance < 0) {
            System.err.println("Tentativa de saque maior que o saldo.");
            return false;
        }

        try {
            // 1. Atualiza o saldo da carteira
            wallet.setBalance(newBalance);
            updateWallet(wallet);

            // 2. Cria o registro da transação
            Transaction transaction = new Transaction(type, amount);
            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.createTransaction(transaction, wallet.getId());

            return true;
        } catch (Exception e) {
            // Em caso de erro, reverte o saldo na memória
            wallet.setBalance(currentBalance);
            System.err.println("Erro na transação da carteira: " + e.getMessage());
            return false;
        }
    }
}
