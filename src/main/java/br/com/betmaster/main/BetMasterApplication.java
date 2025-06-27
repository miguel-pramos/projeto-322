package br.com.betmaster.main;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Ponto de entrada principal para a aplicação BetMaster.
 */
public class BetMasterApplication {

    public static void main(String[] args) {
        // Inicializa o banco de dados
        DatabaseManager.initializeDatabase();

        // Define o Look and Feel do sistema para uma aparência nativa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Garante que a criação da GUI seja feita na Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
