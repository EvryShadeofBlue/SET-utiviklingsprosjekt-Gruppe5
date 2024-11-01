package org.app.core.models;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BeskjedOptions extends JFrame {
    private JTextArea beskrivelseFelt;
    private JComboBox<Integer> synligTidsenhetFelt;
    private JTextField datoFelt;
    private JTextField klokkeslettFelt;
    private JButton lagreKnapp;
    private JPanel beskjedListePanel;
    private BeskjedService beskjedService;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private MainPage mainPage;

    public BeskjedOptions(BeskjedService beskjedService, Parorende parorende, Pleietrengende pleietrengende, MainPage mainPage) {
        this.beskjedService = beskjedService;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
        this.mainPage = mainPage;

        setTitle("Beskjeder");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        setLayout(new BorderLayout());

        JButton tilbakeKnapp = new JButton("Tilbake");
        tilbakeKnapp.addActionListener(e -> {
            mainPage.setVisible(true);
            dispose();
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(tilbakeKnapp, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1, 10, 10));

        beskrivelseFelt = new JTextArea(2, 10);
        beskrivelseFelt.setLineWrap(true);
        beskrivelseFelt.setWrapStyleWord(true);
        JScrollPane beskrivelsePanel = new JScrollPane(beskrivelseFelt);
        beskrivelsePanel.setBorder(BorderFactory.createTitledBorder("Beskrivelse"));


        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);
        JPanel synligTidsenhetPanel = createInputPanel("Synlig i timer: ", synligTidsenhetFelt);

        datoFelt = new JTextField();
        JPanel datoPanel = createInputPanel("Dato (yyyy-MM-dd): ", datoFelt);

        klokkeslettFelt = new JTextField();
        JPanel klokkeslettPanel = createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt);

        lagreKnapp = new JButton("Lagre");
        lagreKnapp.addActionListener(e -> lagreBeskjed());

        inputPanel.add(beskrivelsePanel);
        inputPanel.add(synligTidsenhetPanel);
        inputPanel.add(datoPanel);
        inputPanel.add(klokkeslettPanel);
        inputPanel.add(lagreKnapp);
        add(inputPanel, BorderLayout.NORTH);

        beskjedListePanel = new JPanel();
        beskjedListePanel.setLayout(new BoxLayout(beskjedListePanel, BoxLayout.Y_AXIS));

        JScrollPane beskjedScrollPane = new JScrollPane(beskjedListePanel);
        beskjedScrollPane.setBorder(BorderFactory.createTitledBorder("Opprettede beskjeder"));
        add(beskjedScrollPane, BorderLayout.CENTER);

        visBeskjeder();

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

    private void visBeskjeder() {
        beskjedListePanel.removeAll();
        List<Beskjed> beskjedListe = beskjedService.hentBeskjedForParorende(parorende);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Beskjed beskjed : beskjedListe) {
            JLabel beskjedLabel = new JLabel(
                    "Dato: " + beskjed.getDatoOgTid().format(dateTimeFormatter) + ". Hendelse: " + beskjed.getBeskrivelse()
            );
            beskjedLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            beskjedListePanel.add(beskjedLabel);
        }
        beskjedListePanel.revalidate();
        beskjedListePanel.repaint();
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

            visBeskjeder();
        } catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(this, "Feil format på klokkeslettformat. Vennligst bruk YYYY-MM-DD for dato og HH:MM for klokkeslett");
        }
    }
}
