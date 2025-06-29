package br.com.betmaster.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    private static Font customFont;

    static {
        try {
            // Carrega a fonte do caminho de recursos
            InputStream is = FontLoader.class.getResourceAsStream("/static/Jost-VariableFont_wght.ttf");
            if (is == null) {
                throw new IOException("Arquivo da fonte não encontrado em resources/static");
            }
            // Cria a fonte e define um tamanho padrão
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
        } catch (IOException | FontFormatException e) {
            System.err.println("Erro ao carregar a fonte personalizada. Usando fonte padrão.");
            e.printStackTrace();
            // Define uma fonte de fallback em caso de erro
            customFont = new Font("SansSerif", Font.PLAIN, 12);
        }
    }

    public static Font getFont() {
        return customFont;
    }

    public static Font getFont(float size) {
        return customFont.deriveFont(size);
    }

    public static Font getFont(int style, float size) {
        return customFont.deriveFont(style, size);
    }
}
