package org.app.tests.usercases.avtaler;

import net.bytebuddy.implementation.bytecode.assign.reference.GenericTypeAwareAssigner;
import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.services.AvtaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class RedigerAvtaleTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppdaterer avtale beskrivelse, men har samme dato")
    public void oppdaterKunBeskrivelse() {
        //Arrange
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.of(2024, 11, 20, 10, 0), "" +
                "Legetime", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale(eksisterendeAvtale.getDatoOgTid(), "Fysioterapitime", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Oppdatering av avtalen skal være vellykket");
        Assertions.assertEquals("Fysioterapitime", eksisterendeAvtale.getBeskrivelse(), "Beskrivelse oppdatert");
        Assertions.assertEquals(LocalDateTime.of(2024, 11, 20, 10, 0), eksisterendeAvtale.getDatoOgTid());
    }

    @Test
    @DisplayName("Oppdaterer avtale beskrivelse og dato/tid")
    public void oppdaterAvtaleBeskrivelseOgDatoTid() {
        //Arrange
        Avtale eksisterendeAvtale = new Avtale(LocalDateTime.now(), "Legetime", mockParorende, mockPleietrengende);
        Avtale nyAvtale = new Avtale(LocalDateTime.now().plusDays(1), "Ny beskrivelse", mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.oppdaterAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.oppdaterAvtale(eksisterendeAvtale, nyAvtale);

        //Assert
        Assertions.assertTrue(result, "Avtalen skal være oppdatert");
        Assertions.assertEquals(nyAvtale.getBeskrivelse(), eksisterendeAvtale.getBeskrivelse(), "Beskrivelsen skal være oppdatert");
        Assertions.assertEquals(nyAvtale.getDatoOgTid(), eksisterendeAvtale.getDatoOgTid(), "Dato og tid skal være oppdatert");
    }
}
