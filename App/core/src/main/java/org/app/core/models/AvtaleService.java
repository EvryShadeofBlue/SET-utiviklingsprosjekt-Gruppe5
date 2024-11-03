package org.app.core.models;

import org.app.core.repository.AvtaleRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AvtaleService {
    private AvtaleRepository avtaleRepository;

    public AvtaleService(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }


    public Avtale opprettAvtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse, String gjentakelse, LocalDateTime sluttDato) {
        Avtale avtale = new Avtale(avtaleId, datoOgTid, beskrivelse);
        avtaleRepository.lagreAvtale(avtale);

        if (gjentakelse != null && !gjentakelse.isEmpty() && sluttDato != null) {
            LocalDateTime nesteDato = datoOgTid;

            while (datoOgTid.isBefore(sluttDato)) {
                switch (gjentakelse.toLowerCase()) {
                    case "daglig":
                        nesteDato = nesteDato.plusDays(1);
                        break;
                    case "ukentlig":
                        nesteDato = nesteDato.plusWeeks(1);
                        break;
                    case "månedlig":
                        nesteDato = nesteDato.plusMonths(1);
                        break;
                    default:
                        throw new IllegalArgumentException("Ugyldig gjentakelsestype: " + gjentakelse);
                }
                Avtale nyAvtale = new Avtale(avtaleId, datoOgTid, beskrivelse, gjentakelse, sluttDato);
                avtaleRepository.lagreAvtale(nyAvtale);
            }
        }
        return avtale;
    }

    public Avtale oppdaterAvtale(int avtaleId, Avtale nyAvtale) {
        Avtale eksisterendeAvtale = avtaleRepository.hentAvtale(avtaleId);

        if (eksisterendeAvtale != null) {
            if (nyAvtale.getDatoOgTid() != null) {
                eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
            }
            if (nyAvtale.getBeksrivelse() != null) {
                eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeksrivelse());
            }

            avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
            return eksisterendeAvtale;

        }
        return null;
    }

    public boolean slettAvtale(int avtaleId) {
        try {
            avtaleRepository.hentAvtale(avtaleId);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<Avtale> hentAvtaleForParorened(Parorende parorende) {
        return avtaleRepository.hentAvtaleForParorende(parorende.getParorendeId());
    }


}
