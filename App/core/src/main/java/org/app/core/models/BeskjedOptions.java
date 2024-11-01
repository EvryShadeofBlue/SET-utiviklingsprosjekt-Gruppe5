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

        JPanel hovedInnholdPanel = new JPanel();
        hovedInnholdPanel.setLayout(new BoxLayout(hovedInnholdPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(hovedInnholdPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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

        setLayout(new BorderLayout());
        //add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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
            JPanel beskjedPanel = new JPanel();
            beskjedPanel.setLayout(new BoxLayout(beskjedPanel, BoxLayout.Y_AXIS));

            JLabel beskjedLabel = new JLabel(
                    "Dato: " + beskjed.getDatoOgTid().format(dateTimeFormatter) + ". Hendelse: " + beskjed.getBeskrivelse()
            );
            beskjedLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            beskjedPanel.add(beskjedLabel);

            JButton slettKnapp = new JButton("Slett");
            slettKnapp.addActionListener(e -> slettBeskjed(beskjed));
            beskjedPanel.add(slettKnapp);

            beskjedListePanel.add(beskjedPanel);
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

            beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
            JOptionPane.showMessageDialog(this, "Beskjed opprettet");

            visBeskjeder();
        } catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(this, "Kunne ikke opprette beskjed. Vennligst prøv på nytt.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Det oppsto en feil: " + e.getMessage());
        }
    }
    private void slettBeskjed(Beskjed beskjed) {
        int bekreftelse = JOptionPane.showOptionDialog(this,
                "Er du sikker på at du vil slette denne beskjeden: " + beskjed.getBeskrivelse() + "?",
                "Bekreft sletting",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[] {"Ja", "Nei"},
                "Nei");

        if (bekreftelse == 0) {
            beskjedService.slettBeskjed(beskjed.getBeskjedId());
            JOptionPane.showMessageDialog(this, "Beskjed slettet");
            visBeskjeder();
        }
    }


}
