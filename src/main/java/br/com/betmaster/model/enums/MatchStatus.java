package br.com.betmaster.model.enums;

public enum MatchStatus {

    ONGOING("Pendente", "O jogo está ocorrendo."),
    ENDED("Finalizado", "O jogo acabou."),
    SCHEDULED("Marcado", "O jogo está marcado."),
    CANCELLED("Cancelado", "O jogo foi cancelado.");

    public final String name;
    public final String description;

    private MatchStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
