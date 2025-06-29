package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Bet;
import br.com.betmaster.model.entity.Match;
import br.com.betmaster.model.entity.Team;
import br.com.betmaster.model.entity.Transaction;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.entity.Wallet;
import br.com.betmaster.model.enums.BetStatus;
import br.com.betmaster.model.enums.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BetDAO {

    public List<Bet> getAllBets() {
        String sql = "SELECT * FROM bets";
        List<Bet> bets = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserDAO userDAO = new UserDAO();
                MatchDAO matchDAO = new MatchDAO();
                TeamDAO teamDAO = new TeamDAO();

                User user = userDAO.getUserById(rs.getInt("user_id"));
                Match match = matchDAO.getMatchById(rs.getInt("match_id"));
                Team chosenTeam = teamDAO.getTeamById(rs.getInt("chosen_team_id"));

                Bet bet = new Bet(
                        user,
                        match,
                        rs.getDouble("amount"),
                        chosenTeam,
                        BetStatus.valueOf(rs.getString("bet_status")));
                bet.setId(rs.getInt("id"));
                bets.add(bet);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar apostas: " + e.getMessage());
        }
        return bets;
    }

    public List<Bet> getBetsByUserId(int userId) {
        String sql = "SELECT * FROM bets WHERE user_id = ?";
        List<Bet> bets = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        MatchDAO matchDAO = new MatchDAO();
        TeamDAO teamDAO = new TeamDAO();

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = userDAO.getUserById(userId);
                Match match = matchDAO.getMatchById(rs.getInt("match_id"));
                Team team = teamDAO.getTeamById(rs.getInt("chosen_team_id"));

                bets.add(new Bet(user,
                        match,
                        rs.getInt("amount"),
                        team,
                        BetStatus.valueOf(rs.getString("bet_status"))));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar apostas: " + e.getMessage());
        }
        return bets;

    }

    public boolean createBet(Bet bet) {
        String sql = "INSERT INTO bets(match_id, user_id, chosen_team_id, amount, bet_status) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            // Desabilitar auto-commit para controlar a transação
            conn.setAutoCommit(false);

            // 1. Debitar da carteira e criar a transação
            User user = bet.getUser();
            Wallet wallet = user.getWallet();

            if (wallet.getBalance() < bet.getAmount()) {
                System.err.println("Saldo insuficiente.");
                conn.rollback(); // Desfaz a transação
                return false;
            }

            Transaction transaction = new Transaction(TransactionType.BET_PLACED, (long) bet.getAmount());
            wallet.addTransaction(transaction);

            // 2. Salvar a aposta
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, bet.getMatch().getId());
                pstmt.setInt(2, user.getId());
                pstmt.setInt(3, bet.getChosenTeam().getId());
                pstmt.setDouble(4, bet.getAmount());
                pstmt.setString(5, bet.getStatus().name());
                pstmt.executeUpdate();
            }

            // 3. Atualizar a carteira e salvar a transação no banco
            WalletDAO walletDAO = new WalletDAO();
            walletDAO.updateWallet(wallet, conn);

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.createTransaction(transaction, wallet.getId(), conn);

            // Se tudo deu certo, comitar a transação
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao criar aposta: " + e.getMessage());
            return false;
        }
    }

}
