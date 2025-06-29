package com.eldinedin.example.models;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    private List<Employee> employees;

    // Konstruktor bez ID-a
    public Manager(String username, String password) {
        super(username, password);
        this.employees = new ArrayList<>();
    }

    // Konstruktor s ID-om
    public Manager(int id, String username, String password) {
        super(id, username, password);
        this.employees = new ArrayList<>();
    }

    // Metode za upravljanje zaposlenicima
    public void assignTask(Employee employee, Task task) {
        employee.addTask(task);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}