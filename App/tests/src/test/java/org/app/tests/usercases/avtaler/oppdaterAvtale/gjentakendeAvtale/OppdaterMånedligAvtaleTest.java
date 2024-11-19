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
public class OppdaterMånedligAvtaleTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;
    String gjentakelse = "månedlig";
    String beskrivelse = "Legetime";

    @Test
    @DisplayName("Kan ikke endre dato på en månedlig gjentakende avtale")
    public void kanIkkeEndreDatoPåMånedligAvtale() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2025, 2, 21, 10, 0);

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
    @DisplayName("Kan ikke endre beskrivelse på en månedlig gjentakende avtale")
    public void kanIkkeEndreBeskrivelsePåMånedligAvtale() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 10, 9, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2025, 1, 11, 9, 0);
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
    @DisplayName("Kan ikke endre beskrivelse eller dato/tid på en månedlig gjentakende avtale")
    public void kanIkkeEndreBeskrivelseEllerDatoTidPåMånedligAvtale() {
        // Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 20, 10, 0);
        LocalDateTime sluttdato = LocalDateTime.of(2025, 2, 20, 10, 0);
        Avtale eksisterendeAvtale = new Avtale(dato, beskrivelse, gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Act
        Avtale nyAvtale = new Avtale(dato.plusDays(1), "Endret legetime igjen", gjentakelse, sluttdato, mockParorende, mockPleietrengende);

        // Assert
        OppdaterAvtaleLogikk oppdaterAvtaleLogikk = new OppdaterAvtaleLogikk(mockAvtaleRepo);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            oppdaterAvtaleLogikk.oppdaterAvtale(eksisterendeAvtale, nyAvtale);
        }, "Verken beskrivelse eller dato/tid på en daglig gjentakende avtale kan oppdateres.");
    }
}
