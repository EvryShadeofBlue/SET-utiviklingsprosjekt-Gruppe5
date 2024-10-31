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
    private Parorende parorende;
    private Pleietrengende pleietrengende;

    public BeskjedOptions(BeskjedService beskjedService, Parorende parorende, Pleietrengende pleietrengende) {
        this.beskjedService = beskjedService;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
        setTitle("Beskjeder");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2));

        JLabel beskrivelseLabel = new JLabel("Beskrivelse ");
        beskrivelseFelt = new JTextField();

        JLabel synligTidsenhetLabel = new JLabel("Synlig i timer: ");
        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);

        JLabel datoLabel = new JLabel("Dato (yyyy-MM-dd): ");
        datoFelt = new JTextField();

        JLabel klokkeslettLabel = new JLabel("Klokkelsett (HH:mm): ");
        klokkeslettFelt = new JTextField();

        lagreKnapp = new JButton("Lagre");
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime datoOgTid = LocalDateTime.parse(datoStr + " " + klokkeslettStr, formatter);

            Beskjed nyBeskjed = beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
            JOptionPane.showMessageDialog(this, "Beskjed opprettet ");
            dispose();
        }
        catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(this, "Feil format på klokkeslettformat. Vennligst bruk YYYY-MM-DD for dato og HH:MM for klokkeslett");
        }
    }


}
