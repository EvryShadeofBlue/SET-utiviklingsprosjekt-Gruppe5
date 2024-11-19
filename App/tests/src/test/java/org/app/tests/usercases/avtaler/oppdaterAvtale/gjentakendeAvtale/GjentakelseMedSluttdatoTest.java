package org.app.tests.usercases.avtaler.oppdaterAvtale.gjentakendeAvtale;

import net.bytebuddy.asm.Advice;
import org.app.core.logikk.avtale.OppdaterAvtaleLogikk;
import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.logikk.avtale.AvtaleLogikk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class GjentakelseMedSluttdatoTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Kan ikke oppdatere avtale med ukentlig gjentakelse")
    public void kanIkkeOppdatereAvtaleMedUkentligGjentakelse() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttDato = dato.plusWeeks(2);
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.now(), "Legetime", mockParorende, mockPleietrengende);
        eksisterendeAvtale.setGjentakelse("ukentlig");
        eksisterendeAvtale.setSluttDato(sluttDato);

        Avtale nyAvtale = new Avtale(dato.plusDays(1), "Endret legetime", mockParorende, mockPleietrengende);
        nyAvtale.setSluttDato(sluttDato.plusDays(1));

        //Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        boolean result = oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        Assertions.assertFalse(result, "Avtale med gjentakelse kan ikke oppdateres");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).oppdaterAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Kan ikke oppdatere avtale med daglig gjentakelse")
    public void kanIkkeOppdatereAvtaleMedDagligGjentakelse() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttDato = dato.plusWeeks(2);
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.now(), "Fysioterapaut", mockParorende, mockPleietrengende);
        eksisterendeAvtale.setGjentakelse("daglig");
        eksisterendeAvtale.setSluttDato(sluttDato);

        Avtale nyAvtale = new Avtale(dato.plusDays(1), "Endret time", mockParorende, mockPleietrengende);
        nyAvtale.setSluttDato(sluttDato.plusDays(1));

        //Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        boolean result = oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        Assertions.assertFalse(result, "Avtale med gjentakelse kan ikke oppdateres");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).oppdaterAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Kan ikke oppdatere avtale med gjentakelse")
    public void kanIkkeOppdatereAvtaleMedMånedligGjentakelse() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttDato = dato.plusWeeks(2);
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.now(), "Hjemmebesøk", mockParorende, mockPleietrengende);
        eksisterendeAvtale.setGjentakelse("månedlig");
        eksisterendeAvtale.setSluttDato(sluttDato);

        Avtale nyAvtale = new Avtale(dato.plusDays(1), "Endret besøk", mockParorende, mockPleietrengende);
        nyAvtale.setSluttDato(sluttDato.plusDays(1));

        //Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        boolean result = oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        Assertions.assertFalse(result, "Avtale med gjentakelse kan ikke oppdateres");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).oppdaterAvtale(Mockito.any(Avtale.class));
    }
}
