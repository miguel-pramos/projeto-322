package br.com.betmaster.db;

import br.com.betmaster.model.dao.UserDAO;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.enums.UserRole;

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
                " password TEXT NOT NULL," +
                " role TEXT NOT NULL);";

        String createWalletTableSQL = "CREATE TABLE IF NOT EXISTS wallets (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " user_id INTEGER NOT NULL UNIQUE," +
                " balance REAL NOT NULL," +
                " FOREIGN KEY(user_id) REFERENCES users(id));";

        String createTransactionTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " wallet_id INTEGER NOT NULL," +
                " type TEXT NOT NULL," +
                " value REAL NOT NULL," +
                " FOREIGN KEY(wallet_id) REFERENCES wallets(id));";

        String createTeamTableSQL = "CREATE TABLE IF NOT EXISTS teams (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL UNIQUE," +
                " attack INTEGER NOT NULL," +
                " midfield INTEGER NOT NULL," +
                " defence INTEGER NOT NULL);";

        String createMatchTableSQL = "CREATE TABLE IF NOT EXISTS matches (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " status TEXT NOT NULL," +
                " date TEXT NOT NULL," +
                " team1_id INTEGER," +
                " team2_id INTEGER," +
                " FOREIGN KEY(team1_id) REFERENCES teams(id)," +
                " FOREIGN KEY(team2_id) REFERENCES teams(id));";

        String createBetTableSQL = "CREATE TABLE IF NOT EXISTS bets (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " match_id INTEGER," +
                " user_id INTEGER," +
                " chosen_team_id INTEGER," +
                " amount REAL NOT NULL," +
                " bet_status TEXT NOT NULL," +
                " FOREIGN KEY(chosen_team_id) REFERENCES teams(id)," +
                " FOREIGN KEY(match_id) REFERENCES matches(id)," +
                " FOREIGN KEY(user_id) REFERENCES users(id));";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            // Cria as tabelas
            stmt.execute(createUserTableSQL);
            stmt.execute(createWalletTableSQL);
            stmt.execute(createTransactionTableSQL);
            stmt.execute(createTeamTableSQL);
            stmt.execute(createMatchTableSQL);
            stmt.execute(createBetTableSQL);

            // Cria ambiente de desenvolvimento
            createDevEnvironment();

            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }

    private static void createDevEnvironment() {
        UserDAO userDAO = new UserDAO();
        if (userDAO.getUserByUsername("admin") == null) {
            User admin = new User("admin", "admin", UserRole.ADMIN);
            userDAO.createUser(admin);
            System.out.println("Usuário 'admin' de desenvolvimento criado.");
        }
    }
}
