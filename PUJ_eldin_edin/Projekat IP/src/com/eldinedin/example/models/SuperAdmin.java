package com.eldinedin.example.models;

public class SuperAdmin extends User {
    // Konstruktor bez ID-a
    public SuperAdmin(String username, String password) {
        super(username, password);
    }

    // Konstruktor s ID-om
    public SuperAdmin(int id, String username, String password) {
        super(id, username, password);
    }
}