package br.com.betmaster.model.entity;

import br.com.betmaster.model.enums.TransactionType;

public class Transaction {
    private int id;
    private TransactionType transactionType;
    private Double value;

    public Transaction(TransactionType transactionTypes, Double value) {
        this.transactionType = transactionTypes;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionTypes) {
        this.transactionType = transactionTypes;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
