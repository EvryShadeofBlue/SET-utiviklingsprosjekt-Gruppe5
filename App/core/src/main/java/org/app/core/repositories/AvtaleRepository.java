package org.app.core.repositories;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AvtaleRepository {
    boolean opprettAvtale(Avtale avtale) throws NoSuchAlgorithmException;

    boolean oppdaterAvtale(Avtale avtale);

    boolean slettAvtale(int avtaleId);

    Avtale hentAvtale(int avtaleId);

    List<Avtale> hentAvtalerForParorende(Parorende parorende);
}
