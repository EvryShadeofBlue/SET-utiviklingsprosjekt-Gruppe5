package org.app.core.services;

import org.app.core.models.Pleietrengende;
import org.app.core.models.Parorende;
import org.app.core.repositories.PleietrengendeRepository;

public class PleietrengendeService {
    private PleietrengendeRepository pleietrengendeRepository;

    public PleietrengendeService(PleietrengendeRepository pleietrengendeRepository) {
        this.pleietrengendeRepository = pleietrengendeRepository;
    }

    public boolean leggTilPleietrengende(Pleietrengende pleietrengende, int parorendeId) {
        Pleietrengende eksisterendePleietrengende = pleietrengendeRepository.finnPleietrengendeAvParorende(parorendeId);
        if (eksisterendePleietrengende == null) {
            if (pleietrengende.getParorende() == null) {
                pleietrengende.setParorende(new Parorende());
            }
            pleietrengende.getParorende().setParorendeId(parorendeId);
            pleietrengendeRepository.lagrePleietrengende(pleietrengende);
            return true;
        }
        return false;
    }


}
