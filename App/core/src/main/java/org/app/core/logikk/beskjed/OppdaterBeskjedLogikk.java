package org.app.core.logikk.beskjed;

import org.app.core.models.Beskjed;
import org.app.core.repositories.BeskjedRepository;

public class OppdaterBeskjedLogikk {
    private BeskjedRepository beskjedRepository;

    public OppdaterBeskjedLogikk(BeskjedRepository beskjedRepository) {
        this.beskjedRepository = beskjedRepository;
    }

    public Beskjed oppdaterBeskjed(Beskjed nyBeskjed) {
        Beskjed eksisterendeBeskjed = beskjedRepository.hentBeskjed(nyBeskjed.getBeskjedId());

        if (eksisterendeBeskjed != null) {
            // Valider feltene som kan oppdateres
            if (nyBeskjed.getDatoOgTid() != null) {
                eksisterendeBeskjed.setDatoOgTid(nyBeskjed.getDatoOgTid());
            } else {
                throw new IllegalArgumentException("Dato og tid må være satt ved oppdatering.");
            }

            if (nyBeskjed.getBeskrivelse() != null && !nyBeskjed.getBeskrivelse().isEmpty()) {
                eksisterendeBeskjed.setBeskrivelse(nyBeskjed.getBeskrivelse());
            } else {
                throw new IllegalArgumentException("Beskrivelsen kan ikke være tom ved oppdatering.");
            }

            if (nyBeskjed.getSynligTidsenhet() > 0) {
                eksisterendeBeskjed.setSynligTidsenhet(nyBeskjed.getSynligTidsenhet());
            } else {
                throw new IllegalArgumentException("Synlig tidsenhet må være større enn 0.");
            }

            // Oppdater i databasen
            beskjedRepository.oppdaterBeskjed(eksisterendeBeskjed);
            return eksisterendeBeskjed;
        }

        throw new IllegalArgumentException("Beskjed med angitt ID finnes ikke.");
    }
}
