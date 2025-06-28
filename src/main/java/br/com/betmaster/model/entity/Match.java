package br.com.betmaster.model.entity;

import java.util.Date;

import br.com.betmaster.model.enums.MatchStatus;

public class Match {

    private int id;
    private Team teamA, teamB;
    private MatchStatus status;
    private Date date;

    public Match(Team teamA, Team teamB, Date date) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.status = MatchStatus.SCHEDULED;
    }

    public Match(Team team1, Team team2, MatchStatus status, Date date) {
        this.teamA = team1;
        this.teamB = team2;
        this.status = status;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team team1) {
        this.teamA = team1;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team team2) {
        this.teamB = team2;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

}
