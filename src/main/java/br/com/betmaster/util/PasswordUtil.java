package br.com.betmaster.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilitário para lidar com hashing e verificação de senhas.
 */
public class PasswordUtil {

    /**
     * Gera um hash para uma senha de texto plano.
     *
     * @param plainPassword A senha a ser hasheada.
     * @return A senha hasheada.
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Verifica se uma senha de texto plano corresponde a uma senha hasheada.
     *
     * @param plainPassword  A senha de texto plano.
     * @param hashedPassword A senha hasheada.
     * @return true se as senhas corresponderem, false caso contrário.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
