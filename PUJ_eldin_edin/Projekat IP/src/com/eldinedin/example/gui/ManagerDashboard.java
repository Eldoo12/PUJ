package com.eldinedin.example.gui;

import com.eldinedin.example.dao.TaskDAO;
import com.eldinedin.example.dao.UserDAO;
import com.eldinedin.example.models.Task;
import com.eldinedin.example.models.User;
import com.eldinedin.example.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManagerDashboard extends JFrame {
    private JTable employeeTable;
    private JTable taskTable;
    private JButton assignTaskButton;
    private JButton createTaskButton;
    private JButton backButton;
    private JButton logoutButton;
    private JScrollPane employeeScrollPane;
    private JScrollPane taskScrollPane;
    private JPanel panel;

    private UserDAO userDAO;
    private TaskDAO taskDAO;

    public ManagerDashboard() {
        setTitle("Manager Dashboard");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicijalizacija komponenti
        panel = new JPanel();
        employeeTable = new JTable();
        taskTable = new JTable();
        assignTaskButton = new JButton("Assign Task");
        createTaskButton = new JButton("Create Task");
        backButton = new JButton("Back");
        logoutButton = new JButton("Logout");
        employeeScrollPane = new JScrollPane(employeeTable);
        taskScrollPane = new JScrollPane(taskTable);

        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            userDAO = new UserDAO(connection);
            taskDAO = new TaskDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(panel);
        placeComponents(panel);

        setVisible(true);
        loadEmployeeData();
        loadTaskData();
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        employeeScrollPane.setBounds(10, 10, 360, 250);
        panel.add(employeeScrollPane);

        taskScrollPane.setBounds(400, 10, 360, 250);
        panel.add(taskScrollPane);

        assignTaskButton.setBounds(10, 280, 750, 25);
        panel.add(assignTaskButton);

        createTaskButton.setBounds(10, 320, 750, 25);
        panel.add(createTaskButton);

        backButton.setBounds(10, 360, 360, 25);
        panel.add(backButton);

        logoutButton.setBounds(400, 360, 360, 25);
        panel.add(logoutButton);

        assignTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedEmployeeRow = employeeTable.getSelectedRow();
                int selectedTaskRow = taskTable.getSelectedRow();
                if (selectedEmployeeRow != -1 && selectedTaskRow != -1) {
                    int employeeId = Integer.parseInt(employeeTable.getValueAt(selectedEmployeeRow, 0).toString());
                    int taskId = Integer.parseInt(taskTable.getValueAt(selectedTaskRow, 0).toString());
                    assignTask(employeeId, taskId);
                    JOptionPane.showMessageDialog(null, "Task assigned", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadTaskData();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee and a task", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTask();
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

    private void loadEmployeeData() {
        try {
            List<User> employees = userDAO.getAllUsersByRole("Employee");
            String[] columnNames = {"Employee ID", "Username"};
            Object[][] data = new Object[employees.size()][2];
            for (int i = 0; i < employees.size(); i++) {
                User employee = employees.get(i);
                data[i][0] = employee.getId();
                data[i][1] = employee.getUsername();
            }
            employeeTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTaskData() {
        try {
            List<Task> tasks = taskDAO.getAllUnassignedTasks();
            String[] columnNames = {"Task ID", "Description"};
            Object[][] data = new Object[tasks.size()][2];
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                data[i][0] = task.getTaskId();
                data[i][1] = task.getDescription();
            }
            taskTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void assignTask(int employeeId, int taskId) {
        try {
            taskDAO.assignTaskToEmployee(employeeId, taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTask() {
        String description = JOptionPane.showInputDialog("Enter Task Description:");
        if (description != null && !description.trim().isEmpty()) {
            try {
                Task newTask = new Task(description, false);
                taskDAO.addTask(newTask);
                JOptionPane.showMessageDialog(null, "Task created", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTaskData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Task description cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
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
