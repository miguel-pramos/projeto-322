package br.com.betmaster.model.enums;

public enum BetStatus {
    
    FAILED("Falhou.","A sua aposta falhou."),
    SUCESS("Sucesso.","A sua aposta foi bem sucedida.");

    String name;
    String description;

    private BetStatus(String name, String description){
        this.name = name;
        this.description = description;
    }

}
