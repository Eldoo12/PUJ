package com.eldinedin.example.models;

public class Task {
    private int taskId;
    private String description;
    private boolean isCompleted;

    // Konstruktor bez ID-a za kreiranje novih zadataka
    public Task(String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    // Konstruktor s ID-om za dohvaćanje zadataka iz baze
    public Task(int taskId, String description, boolean isCompleted) {
        this.taskId = taskId;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    // Getter i Setter metode
    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}