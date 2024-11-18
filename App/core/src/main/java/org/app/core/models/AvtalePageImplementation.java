package org.app.core.models;


import org.app.core.logikk.avtale.AvtaleLogikk;

public class AvtalePageImplementation {
    private AvtaleLogikk avtaleService;

    public AvtalePageImplementation(AvtaleLogikk avtaleService) {
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
