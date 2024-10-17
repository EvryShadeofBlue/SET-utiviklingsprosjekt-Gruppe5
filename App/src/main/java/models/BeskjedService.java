package models;

import models.Beskjed;
import repository.BeskjedRepository;

public class BeskjedService {
    private BeskjedRepository beskjedRepository;

    public BeskjedService(BeskjedRepository beskjedRepository) {

        this.beskjedRepository = beskjedRepository;
    }


    // Metode for å opprette beskjed
    public Beskjed opprettBeskjed(String beskrivelse, int synligTidsenhet) {
        Beskjed beskjed = new Beskjed(beskrivelse, synligTidsenhet);
        beskjedRepository.oppretteBeskjed(beskjed);
        return beskjed;
    }

    // Metode for å oppdatere eksisterende beskjed
    public boolean oppdaterBeskjed(int beskjedId, String nyBeskrivelse, int nySynligTidsenhet) {
        // Henter beskjed
        Beskjed eksisterendeBeskjed = beskjedRepository.hentBeskjed(beskjedId);

        // Sjekker om beskjeden eksisterer
        if (eksisterendeBeskjed != null) {
            if (nyBeskrivelse != null) {
                eksisterendeBeskjed.setBeskrivelse(nyBeskrivelse);
            }
            if (nySynligTidsenhet > 0) {
                eksisterendeBeskjed.setSynligTidsenhet(nySynligTidsenhet);
            }
            beskjedRepository.oppdaterBeskjed(beskjedId, eksisterendeBeskjed.getBeskrivelse(), eksisterendeBeskjed.getSynligTidsenhet());
            return true;
        }
        return false;
    }

}
