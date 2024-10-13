package repository;

import model.Avtale;


public interface AvtaleRepository {
    void lagreAvtale(Avtale avtale);
    void oppdaterAvtale(Avtale avtale);
    Avtale hentAvtale(int avtaleId);

    void slettAvtale(int avtaleId);

}
