package org.app.core.models;

import org.app.core.avtaler.AvtaleDBImplementation;
import org.app.core.avtaler.AvtalePage;
import org.app.core.avtaler.AvtaleService;
import org.app.core.beskjeder.BeskjedDBImplementation;
import org.app.core.beskjeder.BeskjedPage;
import org.app.core.beskjeder.BeskjedService;
import org.app.core.brukere.pleietrengende.PleietrengendeDBImplementation;
import org.app.core.brukere.pleietrengende.PleietrengendeService;
import org.app.core.brukere.pårørende.Parorende;
import org.app.core.brukere.pleietrengende.Pleietrengende;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    private JLabel parorendeNavnLabel;
    private JLabel pleietrengendeNavnLabel;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private BeskjedService beskjedService;
    private AvtaleService avtaleService;
    private PleietrengendeService pleietrengendeService;
    private JButton leggTilPleietrengendeKnapp;
    private JButton avtalerKnapp;
    private JButton beskjedKnapp;
    private JButton handlelisteKnapp;

    public MainPage(Parorende parorende, Pleietrengende pleietrengende) {
        setTitle("Hovedside ");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Hjelp");
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);

        setLayout(new GridBagLayout());
        GridBagConstraints g1 = new GridBagConstraints();
        g1.insets = new Insets(10, 10, 10, 10);
        g1.fill = GridBagConstraints.HORIZONTAL;
        g1.weightx = 1;

        g1.gridx = 0;
        g1.gridy = 0;
        g1.anchor = GridBagConstraints.NORTHWEST;
        parorendeNavnLabel = new JLabel("Deg: " + parorende.getFornavn() + " " +  parorende.getEtternavn());
        add(parorendeNavnLabel, g1);
        parorendeNavnLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        String pleietrengendeNavn;
        if (pleietrengende != null) {
            pleietrengendeNavn = pleietrengende.getFornavn() + " " + pleietrengende.getEtternavn();
        }
        else {
            pleietrengendeNavn = "Ingen pleietrengende";
        }

        g1.gridy = 1;
        pleietrengendeNavnLabel = new JLabel("Pleietrengende: " + pleietrengendeNavn);
        add(pleietrengendeNavnLabel, g1);
        pleietrengendeNavnLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        leggTilPleietrengendeKnapp = new JButton("Legg til pleietrengende");
        avtalerKnapp = new JButton("Avtaler");
        beskjedKnapp = new JButton("Beskjeder");
        handlelisteKnapp = new JButton("Handleliste");

        Dimension buttonSize = new Dimension(200, 100);
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

        beskjedService = new BeskjedService(new BeskjedDBImplementation());
        beskjedKnapp.addActionListener(e -> {
            if (pleietrengende != null) {
                new BeskjedPage(beskjedService, parorende, pleietrengende, this);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Legg til en pleietrengende for å få tilgang til denne siden.",
                        "Ingen pleietrengende", JOptionPane.WARNING_MESSAGE);
            }});

        avtaleService = new AvtaleService(new AvtaleDBImplementation());
        avtalerKnapp.addActionListener(e -> {
            if (pleietrengende != null) {
                new AvtalePage(avtaleService, parorende, pleietrengende, this);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(this, "Legg til en pleietrengende for å få tilgang til denne siden.",
                        "Ingen pleietrengende", JOptionPane.WARNING_MESSAGE);
            }
        });

        pleietrengendeService = new PleietrengendeService(new PleietrengendeDBImplementation());
        leggTilPleietrengendeKnapp.addActionListener(e -> {
            new LeggTilPleietrengendePage(pleietrengendeService, parorende.getParorendeId(), this);
            this.setVisible(false);
        });

        setVisible(true);
    }

    public void oppdaterPleietrengendeInfo(Pleietrengende pleietrengende) {
        this.pleietrengende = pleietrengende;
        String pleietrengendeNavn = (pleietrengende != null) ? pleietrengende.getFornavn() + " " + pleietrengende.getEtternavn() : "Ingen pleietrengende";
        pleietrengendeNavnLabel.setText("Pleietrengende: " + pleietrengendeNavn);
        avtalerKnapp.setEnabled(true);
        beskjedKnapp.setEnabled(true);
        repaint();
    }
    public void visHovedside() {
        setVisible(true);
    }
}
