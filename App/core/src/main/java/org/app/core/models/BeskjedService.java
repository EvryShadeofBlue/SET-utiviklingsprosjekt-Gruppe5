package org.app.core.models;

import org.app.core.models.Beskjed;
import org.app.core.repository.BeskjedRepository;

import java.time.LocalDateTime;
import java.util.List;


public class BeskjedService {
    private BeskjedRepository beskjedRepository;

    public BeskjedService(BeskjedRepository beskjedRepository) {

        this.beskjedRepository = beskjedRepository;
    }


    public Beskjed opprettBeskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Parorende parorende, Pleietrengende pleietrengende) {
        Beskjed beskjed = new Beskjed(datoOgTid, beskrivelse, synligTidsenhet, parorende, pleietrengende);
        beskjedRepository.oppretteBeskjed(beskjed);
        return beskjed;
    }

    public Beskjed oppdaterBeskjed(int beskjedId, Beskjed nyBeskjed) {
        Beskjed eksisterendeBeskjed = beskjedRepository.hentBeskjed(beskjedId);

        if (eksisterendeBeskjed != null) {
            if (nyBeskjed.getDatoOgTid() != null) {
                eksisterendeBeskjed.setDatoOgTid(nyBeskjed.getDatoOgTid());
            }
            if (nyBeskjed.getBeskrivelse() != null) {
                eksisterendeBeskjed.setBeskrivelse(nyBeskjed.getBeskrivelse());
            }
            if (nyBeskjed.getSynligTidsenhet() > 0) {
                eksisterendeBeskjed.setSynligTidsenhet(nyBeskjed.getSynligTidsenhet());
            }
            beskjedRepository.oppdaterBeskjed(eksisterendeBeskjed);
            return eksisterendeBeskjed;
        }
        return null;
    }

    public boolean slettBeskjed(int beskjedId) {
        Beskjed beskjed = beskjedRepository.hentBeskjed(beskjedId);
        if (beskjed != null) {
            beskjedRepository.slettBeskjed(beskjedId);
            return true;
        }
        return false;
    }
    public List<Beskjed> hentBeskjedForParorende(Parorende parorende) {
        return beskjedRepository.hentBeskjedForParorende(parorende.getParorendeId());
    }

}