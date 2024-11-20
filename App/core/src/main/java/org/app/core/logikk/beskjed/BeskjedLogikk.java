package org.app.core.logikk.beskjed;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.BeskjedRepository;
import org.app.core.models.Beskjed;

import java.time.LocalDateTime;
import java.util.List;


public class BeskjedLogikk {
    private BeskjedRepository beskjedRepository;
    private OpprettBeskjedLogikk opprettBeskjedLogikk;
    private OppdaterBeskjedLogikk oppdaterBeskjedLogikk;
    private SlettBeskjedLogikk slettBeskjedLogikk;
    private VisBeskjedLogikk visBeskjedLogikk;

    public BeskjedLogikk(BeskjedRepository beskjedRepository) {
        this.beskjedRepository = beskjedRepository;
        this.opprettBeskjedLogikk = new OpprettBeskjedLogikk(beskjedRepository);
        this.oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(beskjedRepository);
        this.slettBeskjedLogikk = new SlettBeskjedLogikk(beskjedRepository);

    }

    public Beskjed opprettBeskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Parorende parorende, Pleietrengende pleietrengende) {
        return opprettBeskjedLogikk.opprettBeskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
    }

    public Beskjed oppdaterBeskjed(Beskjed nyBeskjed) {
        return oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed);
    }

    public boolean slettBeskjed(int beskjedId) {
        return slettBeskjedLogikk.slettBeskjed(beskjedId);
    }

    public List<Beskjed> hentBeskjedForParorende(Parorende parorende) {
        return visBeskjedLogikk.hentBeskjedForParorende(parorende);
    }



//    public Beskjed opprettBeskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Parorende parorende, Pleietrengende pleietrengende) {
//        Beskjed beskjed = new Beskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
//        beskjedRepository.oppretteBeskjed(beskjed);
//        return beskjed;
//    }

//    public Beskjed oppdaterBeskjed(Beskjed nyBeskjed) {
//        Beskjed eksisterendeBeskjed = beskjedRepository.hentBeskjed(nyBeskjed.getBeskjedId());
//
//        if (eksisterendeBeskjed != null) {
//            if (nyBeskjed.getDatoOgTid() != null) {
//                eksisterendeBeskjed.setDatoOgTid(nyBeskjed.getDatoOgTid());
//            }
//            if (nyBeskjed.getBeskrivelse() != null) {
//                eksisterendeBeskjed.setBeskrivelse(nyBeskjed.getBeskrivelse());
//            }
//            if (nyBeskjed.getSynligTidsenhet() > 0) {
//                eksisterendeBeskjed.setSynligTidsenhet(nyBeskjed.getSynligTidsenhet());
//            }
//            if (nyBeskjed.getParorende() != null) {
//                eksisterendeBeskjed.setParorende(nyBeskjed.getParorende());
//            }
//            if (nyBeskjed.getPleietrengende() != null) {
//                eksisterendeBeskjed.setPleietrengende(nyBeskjed.getPleietrengende());
//            }
//
//            beskjedRepository.oppdaterBeskjed(eksisterendeBeskjed);
//            return eksisterendeBeskjed;
//        }
//        return null;
//    }



//    public boolean slettBeskjed(int beskjedId) {
//        try {
//            beskjedRepository.slettBeskjed(beskjedId);
//            return true;
//        }
//        catch (Exception e) {
//            return false;
//        }
//    }
//    public List<Beskjed> hentBeskjedForParorende(Parorende parorende) {
//        List<Beskjed> beskjeder = beskjedRepository.hentBeskjedForParorende(parorende.getParorendeId());
//        beskjeder.sort((b1, b2) -> b2.getDatoOgTid().compareTo(b1.getDatoOgTid()));
//        return beskjeder;
//
//    }

}