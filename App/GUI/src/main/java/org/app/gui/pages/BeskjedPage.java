package org.app.gui.pages;

import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.logikk.beskjed.BeskjedLogikk;
import org.app.gui.utils.GUIUtils;

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

    public BeskjedPage(BeskjedLogikk beskjedService, Parorende parorende, Pleietrengende pleietrengende, MainPage mainPage) {
        this.beskjedLogikk = beskjedService;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
        this.mainPage = mainPage;

        setupFrame();
        setupContent();

        visBeskjeder();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Beskjeder");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void setupContent() {
        add(createTilbakePanel(), BorderLayout.NORTH);
        add(createInputPanel(), BorderLayout.NORTH);
        add(createBeskjedListePanel(), BorderLayout.CENTER);
    }

    private JPanel createTilbakePanel() {
        tilbakeKnapp = GUIUtils.createButton("Tilbake", new Dimension(100, 30), e -> {
            this.dispose();
            mainPage.setVisible(true);
        });

        JPanel tilbakePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tilbakePanel.add(tilbakeKnapp);
        return tilbakePanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1, 10, 10));

        beskrivelseFelt = new JTextArea(2, 10);
        beskrivelseFelt.setLineWrap(true);
        beskrivelseFelt.setWrapStyleWord(true);
        JScrollPane beskrivelsePanel = new JScrollPane(beskrivelseFelt);
        beskrivelsePanel.setBorder(BorderFactory.createTitledBorder("Beskrivelse"));

        Integer[] synligTidsenheter = {12, 24, 36, 48, 60, 72};
        synligTidsenhetFelt = new JComboBox<>(synligTidsenheter);
        JPanel synligTidsenhetPanel = GUIUtils.createInputPanel("Synlig i timer: ", synligTidsenhetFelt);

        datoFelt = new JTextField();
        JPanel datoPanel = GUIUtils.createInputPanel("Dato (yyyy-MM-dd): ", datoFelt);

        klokkeslettFelt = new JTextField();
        JPanel klokkeslettPanel = GUIUtils.createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt);

        lagreKnapp = GUIUtils.createButton("Lagre", new Dimension(100, 30), e -> oppretteBeskjed());

        inputPanel.add(beskrivelsePanel);
        inputPanel.add(synligTidsenhetPanel);
        inputPanel.add(datoPanel);
        inputPanel.add(klokkeslettPanel);
        inputPanel.add(lagreKnapp);

        return inputPanel;
    }

    private JScrollPane createBeskjedListePanel() {
        beskjedListePanel = new JPanel();
        beskjedListePanel.setLayout(new BoxLayout(beskjedListePanel, BoxLayout.Y_AXIS));

        return GUIUtils.createScrollPane(beskjedListePanel, new Dimension(400, 300), "Opprettede beskjeder");
    }

    private void visBeskjeder() {
        beskjedListePanel.removeAll();
        List<Beskjed> beskjedListe = beskjedLogikk.hentBeskjedForParorende(parorende);
        DateTimeFormatter datoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tidFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Beskjed beskjed : beskjedListe) {
            String dato = beskjed.getDatoOgTid().format(datoFormatter);
            String tid = beskjed.getDatoOgTid().format(tidFormatter);
            String[] labels = {
                    "Dato: " + dato,
                    "Klokkeslett: " + tid,
                    "Beskrivelse: " + beskjed.getBeskrivelse()
            };

            JButton slettKnapp = new JButton("Slett");
            slettKnapp.addActionListener(e -> slettBeskjed(beskjed));

            JButton redigerKnapp = new JButton("Rediger");
            redigerKnapp.addActionListener(e -> redigerBeskjed(beskjed));

            beskjedListePanel.add(GUIUtils.createItemPanel(labels, new JButton[]{slettKnapp, redigerKnapp}));
        }

        beskjedListePanel.revalidate();
        beskjedListePanel.repaint();
    }

    private void oppretteBeskjed() {
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
        panel.add(GUIUtils.createInputPanel("Synlig i timer: ", synligTidsenhetFelt));
        panel.add(GUIUtils.createInputPanel("Dato (yyyy-MM-dd): ", datoFelt));
        panel.add(GUIUtils.createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt));
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