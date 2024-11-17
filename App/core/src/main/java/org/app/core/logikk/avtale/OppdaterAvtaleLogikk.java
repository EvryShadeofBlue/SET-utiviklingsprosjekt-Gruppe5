package org.app.core.logikk.avtale;

import org.app.core.models.Avtale;
import org.app.core.repositories.AvtaleRepository;

public class OppdaterAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public OppdaterAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        // Sjekk om eksisterende avtale har gjentakelse
        if (eksisterendeAvtale.getGjentakelse() != null && eksisterendeAvtale.getGjentakelse().equalsIgnoreCase("Ingen")) {
            // Hvis den eksisterende avtalen har "Ingen" som gjentakelse, hindrer vi at den får en ny gjentakelse
            if (nyAvtale.getGjentakelse() != null && !nyAvtale.getGjentakelse().equalsIgnoreCase("Ingen")) {
                System.out.println("Avtale som ikke har gjentakelse kan ikke få gjentakelse.");
                return false;
            }
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


}
