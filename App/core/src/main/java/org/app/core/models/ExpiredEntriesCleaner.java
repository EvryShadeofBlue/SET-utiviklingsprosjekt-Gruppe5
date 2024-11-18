package org.app.core.models;

import org.app.core.services.AvtaleService;
import org.app.core.services.BeskjedService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExpiredEntriesCleaner {

    private final BeskjedService beskjedService;
    private final AvtaleService avtaleService;

    public ExpiredEntriesCleaner(BeskjedService beskjedService, AvtaleService avtaleService) {
        this.beskjedService = beskjedService;
        this.avtaleService = avtaleService;
    }

    public void startCleaning(long delay, long period) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                deleteExpiredBeskjeder();
                deleteExpiredAvtaler();
            }
        }, delay, period);
    }

    private void deleteExpiredBeskjeder() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Beskjed> beskjeder = beskjedService.hentAlleBeskjeder();

        for (Beskjed beskjed : beskjeder) {
            LocalDateTime visibleUntil = beskjed.getDatoOgTid().plusDays(beskjed.getSynligTidsenhet());
            if (visibleUntil.isBefore(currentDate)) {
                beskjedService.slettBeskjed(beskjed.getBeskjedId());
            }
        }
    }

    private void deleteExpiredAvtaler() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Avtale> avtaler = avtaleService.hentAlleAvtaler();

        for (Avtale avtale : avtaler) {
            if (avtale.getDatoOgTid() == null) {
                continue;
            }

            String gjentakelse = avtale.getGjentakelse();
            if (gjentakelse == null || gjentakelse.equals("Ingen")) {
                if (avtale.getDatoOgTid().plusDays(14).isBefore(currentDate)) {
                    avtaleService.slettAvtale(avtale.getAvtaleId());
                }
            } else {
                if (avtale.getSluttDato() != null && avtale.getSluttDato().isBefore(currentDate)) {
                    avtaleService.slettAvtale(avtale.getAvtaleId());
                }
            }
        }
    }

}
