package org.app.gui.pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class ActivityListPage extends JFrame {
    private JTextField taskField;
    private JButton addTaskButton;
    private JList<String> taskList;
    private JButton showTaskButton;
    private DefaultListModel<String> taskListModel;
    private JFormattedTextField dueDateField;
    private JLabel dueTimeLabel;
    private JTextField dueTimeField;
    private JLabel dueDateLabel;
    private JButton backToLoginButton;

    public ActivityListPage() {
        setTitle("Activity Activity List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        taskField = new JTextField(10);
        addTaskButton = new JButton("Add a new Task");
        showTaskButton = new JButton("Show Task On New Page");
        backToLoginButton = new JButton("Back to Login");


        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);

        dueDateLabel = new JLabel("due Date (YYYY-MM-DD)");
        dueTimeLabel = new JLabel("Due Time (HH:MM)");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dueDateField = new JFormattedTextField(dateFormat);
        dueDateField.setColumns(8);

        dueTimeField = new JTextField(8);
        dueTimeField.setText("");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(5, 5, 5, 5);
        g2.fill = GridBagConstraints.HORIZONTAL;

        g2.gridx = 0;
        g2.gridy = 0;
        g2.weightx = 1;
        panel.add(dueDateLabel, g2);

        g2.gridx = 0;
        g2.gridy = 1;
        g2.weightx = 2;
        panel.add(dueDateField, g2);

        g2.gridx = 0;
        g2.gridy = 2;
        g2.weightx = 1;
        panel.add(dueTimeLabel,g2);

        g2.gridx = 0;
        g2.gridy = 3;
        g2.weightx = 1;
        panel.add(dueTimeField, g2);

        g2.gridx = 0;
        g2.gridy = 4;
        g2.weightx = 1;
        panel.add(new JLabel("Task: "), g2);

        g2.gridx = 0;
        g2.gridy = 5;
        g2.weightx = 2;
        panel.add(taskField, g2);

        g2.gridx = 1;
        g2.gridy = 6;
        g2.weightx = 1;
        panel.add(addTaskButton, g2);

        g2.gridx = 0;
        g2.gridy = 8;
        g2.weightx = 1;
        panel.add(showTaskButton,g2);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        g2.gridx = 0;
        g2.gridy = 11;
        g2.gridwidth = 1;
        g2.anchor = GridBagConstraints.WEST;
        panel.add(backToLoginButton, g2);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();
            }
        });

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dueDate = dueDateField.getText().trim();
                String dueTime = dueTimeField.getText().trim();
                String task = taskField.getText();

                if (!task.isEmpty()) {
                    String taskWithDueDateTime = task;

                    if (!dueDate.isEmpty() && !dueTime.isEmpty()) {
                        taskWithDueDateTime +=  "(Due:" + dueDate + " " + dueTime + ")";
                    } else if (!dueDate.isEmpty()) {
                        taskWithDueDateTime += "(Due:" + dueDate + ")";
                    } else if (dueTime.isEmpty()) {
                        taskWithDueDateTime += "(Due:" + dueTime + ")";
                    }
                    taskListModel.addElement(taskWithDueDateTime);
                    dueDateField.setText("");
                    dueTimeField.setText("");
                    taskField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a task");
                }
            }
        });

        showTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaskDisplayPage(taskListModel);
            }
        });

        setVisible(true);
    }

}