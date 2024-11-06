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

    public Pleietrengende leggTilPleietrengende(String fornavn, String etternavn, List<Parorende> parorende) {
        Pleietrengende nyPleietrengende = new Pleietrengende(0, fornavn, etternavn, parorende);
        pleietrengendeRepository.lagrePleietrengende(nyPleietrengende);
        return nyPleietrengende;
    }
}
