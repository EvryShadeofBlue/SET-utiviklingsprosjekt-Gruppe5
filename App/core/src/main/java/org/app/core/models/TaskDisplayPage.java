package org.app.core.models;

import javax.swing.*;
import java.awt.*;

public class TaskDisplayPage extends JFrame {
    private JList<String> taskList;

    public TaskDisplayPage(DefaultListModel<String> taskListModel){
        setTitle("Task Display");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        taskList = new JList<>(taskListModel);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        setVisible(true);
    }

}
