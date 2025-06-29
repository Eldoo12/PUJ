package com.eldinedin.example.models;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {
    private List<Task> tasks;

    // Konstruktor bez ID-a
    public Employee(String username, String password) {
        super(username, password);
        this.tasks = new ArrayList<>();
    }

    // Konstruktor s ID-om
    public Employee(int id, String username, String password) {
        super(id, username, password);
        this.tasks = new ArrayList<>();
    }

    // Metode za upravljanje zadacima
    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}