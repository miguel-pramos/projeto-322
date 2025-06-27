package br.com.betmaster.model.entity;

public class Team {
    private int id;
    private String name;
    private float marketValue;


    public Team(String name, float marketValue) {
        this.name = name;
        this.marketValue = marketValue;
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

    public float getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(float marketValue) {
        this.marketValue = marketValue;
    }
    
}
