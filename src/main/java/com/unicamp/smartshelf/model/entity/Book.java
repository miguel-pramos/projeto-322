package com.unicamp.smartshelf.model.entity;

public class Book {

    private Long id;
    private String description;
    private boolean read;

    public Book() {
    }

    public Book(String description, boolean read) {
        this.description = description;
        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
