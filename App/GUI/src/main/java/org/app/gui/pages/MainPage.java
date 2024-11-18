package org.app.gui.pages;

import org.app.core.models.ExpiredEntriesCleaner;
import org.app.core.services.AvtaleService;
import org.app.core.services.BeskjedService;
import org.app.core.services.PleietrengendeService;
import org.app.core.logikk.avtale.AvtaleLogikk;
import org.app.core.logikk.beskjed.BeskjedLogikk;
import org.app.core.logikk.LeggTilPleietrengendeLogikk;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.database.AvtaleDBImplementation;
import org.app.database.BeskjedDBImplementation;
import org.app.database.PleietrengendeDBImplementation;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    private JLabel parorendeNavnLabel;
    private JLabel pleietrengendeNavnLabel;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private BeskjedLogikk beskjedLogikk;
    private AvtaleLogikk avtaleLogikk;
    private LeggTilPleietrengendeLogikk pleietrengendeService;
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

        beskjedLogikk = new BeskjedLogikk(new BeskjedDBImplementation());
        avtaleLogikk = new AvtaleLogikk(new AvtaleDBImplementation());

        ExpiredEntriesCleaner cleaner = new ExpiredEntriesCleaner(beskjedService, avtaleService);
        cleaner.startCleaning(0, 24 * 60 * 60 * 1000);

        beskjedLogikk = new BeskjedLogikk(new BeskjedDBImplementation());
        beskjedKnapp.addActionListener(e -> {
            if (pleietrengende != null) {
                new BeskjedPage(beskjedLogikk, parorende, pleietrengende, this);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Legg til en pleietrengende for å få tilgang til denne siden.",
                        "Ingen pleietrengende", JOptionPane.WARNING_MESSAGE);
            }});


        avtaleLogikk = new AvtaleLogikk(new AvtaleDBImplementation());
        avtalerKnapp.addActionListener(e -> {
            if (pleietrengende != null) {
                new AvtalePage(avtaleLogikk, parorende, pleietrengende, this);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(this, "Legg til en pleietrengende" +
                                " for å få tilgang til denne siden.",
                        "Ingen pleietrengende", JOptionPane.WARNING_MESSAGE);
            }
        });

        pleietrengendeService = new LeggTilPleietrengendeLogikk(new PleietrengendeDBImplementation());
        leggTilPleietrengendeKnapp.addActionListener(e -> {
            new LeggTilPleietrengendePage(pleietrengendeService, parorende.getParorendeId(), this);
            this.setVisible(false);
        });

        setVisible(true);
    }

    public void setPleietrengende(Pleietrengende pleietrengende) {
        this.pleietrengende = pleietrengende;
    }

    public void oppdaterPleietrengendeInfo(Pleietrengende pleietrengende) {
        this.pleietrengende = pleietrengende;
        String pleietrengendeNavn = (pleietrengende != null) ? pleietrengende.getFornavn() +
                " " + pleietrengende.getEtternavn() : "Ingen pleietrengende";
        pleietrengendeNavnLabel.setText("Pleietrengende: " + pleietrengendeNavn);
        avtalerKnapp.setEnabled(true);
        beskjedKnapp.setEnabled(true);
        revalidate();
        repaint();
    }
    public void visHovedside() {
        setVisible(true);
        revalidate();
        repaint();

    }
}