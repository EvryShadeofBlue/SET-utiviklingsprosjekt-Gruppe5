package repository;

import models.Beskjed;

public interface BeskjedRepository {
    // Metode for å opprette en beskjed
    void oppretteBeskjed(Beskjed beskjed);

    // Metode for å endre beskjed
    void oppdaterBeskjed(int beskjed, String Beskrivelse, int synligTidsenhet);

    // Metode for å slette en beskjed
    void slettBeskjed(int beskjedId);

    // Metode for å hente en beskjed
    // Denne metoden kan vi senere ved implementasjo vurdere om det er fornuftig
    Beskjed hentBeskjed(int beskjedId);
}

