package models;

import models.Beskjed;
import repository.BeskjedRepository;

public class BeskjedService {
    private BeskjedRepository beskjedRepository;

    public BeskjedService(BeskjedRepository beskjedRepository) {
        this.beskjedRepository = beskjedRepository;
    }

    public void opprettBeskjed(String beskrivelse, int synligTidsenhet) {
        Beskjed beskjed = new Beskjed(beskrivelse, synligTidsenhet);
        beskjedRepository.oppretteBeskjed(beskjed);
    }
}
