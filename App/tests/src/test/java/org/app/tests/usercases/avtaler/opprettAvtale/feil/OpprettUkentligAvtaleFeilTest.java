package org.app.tests.usercases.avtaler.opprettAvtale.feil;

import org.app.core.logikk.avtale.OpprettAvtaleLogikk;
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
public class OpprettUkentligAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Feil ved manglende dato for ukentlig avtale")
    public void feilManglendeStartDato() {
        //Arrange
        LocalDateTime sluttdato = LocalDateTime.of(2024, 12, 3, 23, 59);
        Avtale avtale = new Avtale(null, "ukentlig fysioterapaut", "ukentlig", sluttdato, mockParorende, mockPleietrengende);

        //Act og Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
        }, "Dato og klokkeslett kan ikke være tom.");
    }

    @Test
    @DisplayName("Feil ved manglende beskrivelse for ukentlig avtale")
    public void feilManglendeBeskrivelse() {
        // Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 12, 9, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 11, 25, 9, 0);
        Avtale avtale = new Avtale(dato, null, "ukentlig", sluttDato, mockParorende, mockPleietrengende);

        // Act og Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
        }, "Beskrivelse kan ikke være tom.");
    }

    @Test
    @DisplayName("Feil ved sluttdato før startdato")
    public void feilSluttdatoFørDato() {
        // Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 11, 14, 9, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 11, 11, 9, 0);
        Avtale avtale = new Avtale(dato, "ukentlig besøk av pårørende", "ukentlig", sluttDato, mockParorende, mockPleietrengende);

        // Act og Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
        }, "Startdato kan ikke være etter sluttdato eller i fremtiden");
    }

    @Test
    @DisplayName("Feil ved manglende gjentakelse uten sluttdato")
    public void feilManglendeGjentakelse() {
        //Arrange
        LocalDateTime dato = LocalDateTime.of(2024, 12, 10, 15, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 12, 31, 23, 59);
        Avtale avtale = new Avtale(dato, "Besøk fra hjemmetjenesten", null, sluttDato, mockParorende, mockPleietrengende);

        //Act og Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
            opprettAvtaleLogikk.opprettAvtale(avtale);
        }, "Gjentakelse må være spesifisert hvis sluttdato er valgt");
    }
}
