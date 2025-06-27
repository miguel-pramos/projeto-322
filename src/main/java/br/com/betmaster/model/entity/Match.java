package br.com.betmaster.model.entity;

import java.util.Date;
import java.util.List;

import br.com.betmaster.model.enums.MatchStatus;

public class Match {

    private int id;
    private String name;
    private List<Team> teams;
    private MatchStatus status;
    private Date date;

    public Match(String name, List<Team> teams, MatchStatus status, Date date) {
        this.name = name;
        this.teams = teams;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

}
