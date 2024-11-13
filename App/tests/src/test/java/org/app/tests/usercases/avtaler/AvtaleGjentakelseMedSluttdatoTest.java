package org.app.tests.usercases.avtaler;

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
public class AvtaleGjentakelseMedSluttdatoTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Feil ved sluttdato uten gjentakelse")
    public void feilSluttdatoUtenGjentakelse() {
        //Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 11, 13, 10, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 11, 15, 10, 0);
        Avtale avtale = new Avtale(startDato, "Daglig trening", "ingen", sluttDato, mockParorende, mockPleietrengende);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertFalse(result, "Avtale med daglig gjentakelse og sluttdato er opprettet");
        Mockito.verify(mockAvtaleRepo, Mockito.never()).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Oppretter avtale med daglig gjentakelse og sluttdato")
    public void opprettAvtaleMedDagligGjentakelse() {
        //Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 11, 13, 10, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 11, 15, 10, 0);
        Avtale avtale = new Avtale(startDato, "Daglig trening", "daglig", sluttDato, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med daglig gjentakelse og sluttdato er opprettet");
        Mockito.verify(mockAvtaleRepo, Mockito.times(3)).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Oppretter avtale med ukentlig gjentakelse og sluttdato")
    public void opprettAvtaleMedUkentligGjentakelse() {
        //Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 11, 13, 10, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 11, 27, 10, 0);
        Avtale avtale = new Avtale(startDato, "Legetime", "ukentlig", sluttDato, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med ukentlig gjentakelse og sluttdato er opprettet");
        Mockito.verify(mockAvtaleRepo, Mockito.times(3)).opprettAvtale(Mockito.any(Avtale.class));
    }

    @Test
    @DisplayName("Oppretter avtale med månedlig gjentakelse og sluttdato")
    public void opprettAvtaleMedMånedligGjentakelse() {
        //Arrange
        LocalDateTime startDato = LocalDateTime.of(2024, 11, 13, 10, 0);
        LocalDateTime sluttDato = LocalDateTime.of(2024, 12, 13, 10, 0);
        Avtale avtale = new Avtale(startDato, "Legetime", "månedlig", sluttDato, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med månedlig gjentakelse og sluttdato er opprettet");
        Mockito.verify(mockAvtaleRepo, Mockito.times(2)).opprettAvtale(Mockito.any(Avtale.class));
    }
}
