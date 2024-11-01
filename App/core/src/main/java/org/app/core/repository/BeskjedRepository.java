package org.app.core.repository;

import org.app.core.models.Beskjed;


public interface BeskjedRepository {

    void oppretteBeskjed(Beskjed beskjed);

    void oppdaterBeskjed(Beskjed beskjed);

    void slettBeskjed(int beskjedId);

    Beskjed hentBeskjed(int beskjedId);
}

