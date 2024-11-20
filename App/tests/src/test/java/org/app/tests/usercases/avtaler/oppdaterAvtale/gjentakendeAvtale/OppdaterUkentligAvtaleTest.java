package org.app.tests.usercases.avtaler.oppdaterAvtale.gjentakendeAvtale;

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
public class OppdaterUkentligAvtaleTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;
    String gjentakelse = "ukentlig";
    String beskrivelse = "Legetime";

    @Test
    @DisplayName("Kan ikke endre dato på en ukentlig gjentakende avtale")
    public void kanIkkeEndreDatoPåUkentligAvtale() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2024, 12, 9, 23, 59);

        Avtale eksisterendeAvtale = new Avtale(dato, beskrivelse, gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        LocalDateTime nyDato = LocalDateTime.of(2024, 11, 21, 10, 0);
        Avtale nyAvtale = new Avtale(nyDato, beskrivelse, gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        //Act
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);

        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Dato på gjentakende avtale kan ikke oppdateres");
    }

    @Test
    @DisplayName("Kan ikke endre beskrivelse på en ukentlig gjentakende avtale")
    public void kanIkkeEndreBeskrivelsePåUkentligAvtale() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 10, 9, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2024, 11, 25, 23, 59);
        Avtale eksisterendeAvtale = new Avtale(dato, beskrivelse, gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Act
        Avtale nyAvtale = new Avtale(dato, "Endret legetime", gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Beskrivelse på en gjentakende avtale kan ikke oppdateres.");
    }

    @Test
    @DisplayName("Kan ikke endre beskrivelse eller dato/tid på en ukentlig gjentakende avtale")
    public void kanIkkeEndreBeskrivelseEllerDatoTidPåUkentligAvtale() {
        // Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2024, 12, 20, 23, 59);
        Avtale eksisterendeAvtale = new Avtale(dato, beskrivelse, gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Act
        Avtale nyAvtale = new Avtale(dato.plusDays(1), "Endret legetime igjen", gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Verken beskrivelse eller dato/tid på en daglig gjentakende avtale kan oppdateres.");
    }

    @Test
    @DisplayName("Kan ikke endre gjentakelsesformen på en ukentlig gjentakende avtale")
    public void kanIkkeEndreGjentakelseFraUkentlig() {
        // Arrange
        LocalDateTime startDatoTid = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2024, 12, 21, 23, 5);
        Avtale eksisterendeAvtale = new Avtale(startDatoTid, beskrivelse, gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Act
        Avtale nyAvtaleUkentligTilDaglig = new Avtale(startDatoTid, beskrivelse, "daglig", sluttdato, mockParorende, mockPleietrengende);

        // Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleUkentligTilDaglig);
        }, "Gjentakelsesformen kan ikke endres fra ukentlig til daglig.");

        // Act
        Avtale nyAvtaleUkentligTilMånedlig = new Avtale(startDatoTid, "Legetime", "månedlig", sluttdato, mockParorende, mockPleietrengende);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtaleUkentligTilMånedlig);
        }, "Gjentakelsesformen kan ikke endres fra ukentlig til månedlig.");
    }
}
