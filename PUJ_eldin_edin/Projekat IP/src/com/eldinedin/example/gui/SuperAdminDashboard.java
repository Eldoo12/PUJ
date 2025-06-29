package com.eldinedin.example.gui;

import com.eldinedin.example.dao.UserDAO;
import com.eldinedin.example.models.Employee;
import com.eldinedin.example.models.Manager;
import com.eldinedin.example.models.SuperAdmin;
import com.eldinedin.example.models.User;
import com.eldinedin.example.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SuperAdminDashboard extends JFrame {
    private JTable userTable;
    private JPanel panel;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JScrollPane scrollPane;

    private UserDAO userDAO;

    public SuperAdminDashboard() {
        setTitle("SuperAdmin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicijalizacija komponenti
        panel = new JPanel();
        userTable = new JTable();
        scrollPane = new JScrollPane(userTable);
        addUserButton = new JButton("Add User");
        editUserButton = new JButton("Edit User");
        deleteUserButton = new JButton("Delete User");

        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(panel);
        placeComponents(panel);

        setVisible(true);
        loadUserData();
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        scrollPane.setBounds(10, 10, 560, 250);
        panel.add(scrollPane);

        addUserButton.setBounds(10, 280, 180, 25);
        panel.add(addUserButton);

        editUserButton.setBounds(200, 280, 180, 25);
        panel.add(editUserButton);

        deleteUserButton.setBounds(390, 280, 180, 25);
        panel.add(deleteUserButton);

        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter Username:");
                String password = JOptionPane.showInputDialog("Enter Password:");
                String role = JOptionPane.showInputDialog("Enter Role (Employee/Manager/SuperAdmin):");
                try {
                    User newUser;
                    if (role.equalsIgnoreCase("Employee")) {
                        newUser = new Employee(username, password);
                    } else if (role.equalsIgnoreCase("Manager")) {
                        newUser = new Manager(username, password);
                    } else {
                        newUser = new SuperAdmin(username, password);
                    }
                    userDAO.addUser(newUser);
                    JOptionPane.showMessageDialog(null, "User added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadUserData();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        editUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString());
                    String username = JOptionPane.showInputDialog("Enter New Username:");
                    String password = JOptionPane.showInputDialog("Enter New Password:");
                    String role = JOptionPane.showInputDialog("Enter New Role (Employee/Manager/SuperAdmin):");
                    try {
                        User updatedUser;
                        if (role.equalsIgnoreCase("Employee")) {
                            updatedUser = new Employee(userId, username, password);
                        } else if (role.equalsIgnoreCase("Manager")) {
                            updatedUser = new Manager(userId, username, password);
                        } else {
                            updatedUser = new SuperAdmin(userId, username, password);
                        }
                        userDAO.updateUser(updatedUser);
                        JOptionPane.showMessageDialog(null, "User updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadUserData();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to edit", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString());
                    try {
                        userDAO.deleteUser(userId);
                        JOptionPane.showMessageDialog(null, "User deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadUserData();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadUserData() {
        try {
            List<User> users = userDAO.getAllUsers();
            String[] columnNames = {"User ID", "Username", "Role"};
            Object[][] data = new Object[users.size()][3];
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                data[i][0] = user.getId();
                data[i][1] = user.getUsername();
                data[i][2] = user instanceof Employee ? "Employee"
                        : user instanceof Manager ? "Manager"
                        : "SuperAdmin";
            }
            userTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
