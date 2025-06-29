package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.entity.Wallet;
import br.com.betmaster.model.enums.UserRole;
import br.com.betmaster.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável pelas operações de CRUD para a entidade User.
 */
public class UserDAO {

    /**
     * Cria um novo usuário no banco de dados com senha hasheada.
     *
     * @param user o usuário a ser criado.
     * @return true se o usuário foi criado com sucesso, false caso contrário.
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, user.getRole().name());
            pstmt.executeUpdate();

            // Busca o ID do usuário criado
            String selectSql = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                selectPstmt.setString(1, user.getUsername());
                ResultSet rs = selectPstmt.executeQuery();
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    // Cria a carteira para o usuário após criá-lo
                    WalletDAO walletDAO = new WalletDAO();
                    walletDAO.createWallet(user.getId());
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um usuário pelo nome de usuário e carrega sua carteira.
     *
     * @param username o nome de usuário a ser buscado.
     * @return o objeto User se encontrado, null caso contrário.
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT u.id as user_id, u.username, u.password, u.role, w.id as wallet_id, w.balance " +
                "FROM users u LEFT JOIN wallets w ON u.id = w.user_id WHERE u.username = ?";
        User user = null;

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role")));
                user.setId(rs.getInt("user_id"));

                // Verifica se a carteira existe antes de criá-la
                int walletId = rs.getInt("wallet_id");
                if (!rs.wasNull()) {
                    Wallet wallet = new Wallet();
                    wallet.setId(walletId);
                    wallet.setBalance(rs.getDouble("balance"));
                    user.setWallet(wallet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return user;
    }

    /**
     * Valida a senha de um usuário.
     *
     * @param username      o nome de usuário.
     * @param plainPassword a senha em texto plano.
     * @return o objeto User se a senha for válida, null caso contrário.
     */
    public User validatePassword(String username, String plainPassword) {
        User user = getUserByUsername(username);
        if (user != null && PasswordUtil.checkPassword(plainPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;
        WalletDAO walletDAO = new WalletDAO();

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role")));
                user.setId(rs.getInt("id"));
                user.setWallet(walletDAO.getWalletByUserId(user.getId()));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return user;
    }
}
