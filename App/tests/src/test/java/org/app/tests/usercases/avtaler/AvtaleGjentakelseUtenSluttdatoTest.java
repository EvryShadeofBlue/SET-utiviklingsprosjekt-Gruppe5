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

public class AvtaleGjentakelseUtenSluttdatoTest {
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
        AvtaleService avtaleService = new AvtaleService(mockAvtaleRepo);
        boolean result = avtaleService.opprettAvtale(avtale);

        //Assert
        Assertions.assertTrue(result, "Avtale med ingen gjentakelse og uten sluttdato er opprettet");
    }

    @Test
    @DisplayName("Oppretter avtale med daglig gjentakelse og uten sluttdato")

}
