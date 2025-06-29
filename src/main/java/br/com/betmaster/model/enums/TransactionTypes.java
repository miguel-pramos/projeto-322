package br.com.betmaster.model.enums;

public enum TransactionTypes {
    
    DEPOSIT("Depósito", "Depósito de um valor ao saldo."),
    WITHDRAW("Saque", "Saque de um valor do saldo.");    

    public final String name;
    public final String description;

    private TransactionTypes(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
