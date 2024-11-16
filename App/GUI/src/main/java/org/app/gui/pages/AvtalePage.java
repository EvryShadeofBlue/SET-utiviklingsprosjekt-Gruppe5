package org.app.gui.pages;

import org.app.core.models.Avtale;
import org.app.core.models.AvtalePageImplementation;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.services.AvtaleService;


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
    private AvtaleService avtaleService;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private AvtalePageImplementation avtalePageImplementation;
    private org.app.gui.pages.MainPage mainPage;
    private JButton tilbakeKnapp;


    public AvtalePage(AvtaleService avtaleService, Parorende parorende, Pleietrengende pleietrengende, org.app.gui.pages.MainPage mainPage) {
        this.avtaleService = avtaleService;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
        this.mainPage = mainPage;

        setTitle("Avtaler");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel hovedInnholdPanel = new JPanel();
        hovedInnholdPanel.setLayout(new BoxLayout(hovedInnholdPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(hovedInnholdPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 1, 10, 10));

        beskrivelsesFelt = new JTextArea(2, 10);
        beskrivelsesFelt.setLineWrap(true);
        beskrivelsesFelt.setWrapStyleWord(true);

        JScrollPane beskrivelsesPanel = new JScrollPane(beskrivelsesFelt);
        beskrivelsesPanel.setBorder(BorderFactory.createTitledBorder("Beskrivelse"));

        datoFelt = new JTextField();
        JPanel datoPanel = createInputPanel("Dato (yyyy-MM-dd): ", datoFelt);

        klokkeslettFelt = new JTextField();
        JPanel klokkeslettPanel = createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt);

        String[] gjentakelseAlternativer = {"Ingen", "Daglig", "Ukentlig", "Månedlig"};
        gjentakelseFelt = new JComboBox<>(gjentakelseAlternativer);
        JPanel gjentakelsesPanel = createInputPanel("Gjentakelse: ", gjentakelseFelt);

        sluttDatoFelt = new JTextField();
        JPanel sluttDatoPanel = createInputPanel("Slutt dato (yyy-MM-dd)", sluttDatoFelt);

        lagreKnapp = new JButton("Lagre");
        lagreKnapp.setPreferredSize(new Dimension(100, 30));
        lagreKnapp.addActionListener(e -> opprettAvtale());

        JPanel tilbakePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton tilbakeKnapp = new JButton("Tilbake");
        tilbakeKnapp.setPreferredSize(new Dimension(100, 30));
        tilbakeKnapp.addActionListener(e -> {
            this.dispose();
            mainPage.setVisible(true);
        });

        tilbakePanel.add(tilbakeKnapp);

        setLayout(new BorderLayout());
        add(tilbakePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        inputPanel.add(beskrivelsesPanel);
        inputPanel.add(datoPanel);
        inputPanel.add(klokkeslettPanel);
        inputPanel.add(gjentakelsesPanel);
        inputPanel.add(sluttDatoPanel);
        inputPanel.add(lagreKnapp);
        inputPanel.add(tilbakeKnapp);
        add(inputPanel, BorderLayout.NORTH);

        avtaleListePanel = new JPanel();
        avtaleListePanel.setLayout(new BoxLayout(avtaleListePanel, BoxLayout.Y_AXIS));

        JScrollPane avtaleScrollPane = new JScrollPane(avtaleListePanel);
        avtaleScrollPane.setPreferredSize(new Dimension(400, 300));
        avtaleScrollPane.setBorder(BorderFactory.createTitledBorder("Opprettede avtaler"));
        add(avtaleScrollPane, BorderLayout.CENTER);

        visAvtaler();
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

    private void visAvtaler() {
        avtaleListePanel.removeAll();
        List<Avtale> avtaleListe = avtaleService.hentAvtaleForParorened(parorende);
        DateTimeFormatter datoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tidFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Avtale avtale : avtaleListe) {
            JPanel avtalePanel = new JPanel();
            avtalePanel.setLayout(new BoxLayout(avtalePanel, BoxLayout.Y_AXIS));
            avtalePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            avtalePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            String dato = avtale.getDatoOgTid().format(datoFormatter);
            String tid = avtale.getDatoOgTid().format(tidFormatter);

            JLabel datoLabel = new JLabel("Dato: " + dato);
            datoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel tidLabel = new JLabel("Klokkelsett: " + tid);
            tidLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel beskrivelsesLabel = new JLabel("Beskrivelse: " + avtale.getBeskrivelse());
            beskrivelsesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            avtalePanel.add(datoLabel);
            avtalePanel.add(tidLabel);
            avtalePanel.add(beskrivelsesLabel);

            JPanel knapperPanel = new JPanel();
            knapperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JButton slettKnapp = new JButton("Slett");
            slettKnapp.addActionListener(e -> slettAvtale(avtale));
            knapperPanel.add(slettKnapp);

            JButton redigerKnapp = new JButton("Rediger");
            redigerKnapp.addActionListener(e -> redigerAvtale(avtale));
            knapperPanel.add(redigerKnapp);

            avtalePanel.add(knapperPanel);
            avtaleListePanel.add(avtalePanel);
        }
        avtaleListePanel.revalidate();
        avtaleListePanel.repaint();
    }

    public void opprettAvtale() {
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

            avtaleService.oppretteAvtale(datoOgTid, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);
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

    public void redigerAvtale(Avtale avtale) {
        JFrame redigeringsVindu = new JFrame("Rediger Avale");
        redigeringsVindu.setSize(400, 300);
        redigeringsVindu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        redigeringsVindu.setLocationRelativeTo(this);

        JTextArea beskrivelsesFelt = new JTextArea(avtale.getBeskrivelse(), 2, 10);
        beskrivelsesFelt.setLineWrap(true);
        beskrivelsesFelt.setWrapStyleWord(true);

        LocalDate dato = avtale.getDatoOgTid().toLocalDate();
        LocalTime klokkeslett = avtale.getDatoOgTid().toLocalTime();

        JTextField datoFelt = new JTextField(dato.toString());
        JTextField klokkeslettFelt = new JTextField(klokkeslett.toString());

        JComboBox<String> gjentakelsesFelt = new JComboBox<>(new String[] {"Ingen", "Daglig", "Ukentlig", "Månedlig"});
        gjentakelsesFelt.setSelectedItem(avtale.getGjentakelse() != null ? avtale.getGjentakelse() : "Ingen");

        JTextField sluttDatoFelt = new JTextField(avtale.getSluttDato() != null ? avtale.getSluttDato().toString() : "");

        JButton lagreKnapp = new JButton("Lagre");
        lagreKnapp.addActionListener(e -> {
            try {
                String beskrivelse = beskrivelsesFelt.getText();
                LocalDate nyDato = LocalDate.parse(datoFelt.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime nyKlokkeslett = LocalTime.parse(klokkeslettFelt.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                LocalDateTime datoOgTid = LocalDateTime.of(nyDato, nyKlokkeslett);
                String gjentakelse = (String) gjentakelsesFelt.getSelectedItem();
                LocalDateTime sluttDato = null;

                String sluttDatoTekst = sluttDatoFelt.getText();
                if (!sluttDatoTekst.isEmpty()) {
                    try {
                        LocalDate sluttDatoLocal = LocalDate.parse(sluttDatoTekst, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        sluttDato = sluttDatoLocal.atStartOfDay();
                        System.out.println("Parsed sluttDato: " + sluttDato);
                    } catch (DateTimeParseException es) {
                        System.err.println("Error parsing sluttDato: " + es.getMessage());
                    }
                }


                Avtale nyAvtale = new Avtale(avtale.getAvtaleId(), datoOgTid, beskrivelse, gjentakelse, sluttDato);
                Avtale oppdatertAvtale = avtaleService.oppdaterAvtale(nyAvtale);

                if (oppdatertAvtale != null) {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Avtale oppdatert");
                    visAvtaler();
                    redigeringsVindu.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Kunne ikke oppdatere avtale. " +
                            "Vennligst prøv på nytt");
                }
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(redigeringsVindu, "Det oppsto en feil endringen." + exception.getMessage());
                exception.printStackTrace();
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(new JScrollPane(beskrivelsesFelt));
        panel.add(createInputPanel("Dato (yyyy-MM-dd):", datoFelt));
        panel.add(createInputPanel("Klokkeslett (HH:mm):", klokkeslettFelt));
        panel.add(createInputPanel("Gjentakelse:", gjentakelsesFelt));
        panel.add(createInputPanel("sluttdato (yyyy-MM-dd):", sluttDatoFelt));
        panel.add(lagreKnapp);

        redigeringsVindu.add(panel);
        redigeringsVindu.setVisible(true);
    }

    public void slettAvtale(Avtale avtale) {
        int svar = JOptionPane.showConfirmDialog(this, "Er du sikker på at du vil slette denne avtalen?", "Bekreft sletting", JOptionPane.YES_NO_OPTION);
        if (svar == JOptionPane.YES_OPTION) {
            avtaleService.slettAvtale(avtale.getAvtaleId());
            JOptionPane.showMessageDialog(this, "Avtale slettet");
            visAvtaler();
        }
    }
}