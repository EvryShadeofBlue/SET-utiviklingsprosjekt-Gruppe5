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

    public BeskjedOptions(BeskjedService beskjedService) {
        this.beskjedService = beskjedService;
        setTitle("Opprett ny beskjed");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2));

        JLabel beskrivelseLabel = new JLabel("Beskrivelse ");
        beskrivelseFelt = new JTextField();

        JLabel synligTidsenhetLabel = new JLabel("Synlig i timer: ");
        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);

        JLabel datoLabel = new JLabel("Dato (YYYY-MM-DD): ");
        datoFelt = new JTextField();

        JLabel klokkeslettLabel = new JLabel("Klokkelsett (HH:MM): ");
        klokkeslettFelt = new JTextField();

        lagreKnapp = new JButton("Lagre beskjed");
        lagreKnapp.addActionListener(e -> lagreBeskjed());

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

}
