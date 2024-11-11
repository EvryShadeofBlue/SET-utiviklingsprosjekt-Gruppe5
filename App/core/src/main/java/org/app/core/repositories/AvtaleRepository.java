package org.app.core.repositories;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;

import java.util.List;

public interface AvtaleRepository {
    boolean opprettAvtale(Avtale avtale);

    boolean oppdaterAvtale(Avtale avtale);

    boolean slettAvtale(int avtaleId);

    Avtale hentAvtale(int avtaleId);

    //List<Avtale> hentAlleAvtaler();

    List<Avtale> hentAvtalerForParorende(Parorende parorende);
}
