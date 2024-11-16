package org.app.tests.usercases.avtaler.oppdaterAvtale.gjentakendeAvtale;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.logikk.AvtaleLogikk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class GjentakelseUtenSluttdato {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppdater avtale uten gjentakelse til daglig gjentakelse uten sluttdato")
    public void oppdaterAvtaleGjentakelseTilDaglig() {
        //Arrange
        LocalDateTime datoOgTid = LocalDateTime.of(2024, 11, 14, 12, 0);
        Avtale eksisterendeAvtale = new Avtale(datoOgTid, "Besøk hos pleietrengende", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale();
        nyAvtale.setGjentakelse("daglig");

        Mockito.when(mockAvtaleRepo.oppdaterAvtale(eksisterendeAvtale)).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtale til daglig gjentakelse skal være vellykket");
        Assertions.assertEquals("daglig", eksisterendeAvtale.getGjentakelse(), "Gjentakelsen skal være oppdatert");
        Assertions.assertNull(eksisterendeAvtale.getSluttDato(), "Sluttdato skal ikke eksistere");
    }

    @Test
    @DisplayName("Oppdater avtale uten gjentakelse til ukentlig gjentakelse uten sluttdato")
    public void oppdaterAvtaleGjentakelseTilUkentlig() {
        //Arrange
        LocalDateTime datoOgTid = LocalDateTime.of(2024, 11, 14, 12, 0);
        Avtale eksisterendeAvtale = new Avtale(datoOgTid, "Ukentlig fysioterapaut", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale();
        nyAvtale.setGjentakelse("ukentlig");

        Mockito.when(mockAvtaleRepo.oppdaterAvtale(eksisterendeAvtale)).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtale til ukentlig gjentakelse skal være vellykket");
        Assertions.assertEquals("ukentlig", eksisterendeAvtale.getGjentakelse(), "Gjentakelsen skal være oppdatert");
        Assertions.assertNull(eksisterendeAvtale.getSluttDato(), "Sluttdato skal ikke eksistere");
    }

    @Test
    @DisplayName("Oppdater avtale uten gjentakelse til månedlig gjentakelse uten sluttdao")
    public void oppdaterAvtaleGjentakelseTilMånedlig() {
        //Arrange
        LocalDateTime datoOgTid = LocalDateTime.of(2024, 11, 20, 13, 0);
        Avtale eksisterendeAvtale = new Avtale(datoOgTid, "Legetime for pleietrengende", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale();
        nyAvtale.setGjentakelse("månedlig");

        Mockito.when(mockAvtaleRepo.oppdaterAvtale(eksisterendeAvtale)).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtale til månedlig gjentakelse skal være vellykket");
        Assertions.assertEquals("månedlig", eksisterendeAvtale.getGjentakelse(), "Gjentakelsen skal være oppdatert");
        Assertions.assertNull(eksisterendeAvtale.getSluttDato(), "Sluttdato skal ikke eksistere");
    }
}
