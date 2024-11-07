package org.app.core.models;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BeskjedOptions extends JFrame {
    private JTextArea beskrivelseFelt;
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


        setLayout(new GridLayout(5, 2, 10, 10));

        beskrivelseFelt = new JTextArea(2, 20);
        beskrivelseFelt.setLineWrap(true);
        beskrivelseFelt.setWrapStyleWord(true);
        JScrollPane beskrivelsePanel = new JScrollPane(beskrivelseFelt);


        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);
        JPanel synligTidsenhetPanel = createInputPanel("Synlig i timer: ", synligTidsenhetFelt);

        datoFelt = new JTextField();
        JPanel datoPanel = createInputPanel("Dato (yyyy-MM-dd): ", datoFelt);

        klokkeslettFelt = new JTextField();
        JPanel klokkeslettPanel = createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt);

        lagreKnapp = new JButton("Lagre");
        lagreKnapp.setPreferredSize(new Dimension(100, 10));
        lagreKnapp.addActionListener(e -> lagreBeskjed());

        add(beskrivelsePanel);
        add(synligTidsenhetPanel);
        add(datoPanel);
        add(klokkeslettPanel);
        add(new JLabel());
        add(lagreKnapp);

        setVisible(true);
    }

    private JPanel createInputPanel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputField.setPreferredSize(new Dimension(300, 30));
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(inputField);

        return panel;
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
            JOptionPane.showMessageDialog(this, "Beskjed opprettet");
            dispose();
        } catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(this, "Feil format på klokkeslettformat. Vennligst bruk YYYY-MM-DD for dato og HH:MM for klokkeslett");
        }
    }
}