package br.com.betmaster.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private int id;
    private List<Transaction> transactions;
    private Long balance;

    public Wallet() {
        this.balance = 0L;
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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
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
