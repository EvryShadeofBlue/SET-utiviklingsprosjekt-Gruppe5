package org.app.core.models;


import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    private JLabel relativeNameLabel;
    private JLabel careRecieverNameLabel;
    private JLabel relativeNameValue;
    private JLabel careRecieverNameValue;

    public MainPage(String relativeName, String careRecieverName) {
        setTitle("Hovedside ");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints g1 = new GridBagConstraints();
        g1.insets = new Insets(10, 10, 10, 10);
        g1.fill = GridBagConstraints.HORIZONTAL;
        g1.weightx = 1;

        g1.gridx = 0;
        g1.gridy = 0;
        g1.gridwidth = 1;
        relativeNameLabel = new JLabel("Deg: ");
        add(relativeNameLabel, g1);

        g1.gridx = 1;
        g1.gridy = 0;
        g1.gridwidth = 2;
        relativeNameValue = new JLabel(relativeName);
        add(relativeNameValue, g1);

        g1.gridx = 0;
        g1.gridy = 1;
        g1.gridwidth = 1;
        careRecieverNameLabel = new JLabel("Pleietrengende: ");
        add(careRecieverNameLabel, g1);

        String careRecieverDisplayName = (careRecieverName == null || careRecieverName.isEmpty())
                ? "Ingen pleietrengende registrert" : careRecieverName;
        careRecieverNameValue = new JLabel(careRecieverDisplayName);

        g1.gridx = 1;
        g1.gridy = 1;
        g1.gridwidth = 2;
        careRecieverNameValue = new JLabel(careRecieverName);
        add(careRecieverNameValue, g1);

        setVisible(true);


    }
}
