package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Transaction;
import br.com.betmaster.model.enums.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public Transaction createTransaction(Transaction transaction, int walletId) {
        String sql = "INSERT INTO transactions(wallet_id, type, value) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, walletId);
            pstmt.setString(2, transaction.getTransactionType().name());
            pstmt.setLong(3, transaction.getValue());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                transaction.setId(rs.getInt(1));
            }
            return transaction;
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
                        rs.getLong("value"));
                transaction.setId(rs.getInt("id"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar transações por wallet_id: " + e.getMessage());
        }
        return transactions;
    }
}
