package org.app.core.services;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.models.Avtale;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AvtaleService {
    private AvtaleRepository avtaleRepository;

    public AvtaleService(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }


    public boolean opprettAvtale(Avtale avtale) throws NoSuchAlgorithmException {
        if (avtale.getDatoOgTid() == null || avtale.getBeskrivelse() == null || avtale.getBeskrivelse().isEmpty()) {
            System.out.println("Beskrivelse og dato/tid er obligatoriske.");
            return false;
        }

        if (avtale.getSluttDato() != null && (avtale.getGjentakelse() == null || avtale.getGjentakelse().isEmpty())) {
            System.out.println("Hvis sluttdato er fylt ut, må gjentakelse velges.");
            return false;
        }

        if (avtale.getGjentakelse() != null && !avtale.getGjentakelse().isEmpty()) {
            if (avtale.getSluttDato() != null) {
                LocalDateTime currentDatoOgTid = avtale.getDatoOgTid();

                while (currentDatoOgTid.isBefore(avtale.getSluttDato()) || currentDatoOgTid.isEqual(avtale.getSluttDato())) {
                    Avtale nyAvtale = new Avtale(currentDatoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
                    avtaleRepository.opprettAvtale(nyAvtale);

                    switch (avtale.getGjentakelse()) {
                        case "daglig":
                            currentDatoOgTid = currentDatoOgTid.plusDays(1);
                            break;
                        case "ukentlig":
                            currentDatoOgTid = currentDatoOgTid.plusWeeks(1);
                            break;
                        case "månedlig":
                            currentDatoOgTid = currentDatoOgTid.plusMonths(1);
                            break;
                    }
                }
            } else {
                avtaleRepository.opprettAvtale(avtale);
            }
        } else {
            avtaleRepository.opprettAvtale(avtale);
        }
        return true;
    }

    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        if (nyAvtale.getSluttDato() != null && (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty())) {
            System.out.println("Hvis sluttdato er fylt ut, må gjentakelse velges.");
            return false;
        }
        if (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty()) {
            nyAvtale.setSluttDato(null);
        }
        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
        }
        if (nyAvtale.getDatoOgTid() != null) {
            eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
        }
        if (nyAvtale.getGjentakelse() != null && !nyAvtale.getGjentakelse().isEmpty()) {
            eksisterendeAvtale.setGjentakelse(nyAvtale.getGjentakelse());
        }
        if (nyAvtale.getSluttDato() != null) {
            eksisterendeAvtale.setSluttDato(nyAvtale.getSluttDato());
        }

        avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
        return true;
    }

    public boolean slettAvtale(int avtaleId) {
        Avtale avtale = avtaleRepository.hentAvtale(avtaleId);

        if (avtale == null) {
            System.out.println("Avtalen ble ikke funnet.");
            return false;
        }

        avtaleRepository.slettAvtale(avtaleId);

        return true;
    }

    public List<Avtale> hentAvtalerForParorende(Parorende parorende) {
        List<Avtale> avtaler = avtaleRepository.hentAvtalerForParorende(parorende);

        List<Avtale> sorterteAvtaler = avtaler.stream()
                .sorted((avtale1, avtale2) -> avtale2.getDatoOgTid().compareTo(avtale1.getDatoOgTid()))
                .collect(Collectors.toList());
        return sorterteAvtaler;
    }

    public List<Avtale> hentAlleAvtaler() {
        List<Avtale> avtaler = avtaleRepository.hentAlleAvtaler();

        List<Avtale> sorterteAvtaler = avtaler.stream()
                .sorted((avtale1, avtale2) -> avtale2.getDatoOgTid().compareTo(avtale1.getDatoOgTid()))
                .collect(Collectors.toList());
        return sorterteAvtaler;
    }

}



