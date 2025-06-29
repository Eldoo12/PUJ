package com.eldinedin.example.models;

public abstract class User {
    protected int id;
    protected String username;
    protected String password;

    // Konstruktor bez ID-a
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Konstruktor s ID-om, koristi se kod dohvaćanja iz baze
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getter i Setter metode
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}