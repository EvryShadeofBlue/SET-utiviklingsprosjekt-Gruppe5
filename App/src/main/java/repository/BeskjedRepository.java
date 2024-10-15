package repository;

import model.Beskjed;

public interface BeskjedRepository {
    // Metode for å opprette en beskjed
    void oppretteBeskjed(Beskjed beskjed);

    // Metode for å endre beskjed
    void endreBeskjed(int beskjedId, String Beskrivelse, int synligTidsenhet);
}

