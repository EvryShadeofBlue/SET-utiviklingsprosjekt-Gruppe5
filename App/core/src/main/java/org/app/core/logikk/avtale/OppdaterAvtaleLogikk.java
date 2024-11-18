package org.app.core.logikk.avtale;

import org.app.core.models.Avtale;
import org.app.core.repositories.AvtaleRepository;

public class OppdaterAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public OppdaterAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        // Valider at avtalen er ikke-gjentakende
        if (eksisterendeAvtale.getGjentakelse() != null &&
                !eksisterendeAvtale.getGjentakelse().equalsIgnoreCase("Ingen")) {
            throw new IllegalArgumentException("Kun ikke-gjentakende avtaler kan oppdateres.");
        }

        // Valider nye datoer
        if (nyAvtale.getDatoOgTid() == null) {
            throw new IllegalArgumentException("Dato og tid må spesifiseres for oppdateringen.");
        }

        // Oppdater kun datoOgTid og beskrivelse
        eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
        }

        // Lagre oppdatert avtale
        return avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
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