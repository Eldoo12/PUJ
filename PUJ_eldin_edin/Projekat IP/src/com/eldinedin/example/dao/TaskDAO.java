package com.eldinedin.example.dao;

import com.eldinedin.example.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Task> getAllUnassignedTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM Tasks WHERE employeeId IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Task task = new Task(rs.getInt("taskId"), rs.getString("description"), rs.getBoolean("isCompleted"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public List<Task> getTasksForEmployee(int employeeId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM Tasks WHERE employeeId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(rs.getInt("taskId"), rs.getString("description"), rs.getBoolean("isCompleted"));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public void assignTaskToEmployee(int employeeId, int taskId) throws SQLException {
        String query = "UPDATE Tasks SET employeeId = ? WHERE taskId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, taskId);
            stmt.executeUpdate();
        }
    }

    public void completeTask(int taskId) throws SQLException {
        String query = "UPDATE Tasks SET isCompleted = TRUE WHERE taskId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        }
    }


    public void addTask(Task task) throws SQLException {
        String query = "INSERT INTO Tasks (description, isCompleted) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, task.getDescription());
            stmt.setBoolean(2, task.isCompleted());
            stmt.executeUpdate();
        }
    }
}
