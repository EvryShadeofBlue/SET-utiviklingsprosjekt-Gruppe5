package org.app.gui.pages;

import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.logikk.beskjed.BeskjedLogikk;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BeskjedPage extends JFrame {
    private JTextArea beskrivelseFelt;
    private JComboBox<Integer> synligTidsenhetFelt;
    private JTextField datoFelt;
    private JTextField klokkeslettFelt;
    private JButton lagreKnapp;
    private JPanel beskjedListePanel;
    private BeskjedLogikk beskjedLogikk;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private org.app.gui.pages.MainPage mainPage;
    private JButton tilbakeKnapp;

    public BeskjedPage(BeskjedLogikk beskjedLogikk, Parorende parorende, Pleietrengende pleietrengende, MainPage mainPage) {
        this.beskjedLogikk = beskjedLogikk;
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
        inputPanel.setLayout(new GridLayout(6, 1, 10, 10));

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
        lagreKnapp.setPreferredSize(new Dimension(100, 30));
        lagreKnapp.addActionListener(e -> oppretteBeskjed());

        tilbakeKnapp = new JButton("Tilbake");
        tilbakeKnapp.setPreferredSize(new Dimension(100, 30));
        tilbakeKnapp.addActionListener(e -> {
            this.dispose();
            mainPage.setVisible(true);
        });

        JPanel tilbakePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tilbakePanel.add(tilbakeKnapp);

        setLayout(new BorderLayout());
        add(tilbakePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        inputPanel.add(beskrivelsePanel);
        inputPanel.add(synligTidsenhetPanel);
        inputPanel.add(datoPanel);
        inputPanel.add(klokkeslettPanel);
        inputPanel.add(lagreKnapp);
        inputPanel.add(tilbakeKnapp);
        add(inputPanel, BorderLayout.NORTH);

        beskjedListePanel = new JPanel();
        beskjedListePanel.setLayout(new BoxLayout(beskjedListePanel, BoxLayout.Y_AXIS));

        JScrollPane beskjedScrollPane = new JScrollPane(beskjedListePanel);
        beskjedScrollPane.setPreferredSize(new Dimension(400, 300));
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
        List<Beskjed> beskjedListe = beskjedLogikk.hentBeskjedForParorende(parorende);
        DateTimeFormatter datoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tidFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Beskjed beskjed : beskjedListe) {
            JPanel beskjedPanel = new JPanel();
            beskjedPanel.setLayout(new BoxLayout(beskjedPanel, BoxLayout.Y_AXIS));
            beskjedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            beskjedPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            String dato = beskjed.getDatoOgTid().format(datoFormatter);
            String tid = beskjed.getDatoOgTid().format(tidFormatter);

            JLabel datoLabel = new JLabel("Dato: " + dato);
            datoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel tidLabel = new JLabel("Klokkeslett: " + tid);
            tidLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel beskrivelsesLabel = new JLabel("Beskrivelse: " + beskjed.getBeskrivelse());
            beskrivelsesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            beskjedPanel.add(datoLabel);
            beskjedPanel.add(tidLabel);
            beskjedPanel.add(beskrivelsesLabel);

            JPanel knapperPanel = new JPanel();
            knapperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JButton slettKnapp = new JButton("Slett");
            slettKnapp.addActionListener(e -> slettBeskjed(beskjed));
            knapperPanel.add(slettKnapp);

            JButton redigerKnapp = new JButton("Rediger");
            redigerKnapp.addActionListener(e -> redigerBeskjed(beskjed));
            knapperPanel.add(redigerKnapp);

            beskjedPanel.add(knapperPanel);
            beskjedListePanel.add(beskjedPanel);
        }
        beskjedListePanel.revalidate();
        beskjedListePanel.repaint();
    }

    public void oppretteBeskjed() {
        try {
            String beskrivelse = beskrivelseFelt.getText();
            int synligTidsenhet = (int) synligTidsenhetFelt.getSelectedItem();
            String datoStr = datoFelt.getText();
            String klokkeslettStr = klokkeslettFelt.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime datoOgTid = LocalDateTime.parse(datoStr + " " + klokkeslettStr, formatter);

            beskjedLogikk.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
            JOptionPane.showMessageDialog(this, "Beskjed opprettet");

            beskrivelseFelt.setText("");
            datoFelt.setText("");
            klokkeslettFelt.setText("");

            visBeskjeder();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Det oppsto en feil under opprettelsen av beskjedet." +
                    "Vennligst prøv på nytt. ");
        }
    }

    private void redigerBeskjed(Beskjed beskjed) {
        JFrame redigeringsVindu = new JFrame("Rediger Beskjed");
        redigeringsVindu.setSize(400, 300);
        redigeringsVindu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        redigeringsVindu.setLocationRelativeTo(this);

        JTextArea beskrivelseFelt = new JTextArea(beskjed.getBeskrivelse(), 2, 10);
        beskrivelseFelt.setLineWrap(true);
        beskrivelseFelt.setWrapStyleWord(true);

        JComboBox<Integer> synligTidsenhetFelt = new JComboBox<>(new Integer[]{12, 24, 36, 48, 60, 72});
        synligTidsenhetFelt.setSelectedItem(beskjed.getSynligTidsenhet());

        JTextField datoFelt = new JTextField(beskjed.getDatoOgTid().toLocalDate().toString());
        JTextField klokkeslettFelt = new JTextField(beskjed.getDatoOgTid().toLocalTime().toString());

        JButton lagreKnapp = new JButton("Lagre");
        lagreKnapp.addActionListener(e -> {
            try {
                String beskrivelse = beskrivelseFelt.getText();
                int synligTidsenhet = (int) synligTidsenhetFelt.getSelectedItem();
                LocalDateTime datoOgTid = LocalDateTime.parse(datoFelt.getText() + " " + klokkeslettFelt.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                Beskjed nyBeskjed = new Beskjed(beskjed.getBeskjedId(), datoOgTid, beskrivelse, synligTidsenhet, beskjed.getParorende(), beskjed.getPleietrengende());

                Beskjed oppdatertBeskjed = beskjedLogikk.oppdaterBeskjed(nyBeskjed);
                if (oppdatertBeskjed != null) {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Beskjed oppdatert");
                    visBeskjeder();
                    redigeringsVindu.dispose();
                } else {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Kunne ikke oppdatere beskjed. Vennligst prøv på nytt.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(redigeringsVindu, "Det oppsto en feil: " + ex.getMessage());
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(new JScrollPane(beskrivelseFelt));
        panel.add(createInputPanel("Synlig i timer: ", synligTidsenhetFelt));
        panel.add(createInputPanel("Dato (yyyy-MM-dd): ", datoFelt));
        panel.add(createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt));
        panel.add(lagreKnapp);

        redigeringsVindu.add(panel);
        redigeringsVindu.setVisible(true);
    }

    private void slettBeskjed(Beskjed beskjed) {
        int svar = JOptionPane.showConfirmDialog(this, "Er du sikker på at du vil" +
                " slette denne beskjeden?", "Bekreft sletting", JOptionPane.YES_NO_OPTION);
        if (svar == JOptionPane.YES_OPTION) {
            beskjedLogikk.slettBeskjed(beskjed.getBeskjedId());
            JOptionPane.showMessageDialog(this, "Beskjed slettet");
            visBeskjeder();
        }
    }
}