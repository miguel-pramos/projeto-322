package br.com.betmaster.model.entity;

public class Team {
    private int id;
    private String name;
    private int attack;
    private int defence;


    public Team(int attack, int defence, String name) {
        this.attack = attack;
        this.defence = defence;
        this.name = name;
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
