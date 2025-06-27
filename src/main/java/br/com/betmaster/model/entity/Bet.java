package br.com.betmaster.model.entity;

import java.util.Date;

public class Bet {

    private int id;
    private Match match;
    private User user;
    private double amount;
    private Team chosenTeam;
    private Date betDate;

    public Bet(Match match, User user, double amount, Team chosenTeam) {
        this.match = match;
        this.user = user;
        this.amount = amount;
        this.chosenTeam = chosenTeam;
        this.betDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Team getChosenTeam() {
        return chosenTeam;
    }

    public void setChosenTeam(Team chosenTeam) {
        this.chosenTeam = chosenTeam;
    }

    public Date getBetDate() {
        return betDate;
    }

    public void setBetDate(Date betDate) {
        this.betDate = betDate;
    }
}
