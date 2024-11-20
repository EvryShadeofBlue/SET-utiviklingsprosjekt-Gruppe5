package org.app.tests.usercases.beskjeder.opprettBeskjed.feil;

import org.app.core.logikk.beskjed.OpprettBeskjedLogikk;
import org.app.core.models.Beskjed;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.BeskjedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class OpprettBeskjedFeilTest {
    @Mock
    BeskjedRepository mockBeskjedRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Opprettelse av beskjed feiler når beskrivelse mangler")
    public void opprettBeskjedUtenBeskrivelse() {
        // Arrange
        Beskjed beskjed = new Beskjed(LocalDateTime.now(), null, 12, mockParorende, mockPleietrengende);

        OpprettBeskjedLogikk opprettBeskjedLogikk = new OpprettBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> opprettBeskjedLogikk.opprettBeskjed(beskjed.getDatoOgTid(),
                beskjed.getBeskrivelse(), beskjed.getSynligTidsenhet(), beskjed.getParorende(), beskjed.getPleietrengende()),
                "Beskrivelse kan ikke være tom.");
    }

    @Test
    @DisplayName("Opprettelse av beskjed feiler når synlig tidsenhet mangler")
    public void opprettBeskjedUtenSynligTidsenhet() {
        // Arrange
        Beskjed beskjed = new Beskjed(LocalDateTime.now(), "Gyldig beskrivelse", 0, mockParorende, mockPleietrengende);

        OpprettBeskjedLogikk opprettBeskjedLogikk = new OpprettBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> opprettBeskjedLogikk.opprettBeskjed(beskjed.getDatoOgTid(), beskjed.getBeskrivelse(),
                beskjed.getSynligTidsenhet(), beskjed.getParorende(), beskjed.getPleietrengende()),
                "Opprettelse bør feile når synlig tidsenhet er ugyldig.");
    }

    @Test
    @DisplayName("Opprettelse feiler når dato mangler")
    public void opprettBeskjedUtenDato() {
        // Arrange
        Beskjed beskjed = new Beskjed(null, "Gyldig beskrivelse", 12, mockParorende, mockPleietrengende);

        OpprettBeskjedLogikk opprettBeskjedLogikk = new OpprettBeskjedLogikk(mockBeskjedRepo);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> opprettBeskjedLogikk.opprettBeskjed(beskjed.getDatoOgTid(), beskjed.getBeskrivelse(),
                beskjed.getSynligTidsenhet(), beskjed.getParorende(), beskjed.getPleietrengende()),
                "Opprettelse bør feile når dato mangler.");
    }
}
