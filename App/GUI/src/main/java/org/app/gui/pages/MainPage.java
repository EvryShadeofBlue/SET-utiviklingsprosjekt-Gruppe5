package org.app.gui.pages;

import org.app.core.logikk.avtale.AvtaleLogikk;
import org.app.core.logikk.beskjed.BeskjedLogikk;
import org.app.core.logikk.LeggTilPleietrengendeLogikk;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.models.Resources;
import org.app.core.repositories.LoggInterface;
import org.app.database.AvtaleDBImplementation;
import org.app.database.BeskjedDBImplementation;
import org.app.database.PleietrengendeDBImplementation;
import org.app.database.LoggDBImplementation;
import org.app.gui.utils.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public MainPage(Parorende parorende, Pleietrengende pleietrengende) throws SQLException {
        setupFrame();
        setupContent();

//        beskjedLogikk = new BeskjedLogikk(new BeskjedDBImplementation());         //Unfinished code supposed to be implemented for TEK
//        avtaleLogikk = new AvtaleLogikk(new AvtaleDBImplementation());
//
//        ExpiredEntriesCleaner cleaner = new ExpiredEntriesCleaner(beskjedService, avtaleService);
//        cleaner.startCleaning(0, 24 * 60 * 60 * 1000);

        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Hovedside ");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
    }

    private void setupContent() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        addParorendeLabel(g);
        addPleietrengendeLabel(g);
        addActionButtons(g);
    }

    private void addParorendeLabel(GridBagConstraints g) {
        g.gridx = 0;
        g.gridy = 0;

        parorendeNavnLabel = new JLabel("Deg: " + parorende.getFornavn() + " " +  parorende.getEtternavn());
        add(parorendeNavnLabel, g);
    }

    private void addPleietrengendeLabel(GridBagConstraints g) {
        g.gridy++;
        String pleietrengendeNavn = (pleietrengende != null) ? pleietrengende.getFornavn() + " " + pleietrengende.getEtternavn() : "Ingen pleietrengende";
        pleietrengendeNavnLabel = new JLabel("Pleietrengende: " + pleietrengendeNavn);
        add(pleietrengendeNavnLabel, g);
    }

    private void addActionButtons(GridBagConstraints g) {
        g.gridy++;
        JButton leggTilPleietrengendeKnapp = GUIUtils.createButton("Legg til pleietrengende", new Dimension(200, 100), e -> {
            new LeggTilPleietrengendePage(new LeggTilPleietrengendeLogikk(new PleietrengendeDBImplementation()), parorende.getParorendeId(), this);
            setVisible(false);
        });
        add(leggTilPleietrengendeKnapp, g);

        g.gridy++;
        JButton avtalerKnapp = GUIUtils.createButton("Avtaler", new Dimension(200, 100), e -> openAvtalePage());
        add(avtalerKnapp, g);

        g.gridy++;
        JButton beskjedKnapp = GUIUtils.createButton("Beskjeder", new Dimension(200, 100), e -> openBeskjedPage());
        add(beskjedKnapp, g);
    }

    private void openAvtalePage() {
        if (pleietrengende != null) {
            new AvtalePage(new AvtaleLogikk(new AvtaleDBImplementation()), parorende, pleietrengende, this);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Legg til en pleietrengende for å få tilgang til denne siden.", "Ingen pleietrengende", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openBeskjedPage() {
        if (pleietrengende != null) {
            new BeskjedPage(new BeskjedLogikk(new BeskjedDBImplementation()), parorende, pleietrengende, this);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Legg til en pleietrengende for å få tilgang til denne siden.", "Ingen pleietrengende", JOptionPane.WARNING_MESSAGE);
        }
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