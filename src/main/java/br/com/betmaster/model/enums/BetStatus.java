package br.com.betmaster.model.enums;

public enum BetStatus {

    PENDING("Pendente", "Sua aposta está pendente."),
    FAILED("Falhou.", "A sua aposta falhou."),
    SUCESS("Sucesso.", "A sua aposta foi bem sucedida.");

    public final String name;
    public final String description;

    private BetStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
