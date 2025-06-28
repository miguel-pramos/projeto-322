package br.com.betmaster.controller;

import br.com.betmaster.model.dao.BetDAO;
import br.com.betmaster.model.dao.MatchDAO;
import br.com.betmaster.model.entity.Bet;
import br.com.betmaster.model.entity.Match;
import br.com.betmaster.model.entity.Team;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.enums.BetStatus;
import br.com.betmaster.view.DashboardView;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardController {

    private DashboardView view;
    private MatchDAO matchDAO;
    private BetDAO betDAO;
    private AdminController adminController;
    private User user;
    private List<Match> matches;

    public DashboardController(DashboardView view, User user) {
        this.view = view;
        this.user = user;
        this.matchDAO = new MatchDAO();
        this.betDAO = new BetDAO();
        initController();
        loadMatches();
        loadBets();

        if (view.getAdminPanel() != null) {
            this.adminController = new AdminController(view.getAdminPanel());
        }
    }

    private void initController() {
        view.getMatchesPanel().getBetButton().addActionListener(e -> placeBet());
    }

    private void placeBet() {
        int selectedRow = view.getMatchesPanel().getMatchesTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma partida para apostar.", "Nenhuma Partida Selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Match selectedMatch = matches.get(selectedRow);

        String[] teams = { selectedMatch.getTeamA().getName(), selectedMatch.getTeamB().getName() };
        String chosenTeamName = (String) JOptionPane.showInputDialog(
                view,
                "Escolha o time em que deseja apostar:",
                "Fazer Aposta",
                JOptionPane.QUESTION_MESSAGE,
                null,
                teams,
                teams[0]);

        if (chosenTeamName != null) {
            String amountString = JOptionPane.showInputDialog(
                    view,
                    "Digite o valor da aposta:",
                    "Fazer Aposta",
                    JOptionPane.QUESTION_MESSAGE);

            if (amountString != null) {
                try {
                    double amount = Double.parseDouble(amountString);
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(view, "O valor da aposta deve ser positivo.", "Valor Inválido",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Team chosenTeam = null;
                    if (chosenTeamName.equals(selectedMatch.getTeamA().getName())) {
                        chosenTeam = selectedMatch.getTeamA();
                    } else {
                        chosenTeam = selectedMatch.getTeamB();
                    }

                    Bet bet = new Bet(user, selectedMatch, amount, chosenTeam, BetStatus.PENDING);

                    betDAO.createBet(bet);

                    JOptionPane.showMessageDialog(view, "Aposta realizada com sucesso!");
                    loadBets(); // Atualiza a tabela de apostas

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Por favor, insira um valor numérico válido.",
                            "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void loadMatches() {
        matches = matchDAO.getAllMatches();
        DefaultTableModel model = view.getMatchesPanel().getTableModel();
        model.setRowCount(0); // Limpa a tabela
        for (Match match : matches) {
            model.addRow(new Object[] {
                    match.getTeamA().getName(),
                    match.getTeamB().getName(),
                    match.getDate(),
                    match.getStatus()
            });
        }
    }

    private void loadBets() {
        List<Bet> bets = betDAO.getAllBets();
        DefaultTableModel model = view.getBetsPanel().getTableModel();
        model.setRowCount(0);
        for (Bet bet : bets) {
            model.addRow(new Object[] {
                    bet.getMatch().getTeamA().getName() + " vs " + bet.getMatch().getTeamB().getName(),
                    bet.getChosenTeam().getName(),
                    bet.getAmount(),
                    bet.getStatus().name
            });
        }
    }
}
