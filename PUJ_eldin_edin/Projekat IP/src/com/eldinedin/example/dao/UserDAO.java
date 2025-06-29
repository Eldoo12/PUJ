package com.eldinedin.example.dao;

import com.eldinedin.example.models.Employee;
import com.eldinedin.example.models.Manager;
import com.eldinedin.example.models.SuperAdmin;
import com.eldinedin.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user instanceof Employee ? "Employee" : user instanceof Manager ? "Manager" : "SuperAdmin");
            stmt.executeUpdate();
        }
    }

    public User getUser(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if (role.equals("Employee")) {
                        return new Employee(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    } else if (role.equals("Manager")) {
                        return new Manager(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    } else {
                        return new SuperAdmin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    }
                }
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String role = rs.getString("role");
                User user;
                if (role.equals("Employee")) {
                    user = new Employee(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                } else if (role.equals("Manager")) {
                    user = new Manager(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                } else {
                    user = new SuperAdmin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
                users.add(user);
            }
        }
        return users;
    }

    // Metoda za dohvaćanje korisnika po njihovoj ulozi
    public List<User> getAllUsersByRole(String role) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user;
                    if (role.equals("Employee")) {
                        user = new Employee(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    } else if (role.equals("Manager")) {
                        user = new Manager(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    } else {
                        user = new SuperAdmin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    }
                    users.add(user);
                }
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE Users SET username = ?, password = ?, role = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user instanceof Employee ? "Employee" : user instanceof Manager ? "Manager" : "SuperAdmin");
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
