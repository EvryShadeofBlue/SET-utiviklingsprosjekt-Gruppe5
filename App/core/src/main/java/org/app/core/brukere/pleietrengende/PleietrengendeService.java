package org.app.core.brukere.pleietrengende;

import org.app.core.brukere.pleietrengende.Pleietrengende;
import org.app.core.repository.PleietrengendeRepository;

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
