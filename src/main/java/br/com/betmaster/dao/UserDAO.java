package br.com.betmaster.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.User;
import org.mindrot.jbcrypt.BCrypt;

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
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username o nome de usuário a ser buscado.
     * @return o objeto User se encontrado, null caso contrário.
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
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
     * @return true se a senha for válida, false caso contrário.
     */
    public boolean validatePassword(String username, String plainPassword) {
        User user = getUserByUsername(username);
        if (user != null) {
            return BCrypt.checkpw(plainPassword, user.getPassword());
        }
        return false;
    }
}
