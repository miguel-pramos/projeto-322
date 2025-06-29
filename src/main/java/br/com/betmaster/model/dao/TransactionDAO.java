package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Transaction;
import br.com.betmaster.model.enums.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public Transaction createTransaction(Transaction transaction, int walletId, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions(wallet_id, type, value) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, walletId);
            pstmt.setString(2, transaction.getTransactionType().name());
            pstmt.setDouble(3, transaction.getValue());
            pstmt.executeUpdate();

            // Busca o ID da transação criada
            String selectSql = "SELECT id FROM transactions WHERE wallet_id = ? AND type = ? AND value = ? ORDER BY id DESC LIMIT 1";
            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                selectPstmt.setInt(1, walletId);
                selectPstmt.setString(2, transaction.getTransactionType().name());
                selectPstmt.setDouble(3, transaction.getValue());
                ResultSet rs = selectPstmt.executeQuery();
                if (rs.next()) {
                    transaction.setId(rs.getInt("id"));
                }
            }
            return transaction;
        }
    }

    public Transaction createTransaction(Transaction transaction, int walletId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            return createTransaction(transaction, walletId, conn);
        } catch (SQLException e) {
            System.err.println("Erro ao criar transação: " + e.getMessage());
            return null;
        }
    }

    public List<Transaction> getTransactionsByWalletId(int walletId) {
        String sql = "SELECT * FROM transactions WHERE wallet_id = ? ORDER BY id DESC";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, walletId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        TransactionType.valueOf(rs.getString("type")),
                        rs.getDouble("value"));
                transaction.setId(rs.getInt("id"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar transações por wallet_id: " + e.getMessage());
        }
        return transactions;
    }
}
