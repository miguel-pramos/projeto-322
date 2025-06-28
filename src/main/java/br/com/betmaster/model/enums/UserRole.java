package br.com.betmaster.model.enums;

public enum UserRole {
    
    USER("Usuário comum", "Pode criar apostas e gerenciar saldo."),
    ADMIN("Adminstrador", "Tem privilégios de criação de partidas e times. Tem acesso livre a todos os recursos.");

    public final String name;
    public final String description;

    private UserRole(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
