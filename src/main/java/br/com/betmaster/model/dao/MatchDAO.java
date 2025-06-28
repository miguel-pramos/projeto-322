package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Match;
import br.com.betmaster.model.entity.Team;
import br.com.betmaster.model.enums.MatchStatus;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean createMatch(Match match) {
        String sql = "INSERT INTO matches(status, date, team1_id, team2_id) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, match.getStatus().name());
            pstmt.setString(2, dateFormat.format(match.getDate()));
            pstmt.setInt(3, match.getTeamA().getId());
            pstmt.setInt(4, match.getTeamB().getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao criar partida: " + e.getMessage());
            return false;
        }
    }

    public Match getMatchById(int id) {
        String sql = "SELECT * FROM matches WHERE id = ?";
        Match match = null;
        TeamDAO teamDAO = new TeamDAO();
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Team team1 = teamDAO.getTeamById(rs.getInt("team1_id"));
                Team team2 = teamDAO.getTeamById(rs.getInt("team2_id"));
                match = new Match(
                        team1,
                        team2,
                        MatchStatus.valueOf(rs.getString("status")),
                        dateFormat.parse(rs.getString("date")));
                match.setId(rs.getInt("id"));
            }
        } catch (SQLException | ParseException e) {
            System.err.println("Erro ao buscar partida: " + e.getMessage());
        }
        return match;
    }

    public List<Match> getAllMatches() {
        String sql = "SELECT * FROM matches";
        List<Match> matches = new ArrayList<>();
        TeamDAO teamDAO = new TeamDAO();
        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Team team1 = teamDAO.getTeamById(rs.getInt("team1_id"));
                Team team2 = teamDAO.getTeamById(rs.getInt("team2_id"));
                Match match = new Match(
                        team1,
                        team2,
                        MatchStatus.valueOf(rs.getString("status")),
                        dateFormat.parse(rs.getString("date")));
                match.setId(rs.getInt("id"));
                matches.add(match);
            }
        } catch (SQLException | ParseException e) {
            System.err.println("Erro ao buscar todas as partidas: " + e.getMessage());
        }
        return matches;
    }
}
