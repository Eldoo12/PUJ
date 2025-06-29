package com.eldinedin.example.gui;

import com.eldinedin.example.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JButton loginButton;
    private JButton backButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel panel;
    private JLabel userLabel;
    private JLabel passwordLabel;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicijalizacija komponenti
        panel = new JPanel();
        userLabel = new JLabel("User:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        usernameField.setBounds(100, 10, 160, 25);
        panel.add(usernameField);

        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordField.setBounds(100, 40, 160, 25);
        panel.add(passwordField);

        loginButton.setBounds(10, 80, 250, 25);
        panel.add(loginButton);

        backButton.setBounds(10, 110, 250, 25);
        panel.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if (authenticate(username, password)) {
                        String role = getUserRole(username);
                        int userId = getUserId(username);
                        openDashboard(role, userId);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid login credentials", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
    }

    private boolean authenticate(String username, String password) throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    private String getUserRole(String username) throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT role FROM Users WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("role");
        }
        return null;
    }

    private int getUserId(String username) throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT id FROM Users WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        return -1;
    }

    private void openDashboard(String role, int userId) {
        this.dispose();
        switch (role) {
            case "Employee":
                new EmployeeDashboard(userId);
                break;
            case "Manager":
                new ManagerDashboard();
                break;
            case "SuperAdmin":
                new SuperAdminDashboard();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void back() {
        // Implementiraj šta treba da se desi kada korisnik klikne na "Back"
        this.setVisible(false);
        // Možeš npr. otvoriti neki prethodni prozor ili nešto slično
    }
}
