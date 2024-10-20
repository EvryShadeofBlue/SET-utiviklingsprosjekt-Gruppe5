package model;

import repository.AvtaleRepository;

import java.time.LocalDateTime;

public class AvtaleService {
    private AvtaleRepository avtaleRepository;

    public AvtaleService(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }


    // Metode for å opprette avtale

    /* Ved bruk av dette og implementering, fjern avtale id
    ettersom den kun er brukt for testing
     */
    public void opprettAvtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse, String gjentakelse, LocalDateTime sluttDato) {
        Avtale avtale = new Avtale(avtaleId, datoOgTid, beskrivelse);
        avtaleRepository.lagreAvtale(avtale);

        //Ny funksjonalitet
        // Man kan sette en avtale til å gjenta seg etter behov dersom det er ønskelig
        // Ikke nødvendig hvis man ikke ønsker å ha det med
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
    }

    // Metode for å oppdater avtale
    public Avtale oppdaterAvtale(int avtaleId, Avtale nyAvtale) {
        // Henter beskjed
        Avtale eksisterendeAvtale = avtaleRepository.hentAvtale(avtaleId);

        // sjekker om beskjeden eksisterer
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

    // Metode for å slette avtale
    public boolean slettAvtale(int avtaleId) {
        Avtale avtale = avtaleRepository.hentAvtale(avtaleId);
        if (avtale != null) {
            avtaleRepository.slettAvtale(avtaleId);
            return true;
        }
        return false;
    }


}
