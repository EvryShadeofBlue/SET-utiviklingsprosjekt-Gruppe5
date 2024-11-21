package org.app.gui.pages;

import org.app.core.files.FileHandler;
import org.app.core.models.Pleietrengende;
import org.app.core.logikk.LeggTilPleietrengendeLogikk;
import org.app.database.PleietrengendeDBImplementation;
import org.app.gui.utils.GUIUtils;

import javax.swing.*;
import java.awt.*;

public class LeggTilPleietrengendePage extends JFrame {
    private JTextField fornavnTekstFelt;
    private JTextField etternavnTekstFelt;
    private JButton lagreKnapp;
    private JButton tilbakeKnapp;
    private MainPage mainPage;
    private LeggTilPleietrengendeLogikk pleietrengendeService;
    private int parorendeId;

    public LeggTilPleietrengendePage(LeggTilPleietrengendeLogikk pleietrengendeService, int parorendeId, MainPage mainPage) {
        this.pleietrengendeService = pleietrengendeService;
        this.parorendeId = parorendeId;
        this.mainPage = mainPage;

        setupFrame();
        setupForm();
        setupButtons();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Legg til pleietrengende");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void setupForm() {
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
    }

    private void setupButtons() {
        GUIUtils.createButton("Legg til pleietrengende", new Dimension(200, 40), e -> handleLagreAction());
        GUIUtils.createButton("Tilbake", new Dimension(200, 40), e -> {
            this.dispose();
            mainPage.setVisible(true);
        });

        JPanel knappPanel = new JPanel();
        knappPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        knappPanel.add(lagreKnapp);
        knappPanel.add(tilbakeKnapp);
        add(knappPanel);
    }

    private void handleLagreAction() {
        String fornavn = fornavnTekstFelt.getText();
        String etternavn = etternavnTekstFelt.getText();

        if (fornavn.isEmpty() || etternavn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fornavn og etternavn kan ikke være tomme.");
            return;
        }

        Pleietrengende pleietrengende = new Pleietrengende(fornavn, etternavn);
        boolean erLeggetTil = pleietrengendeService.leggTilPleietrengende(pleietrengende, parorendeId);

        if (erLeggetTil) {
            JOptionPane.showMessageDialog(this, "Pleietrengende er lagt til.");
            int id = new PleietrengendeDBImplementation().hentPleietrengendeId(fornavn, etternavn);
            FileHandler.writeToFile("pleietrengende_id.txt", String.valueOf(id), this);

            mainPage.setPleietrengende(pleietrengende);
            mainPage.oppdaterPleietrengendeInfo(pleietrengende);
            mainPage.visHovedside();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Feil ved opprettelse av pleietrengende. Du har allerede en pleietrengende.");
        }
    }

}