package org.app.tests.usercases.avtaler.opprettAvtale.gjentakendeAvtale;

import org.app.core.models.Avtale;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;
import org.app.core.repositories.AvtaleRepository;
import org.app.core.logikk.AvtaleLogikk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)

public class GjentakelseUtenSluttdaoTest {
    @Mock
    AvtaleRepository mockAvtaleRepo;

    @Mock
    Parorende mockParorende;

    @Mock
    Pleietrengende mockPleietrengende;

    @Test
    @DisplayName("Oppretter avtale med ingen gjentakelse og uten sluttdato")
    public void opprettAvtaleMedIngenGjentakelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "Legetime", "Ingen", null, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med ingen gjentakelse og uten sluttdato er opprettet");
    }

    @Test
    @DisplayName("Oppretter avtale med daglig gjentakelse og uten sluttdato")
    public void opprettAvtaleMedDagligGjentakelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "Daglig trening", "daglig", null, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med daglig gjentakelse og uten sluttdato er opprettet");
    }

    @Test
    @DisplayName("Oppretter avtale med ukentlig gjentakelse og uten sluttdato")
    public void opprettAvtaleMedUkentligGjentakelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "Legetime", "ukentlig", null, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med ukentlig gjentakelse og uten sluttdato er opprettet");

    }

    @Test
    @DisplayName("Oppretter avtale med månedlig gjentakelse og uten sluttdato")
    public void opprettAvtaleMedMånedligGjentakelse() {
        //Arrange
        Avtale avtale = new Avtale(LocalDateTime.now(), "Legetime", "månedlig", null, mockParorende, mockPleietrengende);
        Mockito.when(mockAvtaleRepo.opprettAvtale(Mockito.any(Avtale.class))).thenReturn(true);

        //Act
        AvtaleLogikk avtaleService = new AvtaleLogikk(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med månedlig gjentakelse og uten sluttdato er opprettet");

    }
}