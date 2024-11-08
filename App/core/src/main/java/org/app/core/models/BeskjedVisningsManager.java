package org.app.core.models;


import org.app.core.services.BeskjedService;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BeskjedVisningsManager {
    private JPanel beskjedListePanel;
    private BeskjedService beskjedService;
    private Parorende parorende;
    private Pleietrengende pleietrengende;

    public BeskjedVisningsManager(JPanel beskjedListePanel, BeskjedService beskjedService, Parorende parorende, Pleietrengende pleietrengende) {
        this.beskjedListePanel = beskjedListePanel;
        this.beskjedService = beskjedService;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
    }

    public void lagreBeskjed(String beskrivelse, int synligTidsenhet, String datoStr, String klokkeslettStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime datoOgTid = LocalDateTime.parse(datoStr + " " + klokkeslettStr, formatter);

            beskjedService.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
            JOptionPane.showMessageDialog(null, "Beskjed opprettet");

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Det oppsto en feil under opprettelsen av beskjedet." +
                    "Vennligst prøv på nytt. ");
        }
    }


}
