package org.app.gui.pages;

import org.app.core.models.Pleietrengende;
import org.app.core.services.PleietrengendeService;
import org.app.database.PleietrengendeDBImplementation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LeggTilPleietrengendePage extends JFrame {
    private JTextField fornavnTekstFelt;
    private JTextField etternavnTekstFelt;
    private JButton lagreKnapp;
    private JButton tilbakeKnapp;
    private MainPage mainPage;
    private PleietrengendeService pleietrengendeService;
    private int parorendeId;

    public LeggTilPleietrengendePage(PleietrengendeService pleietrengendeService, int parorendeId, MainPage mainPage) {
        this.pleietrengendeService = pleietrengendeService;
        this.parorendeId = parorendeId;
        this.mainPage = mainPage;

        setTitle("Legg til pleietrengende");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel fornavnLabel = new JLabel("Fornavn:");
        fornavnTekstFelt = new JTextField(20);
        fornavnTekstFelt.setMaximumSize(fornavnTekstFelt.getPreferredSize());
        add(fornavnLabel);
        add(fornavnTekstFelt);

        add(Box.createVerticalStrut(10));

        JLabel etternavnLabel = new JLabel("Etternavn");
        etternavnTekstFelt = new JTextField(20);
        etternavnTekstFelt.setMaximumSize(etternavnTekstFelt.getPreferredSize());
        add(etternavnLabel);
        add(etternavnTekstFelt);

        add(Box.createVerticalStrut(20));

        lagreKnapp = new JButton("Legg til pleietrengende");
        lagreKnapp.setPreferredSize(new Dimension(200, 40));
        lagreKnapp.addActionListener(e -> {
            String fornavn = fornavnTekstFelt.getText();
            String etternavn = etternavnTekstFelt.getText();

            if (fornavn.isEmpty() || etternavn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fornavn og etternavn" +
                        " kan ikke være kan ikke være tomme.");
                return;
            }

            Pleietrengende pleietrengende = new Pleietrengende(fornavn, etternavn);

            boolean erLeggetTil = pleietrengendeService.leggTilPleietrengende(pleietrengende, parorendeId);

            if (erLeggetTil) {
                JOptionPane.showMessageDialog(this, "Pleietrengende er lagt til.");

                PleietrengendeDBImplementation db = new PleietrengendeDBImplementation();
                int id = db.hentPleietrengendeId(fornavn, etternavn);
                writePleietrengendeIdToFile(id);

                mainPage.oppdaterPleietrengendeInfo(pleietrengende);
                mainPage.visHovedside();
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Feil ved opprettelse av pleietrengende." +
                        " Du har allerede en pleietrengende.");
            }

        });

        tilbakeKnapp = new JButton("Tilbake");
        tilbakeKnapp.setPreferredSize(new Dimension(200, 40));
        tilbakeKnapp.addActionListener(e -> {
            this.dispose();
            mainPage.setVisible(true);
        });

        JPanel knappPanel = new JPanel();
        knappPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        knappPanel.add(lagreKnapp);
        knappPanel.add(tilbakeKnapp);
        add(knappPanel);

        setVisible(true);
    }

    private void writePleietrengendeIdToFile(int pleietrengende_id) {
        try {
            String currentDir = System.getProperty("user.dir");
            File parentDir = new File(currentDir).getParentFile();
            File file = new File(parentDir, "pleietrengende_id.txt");

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(String.valueOf(pleietrengende_id));
            }

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Kunne ikke lagre pleietrengende" +
                        " ID til fil.", "Feil", JOptionPane.ERROR_MESSAGE);
            }
    }
}