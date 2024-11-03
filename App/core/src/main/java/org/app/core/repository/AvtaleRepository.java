package org.app.core.repository;

import org.app.core.models.Avtale;

import java.util.List;


public interface AvtaleRepository {
    void lagreAvtale(Avtale avtale);
    void oppdaterAvtale(Avtale avtale);
    Avtale hentAvtale(int avtaleId);

    void slettAvtale(int avtaleId);
    List<Avtale> hentAvtaleForParorende(int parorendeId);

}
