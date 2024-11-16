package org.app.core.logikk;

import org.app.core.models.Avtale;
import org.app.core.repositories.AvtaleRepository;

import java.time.LocalDateTime;

public class OpprettAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public OpprettAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public boolean opprettAvtale(Avtale avtale) {
        if ("daglig".equalsIgnoreCase(avtale.getGjentakelse())) {
            return opprettDagligeAvtaler(avtale);
        } else if ("ukentlig".equalsIgnoreCase(avtale.getGjentakelse())) {
            return opprettUkentligeAvtaler(avtale);
        } else if ("månedlig".equalsIgnoreCase(avtale.getGjentakelse())) {
            return opprettMånedligeAvtaler(avtale);
        }
        return avtaleRepository.opprettAvtale(avtale);
    }

    private boolean opprettDagligeAvtaler(Avtale avtale) {
        LocalDateTime datoOgTid = avtale.getDatoOgTid();
        LocalDateTime sluttdato = avtale.getSluttDato();
        boolean resultat = true;

        while (datoOgTid.isBefore(sluttdato) || datoOgTid.isEqual(sluttdato)) {
            Avtale nyAvtale = new Avtale(datoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
            resultat &= avtaleRepository.opprettAvtale(nyAvtale);
            datoOgTid = datoOgTid.plusDays(1);
        }
        return resultat;
    }

    private boolean opprettUkentligeAvtaler(Avtale avtale) {
        LocalDateTime datoOgTid = avtale.getDatoOgTid();
        LocalDateTime sluttdato = avtale.getSluttDato();
        boolean resultat = true;
        LocalDateTime justertSluttdato = sluttdato.isAfter(datoOgTid) ? sluttdato : sluttdato.plusDays(1);

        while (!datoOgTid.isAfter(justertSluttdato)) {
            Avtale nyAvtale = new Avtale(datoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
            resultat &= avtaleRepository.opprettAvtale(nyAvtale);
            datoOgTid = datoOgTid.plusWeeks(1);
        }
        return resultat;
    }

    private boolean opprettMånedligeAvtaler(Avtale avtale) {
        LocalDateTime datoOgTid = avtale.getDatoOgTid();
        LocalDateTime sluttdato = avtale.getSluttDato();
        boolean resultat = true;

        LocalDateTime justertSluttdato = sluttdato.toLocalDate().atStartOfDay().plusDays(1);

        while (datoOgTid.isBefore(justertSluttdato)) {
            Avtale nyAvtale = new Avtale(datoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
            resultat &= avtaleRepository.opprettAvtale(nyAvtale);
            datoOgTid = datoOgTid.plusMonths(1);
        }
        return resultat;
    }
}
