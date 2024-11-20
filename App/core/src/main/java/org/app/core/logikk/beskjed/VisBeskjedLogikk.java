package org.app.core.logikk.beskjed;

import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.repositories.BeskjedRepository;

import java.util.List;

public class VisBeskjedLogikk {
    private BeskjedRepository beskjedRepository;

    public VisBeskjedLogikk(BeskjedRepository beskjedRepository) {
        this.beskjedRepository = beskjedRepository;
    }

    public List<Beskjed> hentBeskjedForParorende(Parorende parorende) {
        List<Beskjed> beskjeder = beskjedRepository.hentBeskjedForParorende(parorende.getParorendeId());
        beskjeder.sort((b1, b2) -> b2.getDatoOgTid().compareTo(b1.getDatoOgTid()));
        return beskjeder;

    }
}
