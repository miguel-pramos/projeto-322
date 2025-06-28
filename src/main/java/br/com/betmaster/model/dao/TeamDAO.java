package br.com.betmaster.model.dao;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.model.entity.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    public boolean createTeam(Team team) {
        String sql = "INSERT INTO teams(name, attack, defence, midfield) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, team.getName());
            pstmt.setInt(2, team.getAttack());
            pstmt.setInt(3, team.getDefence());
            pstmt.setInt(4, team.getMidfield());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao criar time: " + e.getMessage());
            return false;
        }
    }

    public Team getTeamById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        Team team = null;
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                team = new Team(rs.getString("name"), rs.getInt("attack"), rs.getInt("defence"), rs.getInt("midfield"));
                team.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar time: " + e.getMessage());
        }
        return team;
    }

    public List<Team> getAllTeams() {
        String sql = "SELECT * FROM teams";
        List<Team> teams = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Team team = new Team(rs.getString("name"), rs.getInt("attack"), rs.getInt("defence"),
                        rs.getInt("midfield"));
                team.setId(rs.getInt("id"));
                teams.add(team);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os times: " + e.getMessage());
        }
        return teams;
    }
}
