package org.app.tests.usercases.beskjeder.oppdaterBeskjed.feil;

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
public class OppdaterBeskjedFeilTest {
    @Mock
    BeskjedRepository mockBeskjedRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Feil: Beskrivelse forblir tom")
    public void beskrivelseForblirTom() {
        // Arrange
        Beskjed eksisterendeBeskjed = new Beskjed(1, LocalDateTime.now(), "Gammel Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, LocalDateTime.now(), null, 24, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed),
                "Bør kaste unntak når beskrivelsen er tom."
        );

        Assertions.assertEquals("Beskrivelsen kan ikke være tom ved oppdatering.", exception.getMessage());
    }

    @Test
    @DisplayName("Feil: Synlig tidsenhet forblir tom")
    public void synligTidsenhetForblirTomEllerUgyldig() {
        // Arrange
        Beskjed eksisterendeBeskjed = new Beskjed(1, LocalDateTime.now(), "Gammel Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, LocalDateTime.now(), "Gammel Beskrivelse", 0, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed),
                "Bør kaste unntak når synlig tidsenhet er ugyldig."
        );

        Assertions.assertEquals("Synlig tidsenhet må være større enn 0.", exception.getMessage());
    }

    @Test
    @DisplayName("Feil: Dato og tid forblir tom")
    public void datoOgTidForblirTom() {
        // Arrange
        Beskjed eksisterendeBeskjed = new Beskjed(1, LocalDateTime.now(), "Gammel Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, null, "Gammel Beskrivelse", 24, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed),
                "Bør kaste unntak når dato og tid er tom."
        );

        Assertions.assertEquals("Dato og tid må være satt ved oppdatering.", exception.getMessage());
    }

    @Test
    @DisplayName("Feil: Alle feltene er tomme")
    public void alleFelteneErTomme() {
        // Arrange
        Beskjed eksisterendeBeskjed = new Beskjed(1, LocalDateTime.now(), "Gammel Beskrivelse", 24, mockParorende, mockPleietrengende);
        Beskjed nyBeskjed = new Beskjed(1, null, null, 0, mockParorende, mockPleietrengende);

        Mockito.when(mockBeskjedRepo.hentBeskjed(1)).thenReturn(eksisterendeBeskjed);

        OppdaterBeskjedLogikk oppdaterBeskjedLogikk = new OppdaterBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> oppdaterBeskjedLogikk.oppdaterBeskjed(nyBeskjed),
                "Bør kaste unntak når alle feltene er tomme."
        );

        Assertions.assertEquals("Dato og tid må være satt ved oppdatering.", exception.getMessage());
    }
}
