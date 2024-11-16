package org.app.core.logikk;

import org.app.core.models.Avtale;
import org.app.core.repositories.AvtaleRepository;

import java.time.LocalDateTime;

public class OppdaterAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public OppdaterAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        // Sjekk om avtalen har gjentakelse
        if (eksisterendeAvtale.getGjentakelse() != null && !eksisterendeAvtale.getGjentakelse().isEmpty()) {
            System.out.println("Avtaler med gjentakelse kan ikke oppdateres.");
            return false;
        }

        // Valider datoer
        if (nyAvtale.getDatoOgTid() != null && nyAvtale.getSluttDato() != null &&
                nyAvtale.getDatoOgTid().isAfter(nyAvtale.getSluttDato())) {
            System.out.println("Startdato kan ikke være etter sluttdato.");
            return false;
        }

        // Oppdater feltene som er gyldige
        oppdaterFeltene(eksisterendeAvtale, nyAvtale);

        // Oppdater sluttdato hvis den er spesifisert
        if (nyAvtale.getSluttDato() != null) {
            eksisterendeAvtale.setSluttDato(nyAvtale.getSluttDato());
        }

        // Lagre oppdatert avtale
        avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
        return true;
    }

    private void oppdaterFeltene(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
        }

        if (nyAvtale.getDatoOgTid() != null) {
            eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
        }
    }

//    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        if (nyAvtale.getDatoOgTid() != null && nyAvtale.getSluttDato() != null &&
//                nyAvtale.getDatoOgTid().isAfter(nyAvtale.getSluttDato())) {
//            System.out.println("Startdato kan ikke være etter sluttdato.");
//            return false;
//        }
//
//        if (nyAvtale.getSluttDato() != null && (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty())) {
//            System.out.println("Hvis sluttdato er fylt ut, må gjentakelse velges.");
//            return false;
//        }
//
//        if (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty()) {
//            nyAvtale.setSluttDato(null);
//        }
//
//        oppdaterFeltene(eksisterendeAvtale, nyAvtale);
//
//        if (nyAvtale.getGjentakelse() != null && !nyAvtale.getGjentakelse().isEmpty()) {
//            if (eksisterendeAvtale.getGjentakelse() == null || !eksisterendeAvtale.getGjentakelse().equals(nyAvtale.getGjentakelse())) {
//                genererNyeAvtalerBasertPåGjentakelse(eksisterendeAvtale, nyAvtale);
//            }
//            eksisterendeAvtale.setGjentakelse(nyAvtale.getGjentakelse());
//        }
//
//        if (nyAvtale.getSluttDato() != null) {
//            eksisterendeAvtale.setSluttDato(nyAvtale.getSluttDato());
//        }
//
//        avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
//        return true;
//    }
//
//    private void oppdaterFeltene(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
//            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
//        }
//
//        if (nyAvtale.getDatoOgTid() != null) {
//            eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
//        }
//    }
//
//    private void genererNyeAvtalerBasertPåGjentakelse(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        String gjentakelse = nyAvtale.getGjentakelse();
//        System.out.println("Forsøker å generere avtaler med gjentakelse: " + gjentakelse);
//
//        LocalDateTime datoOgTid = eksisterendeAvtale.getDatoOgTid();
//        LocalDateTime sluttdato = nyAvtale.getSluttDato();
//
//        switch (gjentakelse) {
//            case "daglig":
//                genererDagligeAvtaler(datoOgTid, sluttdato, eksisterendeAvtale);
//                break;
//            case "ukentlig":
//                genererUkentligeAvtaler(datoOgTid, sluttdato, eksisterendeAvtale);
//                break;
//            case "månedlig":
//                genererMånedligeAvtaler(datoOgTid, sluttdato, eksisterendeAvtale);
//                break;
//            default:
//                System.out.println("Ukjent gjentakelse: " + gjentakelse);
//                break;
//        }
//    }
//
//    private void genererDagligeAvtaler(LocalDateTime datoOgTid, LocalDateTime sluttdato, Avtale eksisterendeAvtale) {
//        while (datoOgTid.isBefore(sluttdato)) {
//            Avtale nyAvtaleDaglig = new Avtale(datoOgTid, eksisterendeAvtale.getBeskrivelse(), "daglig", sluttdato, eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
//            avtaleRepository.opprettAvtale(nyAvtaleDaglig);
//            datoOgTid = datoOgTid.plusDays(1);
//        }
//    }
//
//    private void genererUkentligeAvtaler(LocalDateTime datoOgTid, LocalDateTime sluttdato, Avtale eksisterendeAvtale) {
//        while (datoOgTid.isBefore(sluttdato)) {
//            Avtale nyAvtaleUkentlig = new Avtale(datoOgTid, eksisterendeAvtale.getBeskrivelse(), "ukentlig", sluttdato, eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
//            avtaleRepository.opprettAvtale(nyAvtaleUkentlig);
//            datoOgTid = datoOgTid.plusWeeks(1);
//        }
//    }
//
//    private void genererMånedligeAvtaler(LocalDateTime datoOgTid, LocalDateTime sluttdato, Avtale eksisterendeAvtale) {
//        while (datoOgTid.isBefore(sluttdato)) {
//            Avtale nyAvtaleMånedlig = new Avtale(datoOgTid, eksisterendeAvtale.getBeskrivelse(), "månedlig", sluttdato, eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
//            avtaleRepository.opprettAvtale(nyAvtaleMånedlig);
//            datoOgTid = datoOgTid.plusMonths(1);
//        }
//    }
}
