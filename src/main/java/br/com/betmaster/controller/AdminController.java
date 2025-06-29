package br.com.betmaster.controller;

import br.com.betmaster.model.dao.MatchDAO;
import br.com.betmaster.model.dao.TeamDAO;
import br.com.betmaster.model.entity.Match;
import br.com.betmaster.model.entity.Team;
import br.com.betmaster.view.panels.AdminPanel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminController {

    private AdminPanel view;
    private TeamDAO teamDAO;
    private MatchDAO matchDAO;
    private DashboardController dashboardController;

    public AdminController(AdminPanel view) {
        this.view = view;
        this.teamDAO = new TeamDAO();
        this.matchDAO = new MatchDAO();
        initController();
    }

    public AdminController(AdminPanel view, DashboardController dashboardController) {
        this.view = view;
        this.teamDAO = new TeamDAO();
        this.matchDAO = new MatchDAO();
        this.dashboardController = dashboardController;
        initController();
    }

    private void initController() {
        view.getCreateTeamPanel().getCreateButton().addActionListener(e -> createTeam());
        view.getCreateMatchPanel().getCreateButton().addActionListener(e -> createMatch());
        loadTeamsIntoComboBoxes();
    }

    private void loadTeamsIntoComboBoxes() {
        List<Team> teams = teamDAO.getAllTeams();

        // O modelo agora armazena objetos Team, não apenas os nomes
        DefaultComboBoxModel<Team> modelA = new DefaultComboBoxModel<>(teams.toArray(new Team[0]));
        DefaultComboBoxModel<Team> modelB = new DefaultComboBoxModel<>(teams.toArray(new Team[0]));

        view.getCreateMatchPanel().getTeamAComboBox().setModel(modelA);
        view.getCreateMatchPanel().getTeamBComboBox().setModel(modelB);

        ListCellRenderer<Object> renderer = new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Team) {
                    setText(((Team) value).getName());
                }
                return this;
            }
        };

        view.getCreateMatchPanel().getTeamAComboBox().setRenderer(renderer);
        view.getCreateMatchPanel().getTeamBComboBox().setRenderer(renderer);
    }

    private void createTeam() {
        try {
            String name = view.getCreateTeamPanel().getTeamName();
            int attack = Integer.parseInt(view.getCreateTeamPanel().getAttack());
            int midfield = Integer.parseInt(view.getCreateTeamPanel().getMidfield());
            int defence = Integer.parseInt(view.getCreateTeamPanel().getDefence());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "O nome do time não pode estar vazio.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Team team = new Team(name, attack, defence, midfield);
            if (teamDAO.createTeam(team)) {
                JOptionPane.showMessageDialog(view, "Time criado com sucesso!");
                loadTeamsIntoComboBoxes(); // Atualiza a lista de times
            } else {
                JOptionPane.showMessageDialog(view, "Erro ao criar time.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Ataque, meio-campo e defesa devem ser números inteiros.",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createMatch() {
        Team teamA = (Team) view.getCreateMatchPanel().getTeamAComboBox().getSelectedItem();
        Team teamB = (Team) view.getCreateMatchPanel().getTeamBComboBox().getSelectedItem();
        String dateStr = view.getCreateMatchPanel().getDate();

        if (teamA == null || teamB == null) {
            JOptionPane.showMessageDialog(view, "Selecione dois times.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (teamA.equals(teamB)) {
            JOptionPane.showMessageDialog(view, "Os times devem ser diferentes.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = formatter.parse(dateStr);
            Match match = new Match(teamA, teamB, date);
            if (matchDAO.createMatch(match)) {
                JOptionPane.showMessageDialog(view, "Partida criada com sucesso!");
                // Notifica o DashboardController para atualizar a tabela de partidas
                if (dashboardController != null) {
                    dashboardController.refreshMatches();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Erro ao criar partida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Formato de data inválido. Use dd/MM/yyyy HH:mm.", "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
