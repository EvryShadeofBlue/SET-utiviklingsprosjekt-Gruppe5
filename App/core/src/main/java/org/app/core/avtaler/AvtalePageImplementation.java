package org.app.core.avtaler;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AvtalePageImplementation {
    private AvtaleService avtaleService;
    private AvtalePage avtalePage;

    public AvtalePageImplementation(AvtaleService avtaleService) {
        this.avtaleService = avtaleService;
    }

    /*
    public void opprettAvtale(String beskrivelse, String datoTekst, String klokkeslettTekst, String gjentakelse, String sluttDatoTekst, Parorende parorende, Pleietrengende pleietrengende) {
        try {
            LocalDate dato = LocalDate.parse(datoTekst);
            LocalTime klokkelsett = LocalTime.parse(klokkeslettTekst);
            LocalDateTime datoOgTid = LocalDateTime.of(dato, klokkelsett);
            LocalDateTime sluttDato = null;
            if (!sluttDatoTekst.isEmpty()) {
                LocalDate sluttDatoLocal = LocalDate.parse(sluttDatoTekst);
                sluttDato = sluttDatoLocal.atStartOfDay();
            }


            avtaleService.oppretteAvtale(datoOgTid, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);
            JOptionPane.showMessageDialog(null, "Avtale opprettet. ");

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kunne ikke opprette avtale. Vennligst prøv på nytt. ");
            e.printStackTrace();
        }
    }

     */

}
