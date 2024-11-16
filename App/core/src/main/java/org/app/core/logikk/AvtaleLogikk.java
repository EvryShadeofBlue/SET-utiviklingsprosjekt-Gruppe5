package org.app.core.logikk;

import org.app.core.models.Parorende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.models.Avtale;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

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

    public boolean OpprettAvtale(Avtale avtale) {
        return opprettAvtaleLogikk.opprettAvtale(avtale);
    }

    public boolean OppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        return oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
    }

    public boolean SlettAvtale(int avtaleId) {
        return slettAvtaleLogikk.slettAvtale(avtaleId);
    }

    public List<Avtale> VisAvtaleForParorende(Parorende parorende) {
        return visAvtaleLogikk.hentAvtalerForParorende(parorende);
    }




//    public boolean opprettAvtale(Avtale avtale) {
//        if (avtale == null) {
//            throw new IllegalArgumentException("Avtale kan ikke være null");
//        }
//        if ("daglig".equalsIgnoreCase(avtale.getGjentakelse())) {
//            return opprettDagligeAvtaler(avtale);
//        } else if ("ukentlig".equalsIgnoreCase(avtale.getGjentakelse())) {
//            return opprettUkentligeAvtaler(avtale);
//        } else if ("månedlig".equalsIgnoreCase(avtale.getGjentakelse())) {
//            return opprettMånedligeAvtaler(avtale);
//        }
//        return avtaleRepository.opprettAvtale(avtale);
//    }
//
//    private boolean opprettDagligeAvtaler(Avtale avtale) {
//        LocalDateTime datoOgTid = avtale.getDatoOgTid();
//        LocalDateTime sluttdato = avtale.getSluttDato();
//        boolean resultat = true;
//
//        while (datoOgTid.isBefore(sluttdato) || datoOgTid.isEqual(sluttdato)) {
//            Avtale nyAvtale = new Avtale(datoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
//
//            resultat &= avtaleRepository.opprettAvtale(nyAvtale);
//
//            datoOgTid = datoOgTid.plusDays(1);
//        }
//        return resultat;
//    }
//
//    private boolean opprettUkentligeAvtaler(Avtale avtale) {
//        LocalDateTime datoOgTid = avtale.getDatoOgTid();
//        LocalDateTime sluttdato = avtale.getSluttDato();
//        boolean resultat = true;
//        LocalDateTime justertSluttdato = sluttdato.isAfter(datoOgTid) ? sluttdato : sluttdato.plusDays(1);
//
//        while (!datoOgTid.isAfter(justertSluttdato)) {
//            Avtale nyAvtale = new Avtale(datoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
//
//            resultat &= avtaleRepository.opprettAvtale(nyAvtale);
//
//            datoOgTid = datoOgTid.plusWeeks(1);
//        }
//        return resultat;
//    }
//
//    private boolean opprettMånedligeAvtaler(Avtale avtale) {
//        LocalDateTime datoOgTid = avtale.getDatoOgTid();
//        LocalDateTime sluttdato = avtale.getSluttDato();
//        boolean resultat = true;
//
//        LocalDateTime justertSluttdato = sluttdato.toLocalDate().atStartOfDay().plusDays(1);
//
//        while (datoOgTid.isBefore(justertSluttdato)) {
//            Avtale nyAvtale = new Avtale(datoOgTid, avtale.getBeskrivelse(), avtale.getGjentakelse(), avtale.getSluttDato(), avtale.getParorende(), avtale.getPleietrengende());
//
//            resultat &= avtaleRepository.opprettAvtale(nyAvtale);
//
//            datoOgTid = datoOgTid.plusMonths(1);
//        }
//        return resultat;
//    }
//
//    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        if (nyAvtale.getSluttDato() != null && (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty())) {
//            System.out.println("Hvis sluttdato er fylt ut, må gjentakelse velges.");
//            return false;
//        }
//        if (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty()) {
//            nyAvtale.setSluttDato(null);
//        }
//        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
//            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
//        }
//        if (nyAvtale.getDatoOgTid() != null) {
//            eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
//        }
//        if (nyAvtale.getGjentakelse() != null && !nyAvtale.getGjentakelse().isEmpty()) {
//            eksisterendeAvtale.setGjentakelse(nyAvtale.getGjentakelse());
//        }
//        if (nyAvtale.getSluttDato() != null) {
//            eksisterendeAvtale.setSluttDato(nyAvtale.getSluttDato());
//        }
//
//        avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
//        return true;
//    }
//
//    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        // Validerer at startdato ikke er etter sluttdato
//        if (nyAvtale.getDatoOgTid() != null && nyAvtale.getSluttDato() != null &&
//                nyAvtale.getDatoOgTid().isAfter(nyAvtale.getSluttDato())) {
//            System.out.println("Startdato kan ikke være etter sluttdato.");
//            return false;
//        }
//
//        // Validerer at gjentakelse og sluttdato er kompatible
//        if (nyAvtale.getSluttDato() != null && (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty())) {
//            System.out.println("Hvis sluttdato er fylt ut, må gjentakelse velges.");
//            return false;
//        }
//
//        // Hvis gjentakelse er null eller tom, fjern sluttdato
//        if (nyAvtale.getGjentakelse() == null || nyAvtale.getGjentakelse().isEmpty()) {
//            nyAvtale.setSluttDato(null);
//        }
//
//        // Oppdaterer beskrivelse
//        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
//            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
//        }
//
//        // Oppdaterer dato og tid
//        if (nyAvtale.getDatoOgTid() != null) {
//            eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
//        }
//
//        // Sjekk om gjentakelsen har endret seg, og generer nye avtaler om nødvendig
//        if (nyAvtale.getGjentakelse() != null && !nyAvtale.getGjentakelse().isEmpty()) {
//            // Hvis gjentakelse har endret seg, må vi håndtere genereringen av nye avtaler
//            if (eksisterendeAvtale.getGjentakelse() == null || !eksisterendeAvtale.getGjentakelse().equals(nyAvtale.getGjentakelse())) {
//                // Generer nye avtaler for den nye gjentakelsen
//                System.out.println("Gjentakelse har endret seg fra " + eksisterendeAvtale.getGjentakelse() + " til " + nyAvtale.getGjentakelse());
//                genererNyeAvtalerBasertPåGjentakelse(eksisterendeAvtale, nyAvtale);
//            }
//            // Oppdater eksisterende avtale med den nye gjentakelsen
//            eksisterendeAvtale.setGjentakelse(nyAvtale.getGjentakelse());
//        }
//
//        // Oppdaterer sluttdato
//        if (nyAvtale.getSluttDato() != null) {
//            eksisterendeAvtale.setSluttDato(nyAvtale.getSluttDato());
//        }
//
//        // Oppdaterer avtalen i databasen
//        avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
//
//        return true;
//    }
//
//    private void genererNyeAvtalerBasertPåGjentakelse(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        String gjentakelse = nyAvtale.getGjentakelse();
//        System.out.println("Forsøker å generere avtaler med gjentakelse: " + gjentakelse);
//
//        // Bruk eksisterende metoder for å generere avtaler
//        LocalDateTime datoOgTid = eksisterendeAvtale.getDatoOgTid();
//        LocalDateTime sluttdato = nyAvtale.getSluttDato();
//
//        // Kall på eksisterende metoder for å generere avtaler
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
//
//    private void genererDagligeAvtaler(LocalDateTime datoOgTid, LocalDateTime sluttdato, Avtale eksisterendeAvtale) {
//        // Bruk din eksisterende logikk for daglige avtaler her
//        while (datoOgTid.isBefore(sluttdato)) {
//            Avtale nyAvtaleDaglig = new Avtale(datoOgTid, eksisterendeAvtale.getBeskrivelse(), "daglig", sluttdato, eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
//            avtaleRepository.opprettAvtale(nyAvtaleDaglig);
//            datoOgTid = datoOgTid.plusDays(1);
//        }
//    }
//
//    private void genererUkentligeAvtaler(LocalDateTime datoOgTid, LocalDateTime sluttdato, Avtale eksisterendeAvtale) {
//        // Bruk din eksisterende logikk for ukentlige avtaler her
//        while (datoOgTid.isBefore(sluttdato)) {
//            Avtale nyAvtaleUkentlig = new Avtale(datoOgTid, eksisterendeAvtale.getBeskrivelse(), "ukentlig", sluttdato, eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
//            avtaleRepository.opprettAvtale(nyAvtaleUkentlig);
//            datoOgTid = datoOgTid.plusWeeks(1);
//        }
//    }
//
//    private void genererMånedligeAvtaler(LocalDateTime datoOgTid, LocalDateTime sluttdato, Avtale eksisterendeAvtale) {
//        // Bruk din eksisterende logikk for månedlige avtaler her
//        while (datoOgTid.isBefore(sluttdato)) {
//            Avtale nyAvtaleMånedlig = new Avtale(datoOgTid, eksisterendeAvtale.getBeskrivelse(), "månedlig", sluttdato, eksisterendeAvtale.getParorende(), eksisterendeAvtale.getPleietrengende());
//            avtaleRepository.opprettAvtale(nyAvtaleMånedlig);
//            datoOgTid = datoOgTid.plusMonths(1);
//        }
//    }
//
//
//    public boolean slettAvtale(int avtaleId) {
//        Avtale avtale = avtaleRepository.hentAvtale(avtaleId);
//
//        if (avtale == null) {
//            System.out.println("Avtalen ble ikke funnet.");
//            return false;
//        }
//
//        avtaleRepository.slettAvtale(avtaleId);
//
//        return true;
//    }
//
//    public List<Avtale> hentAvtalerForParorende(Parorende parorende) {
//        List<Avtale> avtaler = avtaleRepository.hentAvtalerForParorende(parorende);
//
//        List<Avtale> sorterteAvtaler = avtaler.stream()
//                .sorted((avtale1, avtale2) -> avtale2.getDatoOgTid().compareTo(avtale1.getDatoOgTid()))
//                .collect(Collectors.toList());
//        return sorterteAvtaler;
//    }
}



