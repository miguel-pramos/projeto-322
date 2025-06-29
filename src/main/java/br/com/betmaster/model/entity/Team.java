package br.com.betmaster.model.entity;

public class Team {
    private int id;
    private String name;
    private int attack;
    private int defence;
    private int midfield;
    private int teamstrenght;

    public Team(String name, int attack, int defence, int midfield) {
        this.attack = attack;
        this.defence = defence;
        this.midfield = midfield;
        this.name = name;
        this.teamstrenght = attack + defence + midfield;
    }
    public int getTeamStrenght(){
        return teamstrenght;
    }
    public int getMidfield() {
        return midfield;
    }

    public void setMidfield(int midfield) {
        this.midfield = midfield;
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

}
