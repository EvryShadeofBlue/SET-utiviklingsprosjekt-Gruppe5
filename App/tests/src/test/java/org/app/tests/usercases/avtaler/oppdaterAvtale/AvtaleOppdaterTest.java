package org.app.tests.usercases.avtaler.oppdaterAvtale;

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
public class AvtaleOppdaterTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppdaterer kun beskrivelse på avtale")
    public void oppdaterKunBeskrivelse() {
        //Arrange
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.of(2024, 11, 20, 10, 0), "" +
                "Legetime", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale(eksisterendeAvtale.getDatoOgTid(), "Fysioterapitime", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtalen skal være vellykket");
        Assertions.assertEquals("Fysioterapitime", eksisterendeAvtale.getBeskrivelse(), "Beskrivelse oppdatert");
        Assertions.assertEquals(LocalDateTime.of(2024, 11, 20, 10, 0), eksisterendeAvtale.getDatoOgTid());
    }

    @Test
    @DisplayName("Oppdaterer kun dato/tid på avtale")
    public void oppdaterKunDatoOgTid() {
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.of(2024, 11, 20, 15, 0), "Legetime", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale(LocalDateTime.of(2024, 11, 21, 15, 0), eksisterendeAvtale.getBeskrivelse(), mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtalen skal være vellykket");
        Assertions.assertEquals(LocalDateTime.of(2024, 11, 21, 15, 0), eksisterendeAvtale.getDatoOgTid(), "Dato og tid skal være oppdatert");
        Assertions.assertEquals("Legetime", eksisterendeAvtale.getBeskrivelse(), "Beskrivelsen forblir uendret");
    }

    @Test
    @DisplayName("Oppdaterer avtale beskrivelse og dato/tid")
    public void oppdaterAvtaleBeskrivelseOgDatoTid() {
        //Arrange
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.now(), "Legetime", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale(LocalDateTime.now().plusDays(1), "Ny beskrivelse", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen skal være oppdatert");
        Assertions.assertEquals(nyAvtale.getBeskrivelse(), eksisterendeAvtale.getBeskrivelse(), "Beskrivelsen skal være oppdatert");
        Assertions.assertEquals(nyAvtale.getDatoOgTid(), eksisterendeAvtale.getDatoOgTid(), "Dato og tid skal være oppdatert");
    }
}
