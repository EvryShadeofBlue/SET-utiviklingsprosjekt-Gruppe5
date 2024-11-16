package org.app.core.logikk;

import org.app.core.models.Avtale;
import org.app.core.repositories.AvtaleRepository;

import java.time.LocalDateTime;

public class GjentakendeAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public GjentakendeAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }
    public void opprettGjentakendeAvtaler(Avtale eksisterendeAvtale) {
        LocalDateTime datoTid = eksisterendeAvtale.getDatoOgTid();
        LocalDateTime sluttDato = eksisterendeAvtale.getSluttDato();
        String gjentakelse = eksisterendeAvtale.getGjentakelse();

        while (datoTid.isBefore(sluttDato)) {
            Avtale nyOpprettetAvtale = new Avtale(datoTid, eksisterendeAvtale.getBeskrivelse(),
                    eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
            avtaleRepository.opprettAvtale(nyOpprettetAvtale);

            if ("daglig".equalsIgnoreCase(gjentakelse)) {
                datoTid = datoTid.plusDays(1);
            } else if ("ukentlig".equalsIgnoreCase(gjentakelse)) {
                datoTid = datoTid.plusWeeks(1);
            } else if ("månedlig".equalsIgnoreCase(gjentakelse)) {
                datoTid = datoTid.plusMonths(1);
            }
            else {
                throw new IllegalArgumentException("Ugyldig gjentakelsestype: " + gjentakelse);
            }
        }
    }
}
