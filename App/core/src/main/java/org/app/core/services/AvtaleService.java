package org.app.core.services;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.models.Avtale;

import java.time.LocalDateTime;
import java.util.List;

public class AvtaleService {
    private AvtaleRepository avtaleRepository;

    public AvtaleService(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }


    public Avtale oppretteAvtale(LocalDateTime datoOgTid, String beskrivelse, String gjentakelse, LocalDateTime sluttDato, Parorende parorende, Pleietrengende pleietrengende) {
        Avtale avtale = new Avtale(datoOgTid, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);
        avtaleRepository.oppretteAvtale(avtale);

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
                Avtale nyAvtale = new Avtale(nesteDato, beskrivelse, gjentakelse, sluttDato, parorende, pleietrengende);
                avtaleRepository.oppretteAvtale(nyAvtale);
            }
        }
        return avtale;
    }

    public Avtale oppdaterAvtale(Avtale nyAvtale) {
            Avtale eksisterendeAvtale = avtaleRepository.hentAvtale(nyAvtale.getAvtaleId());

            if (eksisterendeAvtale != null) {
                if (nyAvtale.getDatoOgTid() != null) {
                    eksisterendeAvtale.setDatoOgTid(nyAvtale.getDatoOgTid());
                }
                if (nyAvtale.getBeskrivelse() != null) {
                    eksisterendeAvtale.setBeskrivelse(nyAvtale.getBeskrivelse());
                }
                if (nyAvtale.getGjentakelse() != null) {
                    eksisterendeAvtale.setGjentakelse(nyAvtale.getGjentakelse());
                }
                if (nyAvtale.getSluttDato() != null) {
                    eksisterendeAvtale.setSluttDato(nyAvtale.getSluttDato());
                }

                avtaleRepository.oppdaterAvtale(eksisterendeAvtale);
                return eksisterendeAvtale;
            }
            return null;
    }

    public boolean slettAvtale(int avtaleId) {
        try {
            avtaleRepository.slettAvtale(avtaleId);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Avtale> hentAvtaleForParorened(Parorende parorende) {
        List<Avtale> avtaler = avtaleRepository.hentAvtaleForParorende(parorende.getParorendeId());
        avtaler.sort((a1, a2) -> a2.getDatoOgTid().compareTo(a1.getDatoOgTid()));
        return avtaler;
    }


}
