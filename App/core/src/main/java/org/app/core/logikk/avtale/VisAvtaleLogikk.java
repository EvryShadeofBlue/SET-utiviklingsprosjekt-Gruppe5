package org.app.core.logikk.avtale;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.repositories.AvtaleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VisAvtaleLogikk {
    private AvtaleRepository avtaleRepository;
    public VisAvtaleLogikk(AvtaleRepository avtaleRepository) {
        this.avtaleRepository = avtaleRepository;
    }

    public List<Avtale> hentAvtalerForParorende(Parorende parorende) {
        List<Avtale> avtaler = avtaleRepository.hentAvtalerForParorende(parorende);

        List<Avtale> sorterteAvtaler = avtaler.stream()
                .sorted((avtale1, avtale2) -> avtale2.getDatoOgTid().compareTo(avtale1.getDatoOgTid()))
                .collect(Collectors.toList());
        return sorterteAvtaler;
    }
}
