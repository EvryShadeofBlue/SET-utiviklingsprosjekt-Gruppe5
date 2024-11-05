package org.app.core.repository;

import org.app.core.avtaler.Avtale;

import java.util.List;


public interface AvtaleRepository {
    void oppretteAvtale(Avtale avtale);
    void oppdaterAvtale(Avtale avtale);
    Avtale hentAvtale(int avtaleId);

    void slettAvtale(int avtaleId);
    List<Avtale> hentAvtaleForParorende(int parorendeId);

}
