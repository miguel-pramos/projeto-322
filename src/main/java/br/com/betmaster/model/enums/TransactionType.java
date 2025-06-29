package br.com.betmaster.model.enums;

public enum TransactionType {
    BET_PLACED("Aposta Realizada", "Valor apostado em uma bet.", false),
    BET_WON("Aposta Ganha", "Valor ganho em uma aposta vencedora.", true),
    BET_LOST("Aposta Perdida", "Valor perdido em uma aposta perdedora.", false),
    BET_CANCELLED("Aposta Cancelada", "Valor devolvido de uma aposta cancelada.", true),
    DEPOSIT("Depósito", "Depósito de um valor ao saldo.", true),
    WITHDRAW("Saque", "Saque de um valor do saldo.", false);

    public final String name;
    public final String description;
    public final boolean isCredit;

    private TransactionType(String name, String description, boolean isCredit) {
        this.name = name;
        this.description = description;
        this.isCredit = isCredit;
    }
}
