package org.app.core.models;

import org.app.core.repository.BeskjedRepository;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class BeskjedOptions extends JFrame {
    private JTextField beskrivelseField;
    private JComboBox<Integer> synligTidsenhetDropdown;
    private JButton lagreKnapp;
    private BeskjedService beskjedService;

    public BeskjedOptions(BeskjedService beskjedService) {
        this.beskjedService = beskjedService;
        setTitle("Opprett ny beskjed");
        setSize(400, 800);
        setLayout(new GridLayout(3, 2));

        JLabel beskrivelsesLabel = new JLabel("Beskrivelse");
        beskrivelseField = new JTextField();

        JLabel synligTidsenhetLabel = new JLabel("Synlig i timer: ");
        Integer[] synligTidsenheter= {12, 24, 36, 48, 60, 72};
        synligTidsenhetDropdown = new JComboBox<>(synligTidsenheter);

        lagreKnapp = new JButton("Lagre beskjed");
        lagreKnapp.addActionListener(e -> lagreBeskjed());

        add(beskrivelsesLabel);
        add(beskrivelseField);
        add(synligTidsenhetLabel);
        add(synligTidsenhetDropdown);
        add(new JLabel());
        add(lagreKnapp);
    }

    public void lagreBeskjed() {
        String beskrivelse = beskrivelseField.getText();
        int synligTidsenhet = (int) synligTidsenhetDropdown.getSelectedItem();
        LocalDateTime datoOgTid = LocalDateTime.now();
        Beskjed nyBeskjed = beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet);
        JOptionPane.showMessageDialog(this, "Beskjed opprettet: " + nyBeskjed.getBeskrivelse());
        dispose();
    }

}
