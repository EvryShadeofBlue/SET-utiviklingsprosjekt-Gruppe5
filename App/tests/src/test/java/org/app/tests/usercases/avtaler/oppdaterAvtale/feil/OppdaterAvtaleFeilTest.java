package org.app.tests.usercases.avtaler.oppdaterAvtale.feil;

import org.app.core.logikk.avtale.OppdaterAvtaleLogikk;
import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class OppdaterAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    String beskrivelse = "Tannlegetime";

    @Test
    @DisplayName("Kan ikke oppdatere gjentakelse fra ingen til daglig, ukentlig eller månedlig")
    public void kanIkkeOppdatereGjentakelseFraIngenTilGyldig() {
        // Arrange
        LocalDateTime startDatoTid = LocalDateTime.of(2024, 11, 20, 14, 0);
        Avtale eksisterendeAvtale = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        eksisterendeAvtale.setGjentakelse("Ingen");

        // Act
        Avtale nyAvtaleMedDagligGjentakelse = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        nyAvtaleMedDagligGjentakelse.setGjentakelse("Daglig");

        // Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleMedDagligGjentakelse);
        }, "Det skal ikke være mulig å oppdatere gjentakelsen fra ingen til daglig.");

        // Act
        Avtale nyAvtaleMedUkentligGjentakelse = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        nyAvtaleMedUkentligGjentakelse.setGjentakelse("Ukentlig");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleMedUkentligGjentakelse);
        }, "Det skal ikke være mulig å oppdatere gjentakelsen fra ingen til ukentlig.");

        // Act
        Avtale nyAvtaleMedManedligGjentakelse = new Avtale(startDatoTid, beskrivelse, mockParorende, mockPleietrengende);
        nyAvtaleMedManedligGjentakelse.setGjentakelse("Månedlig");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleMedManedligGjentakelse);
        }, "Det skal ikke være mulig å oppdatere gjentakelsen fra ingen til månedlig.");
    }

}
