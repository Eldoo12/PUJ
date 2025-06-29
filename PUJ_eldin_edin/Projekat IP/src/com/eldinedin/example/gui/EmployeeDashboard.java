package com.eldinedin.example.gui;

import com.eldinedin.example.dao.TaskDAO;
import com.eldinedin.example.models.Task;
import com.eldinedin.example.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDashboard extends JFrame {
    private JTable taskTable;
    private JPanel panel;
    private JButton completeTaskButton;
    private JButton backButton;
    private JButton logoutButton;
    private JScrollPane scrollPane;

    private TaskDAO taskDAO;
    private int employeeId;

    public EmployeeDashboard(int employeeId) {
        this.employeeId = employeeId;
        setTitle("Employee Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicijalizacija komponenti
        panel = new JPanel();
        taskTable = new JTable();
        scrollPane = new JScrollPane(taskTable);
        completeTaskButton = new JButton("Complete Task");
        backButton = new JButton("Back");
        logoutButton = new JButton("Logout");

        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            taskDAO = new TaskDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(panel);
        placeComponents(panel);

        setVisible(true);
        loadTaskData();
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        scrollPane.setBounds(10, 10, 460, 200);
        panel.add(scrollPane);

        completeTaskButton.setBounds(10, 220, 460, 25);
        panel.add(completeTaskButton);

        backButton.setBounds(10, 250, 225, 25);
        panel.add(backButton);

        logoutButton.setBounds(245, 250, 225, 25);
        panel.add(logoutButton);

        completeTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int taskId = Integer.parseInt(taskTable.getValueAt(selectedRow, 0).toString());
                    completeTask(taskId);
                    JOptionPane.showMessageDialog(null, "Task marked as completed", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadTaskData();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to complete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void loadTaskData() {
        try {
            List<Task> tasks = taskDAO.getTasksForEmployee(employeeId);
            String[] columnNames = {"Task ID", "Description", "Completed"};
            Object[][] data = new Object[tasks.size()][3];
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                data[i][0] = task.getTaskId();
                data[i][1] = task.getDescription();
                data[i][2] = task.isCompleted();
            }
            taskTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void completeTask(int taskId) {
        try {
            taskDAO.completeTask(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void back() {
        this.dispose();
        new LoginFrame();
    }

    private void logout() {
        this.dispose();
        new LoginFrame();
    }
}
