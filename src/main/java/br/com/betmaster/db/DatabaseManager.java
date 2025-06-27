package br.com.betmaster.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gerencia a conexão com o banco de dados SQLite e a inicialização do schema.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:db.sqlite";

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return um objeto de Conexão.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Inicializa o banco de dados, criando as tabelas necessárias se elas não
     * existirem.
     */
    public static void initializeDatabase() {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " username TEXT NOT NULL UNIQUE," +
                " password TEXT NOT NULL);";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            // Cria a tabela de usuários
            stmt.execute(createUserTableSQL);
            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}
