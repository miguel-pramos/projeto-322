package br.com.betmaster;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.view.MainFrame;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.SwingUtilities;

/**
 * Ponto de entrada principal para a aplicação BetMaster.
 */
public class BetMasterApplication {

    public static void main(String[] args) {
        // Inicializa o banco de dados
        DatabaseManager.initializeDatabase();

        // Define o Look and Feel do sistema para uma aparência nativa
        try {
            FlatLightLaf.setup();
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
