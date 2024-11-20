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
            if (nyBeskjed.getDatoOgTid() != null) {
                eksisterendeBeskjed.setDatoOgTid(nyBeskjed.getDatoOgTid());
            }
            if (nyBeskjed.getBeskrivelse() != null) {
                eksisterendeBeskjed.setBeskrivelse(nyBeskjed.getBeskrivelse());
            }
            if (nyBeskjed.getSynligTidsenhet() > 0) {
                eksisterendeBeskjed.setSynligTidsenhet(nyBeskjed.getSynligTidsenhet());
            }
            if (nyBeskjed.getParorende() != null) {
                eksisterendeBeskjed.setParorende(nyBeskjed.getParorende());
            }
            if (nyBeskjed.getPleietrengende() != null) {
                eksisterendeBeskjed.setPleietrengende(nyBeskjed.getPleietrengende());
            }

            beskjedRepository.oppdaterBeskjed(eksisterendeBeskjed);
            return eksisterendeBeskjed;
        }
        return null;
    }
}
