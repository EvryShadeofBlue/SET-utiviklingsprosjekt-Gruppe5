package org.app.core.models;

import org.app.core.repository.BeskjedRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;

public class BeskjedOptions extends JFrame {
    private JTextField beskrivelseFelt;
    private JComboBox<Integer> synligTidsenhetFelt;
    private JButton lagreKnapp;
    private BeskjedService beskjedService;

    public BeskjedOptions(BeskjedService beskjedService) {
        this.beskjedService = beskjedService;
        setTitle("Opprett ny beskjed");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 2));

        JLabel beskrivelseLabel = new JLabel("Beskrivelse ");
        beskrivelseFelt = new JTextField();

        JLabel synligTidsenhetLabel = new JLabel("Synlig i timer: ");
        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);

        lagreKnapp = new JButton("Lagre beskjed");
        lagreKnapp.addActionListener(e -> lagreBeskjed());

        add(beskrivelseLabel);
        add(beskrivelseFelt);
        add(synligTidsenhetLabel);
        add(synligTidsenhetFelt);
        add(new JLabel());
        add(lagreKnapp);

        setVisible(true);
    }



    public void lagreBeskjed() {
        String beskrivelse = beskrivelseFelt.getText();
        int synligTidsenhet = (int) synligTidsenhetFelt.getSelectedItem();
        LocalDateTime datoOgTid = LocalDateTime.now();
        Beskjed nyBeskjed = beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet);
        JOptionPane.showMessageDialog(this, "Beskjed opprettet: " + nyBeskjed.getBeskrivelse());
        dispose();
    }

}
