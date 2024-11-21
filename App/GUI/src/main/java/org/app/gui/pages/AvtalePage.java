package org.app.gui.pages;

import org.app.core.logikk.avtale.*;
import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.gui.utils.GUIUtils;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AvtalePage extends JFrame{
    private JTextArea beskrivelsesFelt;
    private JTextField datoFelt;
    private JTextField klokkeslettFelt;
    private JTextField sluttDatoFelt;
    private JComboBox<String> gjentakelseFelt;
    private JButton lagreKnapp;
    private JPanel avtaleListePanel;
    private AvtaleLogikk avtaleLogikk;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private MainPage mainPage;
    private JButton tilbakeKnapp;


        public AvtalePage(AvtaleLogikk avtaleLogikk, Parorende parorende, Pleietrengende pleietrengende, org.app.gui.pages.MainPage mainPage) {
        this.avtaleLogikk = avtaleLogikk;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
        this.mainPage = mainPage;

        setupFrame();
        setupContent();

        visAvtaler();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Avtaler");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupContent() {
        add(createTilbakePanel(), BorderLayout.NORTH);
        add(createInputPanel(), BorderLayout.CENTER);
        add(createAvtaleListePanel(), BorderLayout.NORTH);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 1, 10, 10));

        beskrivelsesFelt = new JTextArea(2, 10);
        beskrivelsesFelt.setLineWrap(true);
        beskrivelsesFelt.setWrapStyleWord(true);

        JScrollPane beskrivelsesPanel = new JScrollPane(beskrivelsesFelt);
        beskrivelsesPanel.setBorder(BorderFactory.createTitledBorder("Beskrivelse"));

        datoFelt = new JTextField();
        JPanel datoPanel = GUIUtils.createInputPanel("Dato (yyyy-MM-dd): ", datoFelt);

        klokkeslettFelt = new JTextField();
        JPanel klokkeslettPanel = GUIUtils.createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt);

        String[] gjentakelseAlternativer = {"Ingen", "Daglig", "Ukentlig", "Månedlig"};
        gjentakelseFelt = new JComboBox<>(gjentakelseAlternativer);
        JPanel gjentakelsesPanel = GUIUtils.createInputPanel("Gjentakelse: ", gjentakelseFelt);

        sluttDatoFelt = new JTextField();
        JPanel sluttDatoPanel = GUIUtils.createInputPanel("Slutt dato (yyyy-MM-dd)", sluttDatoFelt);

        lagreKnapp = GUIUtils.createButton("Lagre", new Dimension(100, 30), e -> opprettAvtale());

        inputPanel.add(beskrivelsesPanel);
        inputPanel.add(datoPanel);
        inputPanel.add(klokkeslettPanel);
        inputPanel.add(gjentakelsesPanel);
        inputPanel.add(sluttDatoPanel);
        inputPanel.add(lagreKnapp);

        return inputPanel;
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

    private JScrollPane createAvtaleListePanel() {
        avtaleListePanel = new JPanel();
        avtaleListePanel.setLayout(new BoxLayout(avtaleListePanel, BoxLayout.Y_AXIS));

        return GUIUtils.createScrollPane(avtaleListePanel, new Dimension(400, 300), "Opprettede avtaler");
    }

    private void visAvtaler() {
        avtaleListePanel.removeAll();
        List<Avtale> avtaleListe = avtaleLogikk.visAvtaleForParorende(parorende);
        DateTimeFormatter datoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tidFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Avtale avtale : avtaleListe) {
            String dato = avtale.getDatoOgTid().format(datoFormatter);
            String tid = avtale.getDatoOgTid().format(tidFormatter);
            String[] labels = {
                    "Dato: " + dato,
                    "Klokkeslett: " + tid,
                    "Beskrivelse: " + avtale.getBeskrivelse()
            };

            JButton slettKnapp = new JButton("Slett");
            slettKnapp.addActionListener(e -> slettAvtale(avtale));

            JButton redigerKnapp = new JButton("Rediger");
            redigerKnapp.addActionListener(e -> redigerAvtale(avtale));

            avtaleListePanel.add(GUIUtils.createItemPanel(labels, new JButton[]{slettKnapp, redigerKnapp}));
        }

        avtaleListePanel.revalidate();
        avtaleListePanel.repaint();
    }

    private void opprettAvtale() {
        try {
            String beskrivelse = beskrivelsesFelt.getText();
            String datoTekst = datoFelt.getText();
            String klokkeslettTekst = klokkeslettFelt.getText();
            LocalDateTime sluttDato = null;

            LocalDate dato = LocalDate.parse(datoTekst);
            LocalTime klokkeslett = LocalTime.parse(klokkeslettTekst);
            LocalDateTime datoOgTid = LocalDateTime.of(dato, klokkeslett);

            String gjentakelse = (String) gjentakelseFelt.getSelectedItem();

            String sluttDatoTekst = sluttDatoFelt.getText();
            if (!sluttDatoTekst.isEmpty()) {
                LocalDate sluttDatoLocal = LocalDate.parse(sluttDatoTekst);
                sluttDato = sluttDatoLocal.atStartOfDay();
            }

            Avtale avtale = new Avtale(datoOgTid, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);

            avtaleLogikk.opprettAvtale(avtale);
            JOptionPane.showMessageDialog(this, "Avtale opprettet. ");
            beskrivelsesFelt.setText("");
            datoFelt.setText("");
            klokkeslettFelt.setText("");
            gjentakelseFelt.setSelectedItem(null);
            sluttDatoFelt.setText("");

            visAvtaler();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kunne ikke opprette avtale. Vennligst prøv på nytt. ");
            e.printStackTrace();
        }
    }

    private void redigerAvtale(Avtale avtale) {
        if (avtale.getGjentakelse() != null && !"Ingen".equals(avtale.getGjentakelse())) {
            JOptionPane.showMessageDialog(this,
                    "En gjentakende avtale kan ikke redigeres. Den kan bare slettes.",
                    "Feil",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame redigeringsVindu = new JFrame("Rediger Avtale");
        redigeringsVindu.setSize(400, 250);
        redigeringsVindu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        redigeringsVindu.setLocationRelativeTo(this);

        JTextArea beskrivelsesFelt = new JTextArea(avtale.getBeskrivelse(), 2, 10);
        beskrivelsesFelt.setLineWrap(true);
        beskrivelsesFelt.setWrapStyleWord(true);

        JTextField datoFelt = new JTextField(avtale.getDatoOgTid().toLocalDate().toString());
        JTextField klokkeslettFelt = new JTextField(avtale.getDatoOgTid().toLocalTime().toString());

        JButton lagreKnapp = new JButton("Lagre");
        lagreKnapp.addActionListener(e -> {
            try {
                String beskrivelse = beskrivelsesFelt.getText();
                LocalDate nyDato = LocalDate.parse(datoFelt.getText());
                LocalTime nyKlokkeslett = LocalTime.parse(klokkeslettFelt.getText());
                LocalDateTime nyDatoOgTid = LocalDateTime.of(nyDato, nyKlokkeslett);

                Avtale nyAvtale = new Avtale(avtale.getAvtaleId(), nyDatoOgTid, beskrivelse, "Ingen", null);

                boolean oppdatert = avtaleLogikk.oppdaterAvtale(avtale, nyAvtale);

                if (oppdatert) {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Avtalen ble oppdatert.");
                    redigeringsVindu.dispose();
                    visAvtaler();
                } else {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Kunne ikke oppdatere avtalen. Prøv igjen.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(redigeringsVindu,
                        "Det oppstod en feil: " + ex.getMessage(),
                        "Feil",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel redigeringsPanel = new JPanel();
        redigeringsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        redigeringsPanel.add(new JLabel("Beskrivelse:"));
        redigeringsPanel.add(beskrivelsesFelt);
        redigeringsPanel.add(new JLabel("Dato (yyyy-MM-dd):"));
        redigeringsPanel.add(datoFelt);
        redigeringsPanel.add(new JLabel("Klokkeslett (HH:mm):"));
        redigeringsPanel.add(klokkeslettFelt);

        redigeringsVindu.add(redigeringsPanel, BorderLayout.CENTER);
        redigeringsVindu.add(lagreKnapp, BorderLayout.SOUTH);

        redigeringsVindu.setVisible(true);
    }


    private void slettAvtale(Avtale avtale) {
        int svar = JOptionPane.showConfirmDialog(this, "Er du sikker på at du vil" +
                " slette denne avtalen?", "Bekreft sletting", JOptionPane.YES_NO_OPTION);
        if (svar == JOptionPane.YES_OPTION) {
            avtaleLogikk.slettAvtale(avtale.getAvtaleId());
            JOptionPane.showMessageDialog(this, "Avtale slettet");
            visAvtaler();
        }
    }
}