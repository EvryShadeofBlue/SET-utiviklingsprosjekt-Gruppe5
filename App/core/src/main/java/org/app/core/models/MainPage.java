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
        g1.anchor = GridBagConstraints.NORTHWEST;
        relativeNameLabel = new JLabel("Deg: " + relativeName);
        add(relativeNameLabel, g1);

        /*
        g1.gridy = 1;
        g1.gridwidth = 2;
        relativeNameValue = new JLabel(relativeName);
        add(relativeNameValue, g1);

         */


        g1.gridy = 1;
        careRecieverNameLabel = new JLabel("Pleietrengende: " + (careRecieverName.isEmpty() ? "Ingen pleietrengende registrert" : careRecieverName));
        add(careRecieverNameLabel, g1);

        /*
        String careRecieverDisplayName = (careRecieverName == null || careRecieverName.isEmpty())
                ? "Ingen pleietrengende registrert" : careRecieverName;
        careRecieverNameValue = new JLabel(careRecieverDisplayName);



        g1.gridx = 1;
        g1.gridy = 1;
        g1.gridwidth = 2;
        careRecieverNameValue = new JLabel(careRecieverName);
        add(careRecieverNameValue, g1);

         */

        JButton leggTilPleietrengendeKnapp = new JButton("Legg til pleietrengende");
        JButton avtalerKnapp = new JButton("Avtaler");
        JButton beskjedKnapp = new JButton("Beskjeder");
        JButton handlelisteKnapp = new JButton("Handleliste");

        Dimension buttonSize = new Dimension(200, 40);
        leggTilPleietrengendeKnapp.setPreferredSize(buttonSize);
        avtalerKnapp.setPreferredSize(buttonSize);
        beskjedKnapp.setPreferredSize(buttonSize);
        handlelisteKnapp.setPreferredSize(buttonSize);

        g1.gridx = 0;
        g1.gridy = 2;
        add(leggTilPleietrengendeKnapp, g1);

        g1.gridy = 3;
        add(avtalerKnapp, g1);

        g1.gridy = 4;
        add(beskjedKnapp, g1);

        g1.gridy = 5;
        add(handlelisteKnapp, g1);

        setVisible(true);


    }
}
