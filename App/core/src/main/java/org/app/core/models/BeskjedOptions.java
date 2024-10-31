package org.app.core.models;

import org.app.core.repository.BeskjedRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BeskjedOptions extends JFrame {
    private JTextField beskrivelseFelt;
    private JComboBox<Integer> synligTidsenhetFelt;
    private JTextField datoFelt;
    private JTextField klokkeslettFelt;
    private JButton lagreKnapp;
    private BeskjedService beskjedService;
    private Beskjed eksisterendeBeskjed;

    public BeskjedOptions(BeskjedService beskjedService) {
        this(beskjedService, null);
    }

    public BeskjedOptions(BeskjedService beskjedService, Beskjed beskjed) {
        this.beskjedService = beskjedService;
        this.eksisterendeBeskjed = beskjed;
        setTitle("Beskjeder");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2));

        JLabel beskrivelseLabel = new JLabel("Beskrivelse ");
        beskrivelseFelt = new JTextField();
        beskrivelseFelt.setText(beskjed != null ? beskjed.getBeskrivelse() : "");

        JLabel synligTidsenhetLabel = new JLabel("Synlig i timer: ");
        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);
        if (beskjed != null) {
            synligTidsenhetFelt.setSelectedItem(beskjed.getSynligTidsenhet());
        }

        JLabel datoLabel = new JLabel("Dato (YYYY-MM-DD): ");
        datoFelt = new JTextField();
        datoFelt.setText(beskjed != null ? beskjed.getDatoOgTid().toLocalDate().toString() : "");

        JLabel klokkeslettLabel = new JLabel("Klokkelsett (HH:MM): ");
        klokkeslettFelt = new JTextField();
        klokkeslettFelt.setText(beskjed != null ? beskjed.getDatoOgTid().toLocalTime().toString() : "");

        lagreKnapp = new JButton(beskjed == null ? "Lagre beskjed" : "Oppdater beskjed");
        lagreKnapp.addActionListener(e -> {
            if (beskjed == null) {
                lagreBeskjed();
            }
            else {
                oppdaterBeskjed();
            }
        });

        add(beskrivelseLabel);
        add(beskrivelseFelt);
        add(synligTidsenhetLabel);
        add(synligTidsenhetFelt);
        add(datoLabel);
        add(datoFelt);
        add(klokkeslettLabel);
        add(klokkeslettFelt);
        add(new JLabel());
        add(lagreKnapp);

        setVisible(true);
    }



    public void lagreBeskjed() {
        try {
            String beskrivelse = beskrivelseFelt.getText();
            int synligTidsenhet = (int) synligTidsenhetFelt.getSelectedItem();

            String datoStr = datoFelt.getText();
            String klokkeslettStr = klokkeslettFelt.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM");
            LocalDateTime datoOgTid = LocalDateTime.parse(datoStr + " " + klokkeslettStr, formatter);

            Beskjed nyBeskjed = beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet);
            JOptionPane.showMessageDialog(this, "Beskjed opprettet: " + nyBeskjed.getBeskrivelse());
            dispose();
        }
        catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(this, "Feil format på klokkeslettformat. Vennligst bruk YYYY-MM-DD for dato og HH:MM for klokkeslett");
        }
        /*
        String beskrivelse = beskrivelseFelt.getText();
        int synligTidsenhet = (int) synligTidsenhetFelt.getSelectedItem();
        LocalDateTime datoOgTid = LocalDateTime.now();
        Beskjed nyBeskjed = beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet);
        JOptionPane.showMessageDialog(this, "Beskjed opprettet: " + nyBeskjed.getBeskrivelse());
        dispose();

         */
    }

    public void oppdaterBeskjed() {
        try {
            eksisterendeBeskjed.setBeskrivelse(beskrivelseFelt.getText());
            eksisterendeBeskjed.setSynligTidsenhet((int) synligTidsenhetFelt.getSelectedItem());
            eksisterendeBeskjed.setDatoOgTid(LocalDateTime.parse(datoFelt.getText() + " " + klokkeslettFelt.getText(), DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM")));

            beskjedService.oppdaterBeskjed(eksisterendeBeskjed);
            JOptionPane.showMessageDialog(this, "Beskjed oppdatert");
            dispose();
        }
        catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(this, "Feil format på dato eller klokkelsett. ");
        }
    }

}
