package org.app.core.logikk.avtale;

import org.app.core.models.Avtale;
import org.app.core.repositories.AvtaleRepository;

public class OppdaterAvtaleLogikk {
    private AvtaleRepository avtaleRepository;

    public OppdaterAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public boolean oppdaterAvtale(Avtale eksisterendeAvtale, Avtale nyAvtale) {
        if (eksisterendeAvtale.getGjentakelse() != null &&
                !eksisterendeAvtale.getGjentakelse().equalsIgnoreCase("Ingen")) {
            throw new IllegalArgumentException("Kun ikke-gjentakende avtaler kan oppdateres.");
        }

        if (nyAvtale.getDatoOgTid() == null) {
            throw new IllegalArgumentException("Dato og tid må spesifiseres for oppdateringen.");
        }

        eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
        }

        return avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
    }



//    private void oppdaterFeltene(Avtale eksisterendeAvtale, Avtale nyAvtale) {
//        if (nyAvtale.getBeskrivelse() != null && !nyAvtale.getBeskrivelse().isEmpty()) {
//            eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
//        }
//
//        if (nyAvtale.getDatoOgTid() != null) {
//            eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
//        }
//    }


}