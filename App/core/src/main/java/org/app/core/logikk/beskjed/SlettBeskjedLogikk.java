package org.app.core.logikk.beskjed;

import org.app.core.repositories.BeskjedRepository;

public class SlettBeskjedLogikk {
    private BeskjedRepository beskjedRepository;

    public SlettBeskjedLogikk(BeskjedRepository beskjedRepository) {
        this.beskjedRepository = beskjedRepository;
    }

    public boolean slettBeskjed(int beskjedId) {
        try {
            beskjedRepository.slettBeskjed(beskjedId);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
