package org.app.core.logikk.avtale;

import org.app.core.models.Parorende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.models.Avtale;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AvtaleLogikk {
    private AvtaleRepository avtaleRepository;
    private OpprettAvtaleLogikk opprettAvtaleLogikk;
    private OppdaterAvtaleLogikk oppdaterAvtaleLogikk;
    private SlettAvtaleLogikk slettAvtaleLogikk;
    private VisAvtaleLogikk visAvtaleLogikk;

    public AvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
        this.opprettAvtaleLogikk = new OpprettAvtaleLogikk(avtaleRepository);
        this.oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(avtaleRepository);
        this.slettAvtaleLogikk = new SlettAvtaleLogikk(avtaleRepository);
        this.visAvtaleLogikk = new VisAvtaleLogikk(avtaleRepository);
    }

    public boolean opprettAvtale(Avtale avtale) throws NoSuchAlgorithmException {
        return opprettAvtaleLogikk.opprettAvtale(avtale);
    }

    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        return oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
    }

    public boolean slettAvtale(int avtaleId) {
        return slettAvtaleLogikk.slettAvtale(avtaleId);
    }

    public List<Avtale> visAvtaleForParorende(Parorende parorende) {
        return visAvtaleLogikk.hentAvtalerForParorende(parorende);
    }

}



