package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WalletDAO {

    public Wallet createWallet(int userId) {
        String sql = "INSERT INTO wallets(user_id, balance) VALUES(?, ?)";
        Wallet wallet = new Wallet();
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setLong(2, 0L);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                wallet.setId(rs.getInt(1));
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
                wallet.setBalance(rs.getLong("balance"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carteira por user_id: " + e.getMessage());
        }
        return wallet;
    }

    public void updateWallet(Wallet wallet, Connection conn) throws SQLException {
        String sql = "UPDATE wallets SET balance = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, wallet.getBalance());
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
}
