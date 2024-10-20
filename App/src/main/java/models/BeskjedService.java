package models;

import models.Beskjed;
import repository.BeskjedRepository;

import java.time.LocalDateTime;


public class BeskjedService {
    private BeskjedRepository beskjedRepository;

    public BeskjedService(BeskjedRepository beskjedRepository) {

        this.beskjedRepository = beskjedRepository;
    }


    // Metode for å opprette beskjed
    public Beskjed opprettBeskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet) {
        Beskjed beskjed = new Beskjed(datoOgTid, beskrivelse, synligTidsenhet);
        beskjedRepository.oppretteBeskjed(beskjed);
        return beskjed;
    }

    // Metode for å oppdatere eksisterende beskjed
    public Beskjed oppdaterBeskjed(int beskjedId, Beskjed nyBeskjed) {
        // Henter beskjed
        Beskjed eksisterendeBeskjed = beskjedRepository.hentBeskjed(beskjedId);

        // Sjekker om beskjeden eksisterer
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
            beskjedRepository.oppdaterBeskjed(eksisterendeBeskjed);
            return eksisterendeBeskjed;
        }
        return null;
    }

    // Metode for å slette en beskjed
    public boolean slettBeskjed(int beskjedId) {
        Beskjed beskjed = beskjedRepository.hentBeskjed(beskjedId);
        if (beskjed != null) {
            beskjedRepository.slettBeskjed(beskjedId);
            return true;
        }
        return false;
    }

}
