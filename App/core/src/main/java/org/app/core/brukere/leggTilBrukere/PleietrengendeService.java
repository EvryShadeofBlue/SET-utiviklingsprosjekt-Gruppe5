package org.app.core.brukere.leggTilBrukere;

import org.app.core.brukere.Parorende;
import org.app.core.brukere.Pleietrengende;
import org.app.core.repository.PleietrengendeRepository;

import java.util.List;

public class PleietrengendeService {
    private PleietrengendeRepository pleietrengendeRepository;

    public PleietrengendeService(PleietrengendeRepository pleietrengendeRepository) {
        this.pleietrengendeRepository = pleietrengendeRepository;
    }

    public boolean leggTilPleietrengende(Pleietrengende pleietrengende, int parorendeId) {
        Pleietrengende eksisterendePleietrengende = pleietrengendeRepository.finnPleietrengendeAvParorende(parorendeId);
        if (eksisterendePleietrengende != null) {
            return false;
        }
        pleietrengendeRepository.lagrePleietrengende(pleietrengende);
        return true;
    }

}
