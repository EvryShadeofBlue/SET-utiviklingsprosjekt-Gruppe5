package org.app.gui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUIUtils {

    public static JPanel createInputPanel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputField.setPreferredSize(new Dimension(300, 30));
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(inputField);

        return panel;
    }

    public static JScrollPane createScrollPane(JComponent component, Dimension preferredSize, String title) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(preferredSize);
        scrollPane.setBorder(BorderFactory.createTitledBorder(title));
        return scrollPane;
    }

    public static JPanel createItemPanel(String[] labels, JButton[] buttons) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemPanel.add(jLabel);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        itemPanel.add(buttonPanel);

        return itemPanel;
    }

    public static JButton createButton(String text, Dimension dimension, ActionListener actionListener) {
        JButton button = new JButton(text);
        if (dimension != null) {
            button.setPreferredSize(dimension);
        }
        if (actionListener != null) {
            button.addActionListener(actionListener);
        }
        return button;
    }

    public static JLabel createLabel(String text) {
        return new JLabel(text);
    }

    public static JTextField createTextField(int columns) {
        return new JTextField(columns);
    }


}
