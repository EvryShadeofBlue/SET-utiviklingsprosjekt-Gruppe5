package org.app.core.logikk.avtale;

import org.app.core.repositories.AvtaleRepository;
import org.app.core.models.Avtale;

public class SlettAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public SlettAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public boolean slettAvtale(int avtaleId) {
        Avtale avtale = avtaleRepository.hentAvtale(avtaleId);

        if (avtale == null) {
            System.out.println("Avtalen ble ikke funnet.");
            return false;
        }

        avtaleRepository.slettAvtale(avtaleId);
        return true;
    }
}
