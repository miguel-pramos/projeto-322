package br.com.betmaster.model.entity;

import br.com.betmaster.model.enums.TransactionType;

public class Transaction {
    private int id;
    private TransactionType transactionType;
    private Long value;

    public Transaction(TransactionType transactionTypes, Long value) {
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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
