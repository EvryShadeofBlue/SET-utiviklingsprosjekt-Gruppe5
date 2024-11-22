package org.app.core.logikk;

import org.app.core.logikk.avtale.AvtaleLogikk;
import org.app.core.logikk.beskjed.BeskjedLogikk;
import org.app.core.models.Beskjed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExpiredEntriesCleaner {

    private final BeskjedLogikk beskjedLogikk;
    private final AvtaleLogikk avtaleLogikk;

    public ExpiredEntriesCleaner(BeskjedLogikk beskjedLogikk, AvtaleLogikk avtaleLogikk) {
        this.beskjedLogikk = beskjedLogikk;
        this.avtaleLogikk = avtaleLogikk;
    }

    public void startCleaning(long delay, long period) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                deleteExpiredBeskjeder();
                //deleteExpiredAvtaler();
            }
        }, delay, period);
    }

    private void deleteExpiredBeskjeder() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Beskjed> beskjeder = beskjedLogikk.hentAlleBeskjeder();

        for (Beskjed beskjed : beskjeder) {
            LocalDateTime visibleUntil = beskjed.getDatoOgTid().plusDays(beskjed.getSynligTidsenhet());
            if (visibleUntil.isBefore(currentDate)) {
                beskjedLogikk.slettBeskjed(beskjed.getBeskjedId());
            }
        }
    }

//    private void deleteExpiredAvtaler() {
//        LocalDateTime currentDate = LocalDateTime.now();
//        List<Avtale> avtaler = avtaleLogikk.hentAlleAvtaler();
//
//        for (Avtale avtale : avtaler) {
//            if (avtale.getDatoOgTid() == null) {
//                continue;
//            }
//
//            String gjentakelse = avtale.getGjentakelse();
//            if (gjentakelse == null || gjentakelse.equals("Ingen")) {
//                if (avtale.getDatoOgTid().plusDays(14).isBefore(currentDate)) {
//                    avtaleLogikk.slettAvtale(avtale.getAvtaleId());
//                }
//            } else {
//                if (avtale.getSluttDato() != null && avtale.getSluttDato().isBefore(currentDate)) {
//                    avtaleLogikk.slettAvtale(avtale.getAvtaleId());
//                }
//            }
//        }
//    }

}
