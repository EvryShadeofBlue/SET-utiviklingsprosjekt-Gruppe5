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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class OpprettDagligAvtaleFeilTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Feil ved manglende dato for daglig avtale")
    public void feilManglendeStartDato() {
        //Arrange
        LocalDateTime sluttdato = LocalDateTime.of(2024, 11, 30, 23, 59);
        Avtale avtale = new Avtale(null, "Daglig oppfølging", "daglig", sluttdato, mockParorende, mockPleietrengende);

        //Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        //Assert
        Assertions.assertFalse(result, "Avtalen skal ikke kunne opprettes uten startdato");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Feil ved manglende beskrivelse for daglig avtale")
    public void feilManglendeBeskrivelse() {
        // Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 11, 1, 9, 0); // Startdato: 1. november 2024, 09:00
        LocalDateTime sluttDato = LocalDateTime.of(2024, 11, 10, 9, 0); // Sluttdato: 10. november 2024
        Avtale avtale = new Avtale(startDato, "", "daglig", sluttDato, mockParorende, mockPleietrengende);

        // Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        // Assert
        Assertions.assertFalse(result, "Avtalen skal ikke kunne opprettes uten beskrivelse.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Feil ved sluttdato før startdato for daglig avtale")
    public void feilSluttdatoFørStartdato() {
        // Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 11, 1, 9, 0); // Startdato: 1. november 2024, 09:00
        LocalDateTime sluttDato = LocalDateTime.of(2024, 10, 30, 9, 0); // Sluttdato før startdato
        Avtale avtale = new Avtale(startDato, "Daglig oppfølging", "daglig", sluttDato, mockParorende, mockPleietrengende);

        // Act
        OpprettAvtaleLogikk opprettAvtaleLogikk = new OpprettAvtaleLogikk(mockAvtaleRepo);
        boolean result = opprettAvtaleLogikk.opprettAvtale(avtale);

        // Assert
        Assertions.assertFalse(result, "Avtalen skal ikke kunne opprettes med sluttdato før startdato.");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }
}
