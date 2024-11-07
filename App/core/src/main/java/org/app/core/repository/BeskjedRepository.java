package org.app.core.repository;

import org.app.core.beskjeder.Beskjed;

import java.util.List;


public interface BeskjedRepository {

    void oppretteBeskjed(Beskjed beskjed);

    void oppdaterBeskjed(Beskjed beskjed);

    void slettBeskjed(int beskjedId);

    Beskjed hentBeskjed(int beskjedId);
    List<Beskjed> hentBeskjedForParorende(int parorendeId);
}

