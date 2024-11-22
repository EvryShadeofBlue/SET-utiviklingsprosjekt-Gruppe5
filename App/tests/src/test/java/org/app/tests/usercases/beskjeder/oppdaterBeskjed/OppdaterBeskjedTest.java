package org.app.tests.usercases.beskjeder.oppdaterBeskjed;

import org.app.core.logikk.beskjed.OppdaterBeskjedLogikk;
import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.BeskjedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class OppdaterBeskjedTest {
    @Mock
    BeskjedRepository mockBeskjedRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppdaterer kun beskrivelse")
    public void oppdaterKunBeskrivelse() {
        // Arrange
        Beskjed eksisterendeBeskjed = new Beskjed(1, LocalDateTime.now(), "Gammel Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, LocalDateTime.now(), "Ny Beskrivelse", 24, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act
        Beskjed oppdatertBeskjed = oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed);

        // Assert
        Assertions.assertEquals("Ny Beskrivelse", oppdatertBeskjed.getBeskrivelse(), "Beskrivelsen bør oppdateres.");
        Assertions.assertEquals(24, oppdatertBeskjed.getSynligTidsenhet(), "Synlig tidsenhet bør ikke endres.");
        Assertions.assertNotNull(oppdatertBeskjed.getDatoOgTid(), "Dato og tid bør forbli uendret.");
    }

    @Test
    @DisplayName("Oppdaterer kun synlig tidsenhet")
    public void oppdaterKunSynligTidsenhet() {
        // Arrange
        Beskjed eksisterendeBeskjed = new Beskjed(1, LocalDateTime.now(), "Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, LocalDateTime.now(), "Beskrivelse", 48, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act
        Beskjed oppdatertBeskjed = oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed);

        // Assert
        Assertions.assertEquals(48, oppdatertBeskjed.getSynligTidsenhet(), "Synlig tidsenhet bør oppdateres.");
        Assertions.assertEquals("Beskrivelse", oppdatertBeskjed.getBeskrivelse(), "Beskrivelsen bør ikke endres.");
        Assertions.assertNotNull(oppdatertBeskjed.getDatoOgTid(), "Dato og tid bør forbli uendret.");
    }

    @Test
    @DisplayName("Oppdaterer kun dato og tid")
    public void oppdaterKunDatoOgTid() {
        // Arrange
        LocalDateTime gammelDato = LocalDateTime.now().minusDays(1);
        LocalDateTime nyDato = LocalDateTime.now();
        Beskjed eksisterendeBeskjed = new Beskjed(1, gammelDato, "Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, nyDato, "Beskrivelse", 24, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act
        Beskjed oppdatertBeskjed = oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed);

        // Assert
        Assertions.assertEquals(nyDato, oppdatertBeskjed.getDatoOgTid(), "Dato og tid bør oppdateres.");
        Assertions.assertEquals("Beskrivelse", oppdatertBeskjed.getBeskrivelse(), "Beskrivelsen bør ikke endres.");
        Assertions.assertEquals(24, oppdatertBeskjed.getSynligTidsenhet(), "Synlig tidsenhet bør ikke endres.");
    }

    @Test
    @DisplayName("Oppdaterer alle felter")
    public void oppdaterAllefelter() {
        // Arrange
        LocalDateTime gammelDato = LocalDateTime.now().minusDays(1);
        LocalDateTime nyDato = LocalDateTime.now();
        Beskjed eksisterendeBeskjed = new Beskjed(1, gammelDato, "Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, nyDato, "Ny beskrivelse", 36, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act
        Beskjed oppdatertBeskjed = oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed);

        // Assert
        Assertions.assertEquals(nyDato, oppdatertBeskjed.getDatoOgTid(), "Dato og tid bør oppdateres.");
        Assertions.assertEquals("Ny beskrivelse", oppdatertBeskjed.getBeskrivelse(), "Beskrivelsen bør oppdateres.");
        Assertions.assertEquals(36, oppdatertBeskjed.getSynligTidsenhet(), "Synlig tidsenhet bør oppdateres.");
    }
}
