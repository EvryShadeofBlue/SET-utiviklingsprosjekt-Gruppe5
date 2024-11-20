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
        Beskjed beskjed = new Beskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
        beskjedRepository.oppretteBeskjed(beskjed);
        return beskjed;
    }
}
