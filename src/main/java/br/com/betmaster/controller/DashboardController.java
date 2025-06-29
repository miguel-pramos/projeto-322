package br.com.betmaster.controller;

import br.com.betmaster.model.dao.BetDAO;
import br.com.betmaster.model.dao.MatchDAO;
import br.com.betmaster.model.dao.TransactionDAO;
import br.com.betmaster.model.dao.WalletDAO;
import br.com.betmaster.model.entity.Bet;
import br.com.betmaster.model.entity.Match;
import br.com.betmaster.model.entity.Team;
import br.com.betmaster.model.entity.Transaction;
import br.com.betmaster.model.entity.User;
import br.com.betmaster.model.entity.Wallet;
import br.com.betmaster.model.enums.BetStatus;
import br.com.betmaster.model.enums.TransactionType;
import br.com.betmaster.view.DashboardView;
import br.com.betmaster.view.LoginView;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardController {

    private DashboardView view;
    private MatchDAO matchDAO;
    private BetDAO betDAO;
    private TransactionDAO transactionDAO;
    private WalletDAO walletDAO;
    private AdminController adminController;
    private User user;
    private List<Match> matches;
    private JFrame mainFrame;

    public DashboardController(DashboardView view, User user, JFrame mainFrame) {
        this.view = view;
        this.user = user;
        this.mainFrame = mainFrame;
        this.matchDAO = new MatchDAO();
        this.betDAO = new BetDAO();
        this.transactionDAO = new TransactionDAO();
        this.walletDAO = new WalletDAO();
        initController();
        loadMatches();
        loadBets();
        loadWallet();

        if (view.getAdminPanel() != null) {
            this.adminController = new AdminController(view.getAdminPanel(), this);
        }
    }

    private void initController() {
        view.getMatchesPanel().getBetButton().addActionListener(e -> placeBet());
        view.getWalletPanel().getDepositButton().addActionListener(e -> deposit());
        view.getWalletPanel().getWithdrawButton().addActionListener(e -> withdraw());
        view.getLogoutButton().addActionListener(e -> logout());
    }

    private void placeBet() {
        int selectedRow = view.getMatchesPanel().getMatchesTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma partida para apostar.", "Nenhuma Partida Selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Match selectedMatch = matches.get(selectedRow);

        // Preparar informações das odds para exibir
        String teamAInfo = String.format("%s (Odd: %.2f)", selectedMatch.getTeamA().getName(), selectedMatch.getOddA());
        String teamBInfo = String.format("%s (Odd: %.2f)", selectedMatch.getTeamB().getName(), selectedMatch.getOddB());
        String[] teamsWithOdds = { teamAInfo, teamBInfo };

        String chosenTeamInfo = (String) JOptionPane.showInputDialog(
                view,
                "Escolha o time em que deseja apostar:",
                "Fazer Aposta",
                JOptionPane.QUESTION_MESSAGE,
                null,
                teamsWithOdds,
                teamsWithOdds[0]);

        if (chosenTeamInfo != null) {
            // Determinar qual time foi escolhido
            Team chosenTeam;
            double chosenOdd;
            if (chosenTeamInfo.equals(teamAInfo)) {
                chosenTeam = selectedMatch.getTeamA();
                chosenOdd = selectedMatch.getOddA();
            } else {
                chosenTeam = selectedMatch.getTeamB();
                chosenOdd = selectedMatch.getOddB();
            }

            String amountString = JOptionPane.showInputDialog(
                    view,
                    String.format(
                            "Digite o valor da aposta:\n\nTime escolhido: %s\nOdd: %.2f\n\nSe ganhar, você receberá: R$ %.2f por cada R$ 1,00 apostado",
                            chosenTeam.getName(), chosenOdd, chosenOdd),
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

                    if (user.getWallet().getBalance() < amount) {
                        JOptionPane.showMessageDialog(view, "Saldo insuficiente para realizar a aposta.",
                                "Saldo Insuficiente",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Calcular ganho potencial
                    double potentialWin = amount * chosenOdd;

                    // Confirmar aposta com informações detalhadas
                    int confirm = JOptionPane.showConfirmDialog(
                            view,
                            String.format(
                                    "Confirmar aposta?\n\nTime: %s\nValor apostado: R$ %.2f\nOdd: %.2f\nGanho potencial: R$ %.2f",
                                    chosenTeam.getName(), amount, chosenOdd, potentialWin),
                            "Confirmar Aposta",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        Bet bet = new Bet(user, selectedMatch, amount, chosenTeam, BetStatus.PENDING);

                        if (betDAO.createBet(bet)) {
                            JOptionPane.showMessageDialog(view,
                                    String.format("Aposta realizada com sucesso!\n\nGanho potencial: R$ %.2f",
                                            potentialWin));
                            loadBets(); // Atualiza a tabela de apostas
                            loadWallet(); // Atualiza o saldo
                        } else {
                            JOptionPane.showMessageDialog(view,
                                    "Não foi possível realizar a aposta. Verifique seu saldo.",
                                    "Erro na Aposta",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Por favor, insira um valor numérico válido.",
                            "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deposit() {
        String amountString = JOptionPane.showInputDialog(
                view,
                "Digite o valor do depósito:",
                "Depositar",
                JOptionPane.QUESTION_MESSAGE);

        if (amountString != null) {
            try {
                double amount = Double.parseDouble(amountString);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(view, "O valor do depósito deve ser positivo.", "Valor Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (walletDAO.performTransaction(user.getWallet(), amount, TransactionType.DEPOSIT)) {
                    JOptionPane.showMessageDialog(view, "Depósito realizado com sucesso!");
                    loadWallet(); // Atualiza o saldo e transações
                } else {
                    JOptionPane.showMessageDialog(view, "Erro ao realizar depósito. Tente novamente.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Por favor, insira um valor numérico válido.",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void withdraw() {
        String amountString = JOptionPane.showInputDialog(
                view,
                "Digite o valor do saque:",
                "Sacar",
                JOptionPane.QUESTION_MESSAGE);

        if (amountString != null) {
            try {
                double amount = Double.parseDouble(amountString);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(view, "O valor do saque deve ser positivo.", "Valor Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (user.getWallet().getBalance() < amount) {
                    JOptionPane.showMessageDialog(view, "Saldo insuficiente para realizar o saque.",
                            "Saldo Insuficiente", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (walletDAO.performTransaction(user.getWallet(), amount, TransactionType.WITHDRAW)) {
                    JOptionPane.showMessageDialog(view, "Saque realizado com sucesso!");
                    loadWallet(); // Atualiza o saldo e transações
                } else {
                    JOptionPane.showMessageDialog(view, "Erro ao realizar saque. Tente novamente.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Por favor, insira um valor numérico válido.",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshMatches() {
        loadMatches();
    }

    private void loadWallet() {
        // Recarrega a carteira do usuário do banco de dados para garantir dados
        // atualizados
        WalletDAO walletDAO = new WalletDAO();
        Wallet updatedWallet = walletDAO.getWalletByUserId(user.getId());
        if (updatedWallet != null) {
            user.setWallet(updatedWallet);
        }

        // Atualiza o saldo na view
        double balance = user.getWallet().getBalance();
        view.getBalanceLabel().setText(String.format("Saldo: R$ %.2f", balance));
        view.getWalletPanel().getBalanceLabel().setText(String.format("Saldo: R$ %.2f", balance));

        // Carrega as transações
        List<Transaction> transactions = transactionDAO.getTransactionsByWalletId(user.getWallet().getId());
        DefaultTableModel model = (DefaultTableModel) view.getWalletPanel().getTransactionsTable().getModel();
        model.setRowCount(0);
        for (Transaction t : transactions) {
            model.addRow(new Object[] {
                    t.getId(),
                    t.getTransactionType().description,
                    String.format("R$ %.2f", t.getValue())
            });
        }
    }

    private void loadMatches() {
        matches = matchDAO.getAllMatches();
        DefaultTableModel model = view.getMatchesPanel().getTableModel();
        model.setRowCount(0);
        for (Match match : matches) {
            model.addRow(new Object[] {
                    match.getTeamA().getName(),
                    String.format("%.2f", match.getOddA()),
                    match.getTeamB().getName(),
                    String.format("%.2f", match.getOddB()),
                    match.getDate(),
                    match.getStatus().name
            });
        }
    }

    private void loadBets() {
        List<Bet> bets = betDAO.getBetsByUserId(user.getId());
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

    private void logout() {
        int option = JOptionPane.showConfirmDialog(
                view,
                "Tem certeza que deseja fazer logout?",
                "Confirmar Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            mainFrame.getContentPane().removeAll();
            LoginView loginView = new LoginView();
            new LoginController(loginView, mainFrame);
            mainFrame.add(loginView);
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }
}
