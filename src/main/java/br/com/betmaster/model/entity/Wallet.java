package br.com.betmaster.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private int id;
    private List<Transaction> transactions;
    private Double balance;

    public Wallet() {
        this.balance = 0D;
        this.transactions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        if (transaction.getTransactionType().isCredit) {
            this.balance += transaction.getValue();
        } else {
            this.balance -= transaction.getValue();
        }
    }
}
