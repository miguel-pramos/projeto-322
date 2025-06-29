package br.com.betmaster.model.enums;

public enum TransactionTypes {
    BET_PLACED("Aposta Realizada", "Valor apostado em uma bet."),
    BET_WON("Aposta Ganha", "Valor ganho em uma aposta vencedora."),
    BET_LOST("Aposta Perdida", "Valor perdido em uma aposta perdedora."),
    BET_CANCELLED("Aposta Cancelada", "Valor devolvido de uma aposta cancelada."),
    DEPOSIT("Depósito", "Depósito de um valor ao saldo."),
    WITHDRAW("Saque", "Saque de um valor do saldo.");

    public final String name;
    public final String description;

    private TransactionTypes(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
