package org.app.core.logikk.beskjed;

import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.BeskjedRepository;

import java.time.LocalDateTime;

public class OpprettBeskjedLogikk {
    private BeskjedRepository beskjedRepository;

    public OpprettBeskjedLogikk(BeskjedRepository beskjedRepository) {
        this.beskjedRepository = beskjedRepository;
    }

    public Beskjed opprettBeskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Parorende parorende, Pleietrengende pleietrengende) {
        if (datoOgTid == null) {
            throw new IllegalArgumentException("Dato og tid kan ikke være null.");
        }
        if (beskrivelse == null || beskrivelse.isEmpty()) {
            throw new IllegalArgumentException("Beskrivelse kan ikke være tom.");
        }
        if (synligTidsenhet <= 0) {
            throw new IllegalArgumentException("Synlig tidsenhet må være et positivt tall.");
        }
        if (parorende == null) {
            throw new IllegalArgumentException("Pårørende kan ikke være null.");
        }
        if (pleietrengende == null) {
            throw new IllegalArgumentException("Pleietrengende kan ikke være null.");
        }

        Beskjed beskjed = new Beskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);

        try {
            beskjedRepository.oppretteBeskjed(beskjed);
            System.out.println("Beskjed opprettet: " + beskjed.getBeskrivelse());
        } catch (Exception e) {
            throw new RuntimeException("Feil ved opprettelse av beskjed: " + e.getMessage(), e);
        }
        return beskjed;
    }
}
