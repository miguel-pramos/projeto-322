package br.com.betmaster;

import br.com.betmaster.db.DatabaseManager;
import br.com.betmaster.util.FontLoader;
import br.com.betmaster.view.MainFrame;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.Enumeration;

/**
 * Ponto de entrada principal para a aplicação BetMaster.
 */
public class BetMasterApplication {

    public static void main(String[] args) {
        // Configura o Look and Feel FlatLaf
        FlatDarkLaf.setup();

        // Inicializa o banco de dados
        DatabaseManager.initializeDatabase();

        // Configura a fonte personalizada para toda a aplicação
        setUIFont(new javax.swing.plaf.FontUIResource(FontLoader.getFont(14f)));

        // Inicia a interface gráfica
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }
}
