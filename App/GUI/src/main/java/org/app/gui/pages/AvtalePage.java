package org.app.gui.pages;

import org.app.core.logikk.avtale.*;
import org.app.core.models.Avtale;
import org.app.core.models.AvtalePageImplementation;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private OpprettAvtaleLogikk opprettAvtaleLogikk;
    private OppdaterAvtaleLogikk oppdaterAvtaleLogikk;
    private SlettAvtaleLogikk slettAvtaleLogikk;
    private VisAvtaleLogikk visAvtaleLogikk;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private AvtalePageImplementation avtalePageImplementation;
    private MainPage mainPage;
    private JButton tilbakeKnapp;


        public AvtalePage(AvtaleLogikk avtaleLogikk, Parorende parorende, Pleietrengende pleietrengende, org.app.gui.pages.MainPage mainPage) {
        this.avtaleLogikk = avtaleLogikk;
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

        tilbakeKnapp = new JButton("Tilbake");
        JPanel tilbakePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        List<Avtale> avtaleListe = avtaleLogikk.VisAvtaleForParorende(parorende);
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

            Avtale avtale = new Avtale(datoOgTid, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);

            avtaleLogikk.OpprettAvtale(avtale);
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

    public boolean erGjentakende(Avtale avtale) {
            return true;
    }

    public void redigerAvtale(Avtale avtale) {
        // Sjekk om avtalen er gjentakende før vi åpner redigeringsvinduet
        if (avtale.getGjentakelse() != null && !avtale.getGjentakelse().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "En gjentakende avtale kan ikke redigeres. Den kan bare slettes.",
                    "Feil",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame redigeringsVindu = new JFrame("Rediger Avtale");
        redigeringsVindu.setSize(400, 300);
        redigeringsVindu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        redigeringsVindu.setLocationRelativeTo(this);

        // Bruk eksisterende beskrivelse fra avtalen
        JTextArea beskrivelsesFelt = new JTextArea(avtale.getBeskrivelse(), 2, 10);
        beskrivelsesFelt.setLineWrap(true);
        beskrivelsesFelt.setWrapStyleWord(true);

        // Bruk eksisterende dato og klokkeslett fra avtalen
        LocalDate dato = avtale.getDatoOgTid().toLocalDate();
        LocalTime klokkeslett = avtale.getDatoOgTid().toLocalTime();

        JTextField datoFelt = new JTextField(dato.toString());
        JTextField klokkeslettFelt = new JTextField(klokkeslett.toString());

        JComboBox<String> gjentakelsesFelt = new JComboBox<>(new String[] {"Ingen", "Daglig", "Ukentlig", "Månedlig"});
// Sett valgt verdi i JComboBox basert på gjentakelse fra avtalen
        if (avtale.getGjentakelse() != null && !avtale.getGjentakelse().isEmpty()) {
            gjentakelsesFelt.setSelectedItem(avtale.getGjentakelse());
        } else {
            gjentakelsesFelt.setSelectedItem("Ingen");
        }

// Håndter sluttDato-feltet
        JTextField sluttDatoFelt = new JTextField();
        if (avtale.getSluttDato() != null) {
            sluttDatoFelt.setText(avtale.getSluttDato().toLocalDate().toString());
        }


        JButton lagreKnapp = new JButton("Lagre");
        lagreKnapp.addActionListener(e -> {
            try {
                // Hent de nye verdiene fra inputfeltene
                String beskrivelse = beskrivelsesFelt.getText();
                LocalDate nyDato = LocalDate.parse(datoFelt.getText());
                LocalTime nyKlokkeslett = LocalTime.parse(klokkeslettFelt.getText());
                LocalDateTime datoOgTid = LocalDateTime.of(nyDato, nyKlokkeslett);
                String gjentakelse = (String) gjentakelsesFelt.getSelectedItem();
                LocalDateTime sluttDato = null;

                String sluttDatoTekst = sluttDatoFelt.getText();
                if (!sluttDatoTekst.isEmpty()) {
                    LocalDate sluttDatoLocal = LocalDate.parse(sluttDatoTekst);
                    sluttDato = sluttDatoLocal.atStartOfDay();
                }

                // Lag en ny Avtale med de oppdaterte verdiene
                Avtale nyAvtale = new Avtale(avtale.getAvtaleId(), datoOgTid, beskrivelse, gjentakelse, sluttDato);

                // Prøv å oppdatere avtalen
                boolean oppdatert = avtaleLogikk.OppdaterAvtale(avtale, nyAvtale);

                if (oppdatert) {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Avtale oppdatert");
                    visAvtaler();  // Vis oppdaterte avtaler
                    redigeringsVindu.dispose();  // Lukk vinduet
                } else {
                    JOptionPane.showMessageDialog(redigeringsVindu, "Kunne ikke oppdatere avtale. Vennligst prøv på nytt.");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(redigeringsVindu, "Det oppsto en feil under endringen." + exception.getMessage());
                exception.printStackTrace();
            }
        });

        // Legg til komponentene i redigeringsvinduet
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(new JScrollPane(beskrivelsesFelt));
        panel.add(createInputPanel("Dato (yyyy-MM-dd):", datoFelt));
        panel.add(createInputPanel("Klokkeslett (HH:mm):", klokkeslettFelt));
        panel.add(createInputPanel("Gjentakelse:", gjentakelsesFelt));
        panel.add(createInputPanel("Sluttdato (yyyy-MM-dd):", sluttDatoFelt));
        panel.add(lagreKnapp);

        redigeringsVindu.add(panel);
        redigeringsVindu.setVisible(true);
    }





    public void slettAvtale(Avtale avtale) {
        int svar = JOptionPane.showConfirmDialog(this, "Er du sikker på at du vil slette denne avtalen?", "Bekreft sletting", JOptionPane.YES_NO_OPTION);
        if (svar == JOptionPane.YES_OPTION) {
            avtaleLogikk.SlettAvtale(avtale.getAvtaleId());
            JOptionPane.showMessageDialog(this, "Avtale slettet");
            visAvtaler();
        }
    }
}